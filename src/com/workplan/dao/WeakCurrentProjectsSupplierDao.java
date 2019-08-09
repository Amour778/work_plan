package com.workplan.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.workplan.bean.WeakCurrentProjectsSupplierBean;

public class WeakCurrentProjectsSupplierDao {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/*拥有所有列数据*/
	class WCPSupplierAllInfoMapper implements RowMapper<WeakCurrentProjectsSupplierBean> {
		public WeakCurrentProjectsSupplierBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WeakCurrentProjectsSupplierBean info = new WeakCurrentProjectsSupplierBean();
			info.setSupplier_sid(rs.getString("supplier_sid"));
			info.setSupplier_name(rs.getString("supplier_name"));
			info.setMnemonic_code(rs.getString("mnemonic_code"));
			info.setSupplier_name_abbreviation(rs.getString("supplier_name_abbreviation"));
			info.setSupplier_english_name(rs.getString("supplier_english_name"));
			return info;
		}
	}
	/*拥有供应商ID、供应商名称数据*/
	class WCPSupplierInfoOnlySidAndNameMapper implements RowMapper<WeakCurrentProjectsSupplierBean> {
		public WeakCurrentProjectsSupplierBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WeakCurrentProjectsSupplierBean info = new WeakCurrentProjectsSupplierBean();
			info.setSupplier_sid(rs.getString("supplier_sid"));
			info.setSupplier_name(rs.getString("supplier_name"));
			return info;
		}
	}

	/**
	 * 无条件搜索所有符合供应商数据
	 * @return List
	 * @throws Exception
	 */
	public List<WeakCurrentProjectsSupplierBean> queryAllSupplierNoCriteria()
			throws Exception {
			String sql = "select supplier_sid,supplier_name from wcp_supplier";
		return jdbcTemplate.query(sql,new WCPSupplierInfoOnlySidAndNameMapper());
	}
	/**
	 * 根据搜索条件获取所有符合条件的供应商数据
	 * @param criteria 约束条件
	 * @param page
	 * @param limit
	 * @return List
	 * @throws Exception
	 */
	public List<WeakCurrentProjectsSupplierBean> queryAllSupplierList(String criteria,int page,int limit)
			throws Exception {
		String sql;
		List<WeakCurrentProjectsSupplierBean> userInfoBean=null;
		if(criteria==null){
			sql = "select * from wcp_supplier ORDER BY time DESC limit ? , ?";
			userInfoBean = jdbcTemplate.query(sql,new Object[]{(page - 1)*limit,limit},new WCPSupplierAllInfoMapper());
		}else{
			sql = "select * from wcp_supplier  where supplier_sid like ? OR supplier_name like ? ORDER BY time DESC limit ? , ?";
			userInfoBean = jdbcTemplate.query(sql,new Object[]{"%"+criteria+"%","%"+criteria+"%",(page - 1)*limit,limit},new WCPSupplierAllInfoMapper());
		}
		return userInfoBean;
	}
	/**
	 * 根据搜索条件获取所有符合条件的供应商数据
	 * @param criteria 约束条件
	 * @param page
	 * @param limit
	 * @return counts
	 * @throws Exception
	 */
	public int queryAllSupplierCounts(String criteria,int page,int limit)throws Exception {
		String sql;
		List<WeakCurrentProjectsSupplierBean> userInfoBean=null;
		if(criteria==null){
			sql = "select * from wcp_supplier ORDER BY time DESC limit ? , ?";
			userInfoBean = jdbcTemplate.query(sql,new Object[]{(page - 1)*limit,limit},new WCPSupplierAllInfoMapper());
		}else{
			sql = "select * from wcp_supplier  where supplier_sid like ? OR supplier_name like ? ORDER BY time DESC limit ? , ?";
			userInfoBean = jdbcTemplate.query(sql,new Object[]{"%"+criteria+"%","%"+criteria+"%",(page - 1)*limit,limit},new WCPSupplierAllInfoMapper());
		}
		return userInfoBean.size();
	}
	
	/**
	 * 添加新的供应商信息
	 * @param supplier_sid 客商编码
	 * @param supplier_name客商名称
	 * @param mnemonic_code助记码
	 * @param supplier_name_abbreviation客商简称
	 * @param supplier_english_name外文名称
	 * @return
	 */
	public boolean insertSupplierInfo(String supplier_sid,String supplier_name,String mnemonic_code,String supplier_name_abbreviation,String supplier_english_name) {
		String sql = "insert into wcp_supplier(supplier_sid,supplier_name,mnemonic_code,supplier_name_abbreviation,supplier_english_name) values(?,?,?,?,?)";
		return jdbcTemplate.update(sql, new Object[] {supplier_sid,supplier_name,mnemonic_code,supplier_name_abbreviation,supplier_english_name}) == 1;
	}
}
