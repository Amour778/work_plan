package com.workplan.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.workplan.bean.ParameterBean;

public class ParameterDao {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 数据模板
	 * 
	 * @author 01059101
	 * 
	 */
	class ParameterMapper implements RowMapper<ParameterBean> {
		public ParameterBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			ParameterBean info = new ParameterBean();
			info.setPid(rs.getString("pid"));
			info.setType(rs.getString("type"));
			info.setCid(rs.getString("cid"));
			info.setText(rs.getString("text"));
			info.setValue(rs.getString("value"));
			info.setSelect(rs.getString("select"));
			info.setDisable(rs.getString("disable"));
			return info;
		}

	}

	/**
	 * 获取所有参数信息
	 */
	public List<ParameterBean> queryAllParameterNoparameter() {
		String sql = "select * from parameter order by pid";
		System.out.println(sql);
		List<ParameterBean> list = jdbcTemplate.query(sql,new ParameterMapper());
		return list;
	}

	/**
	 * 通过PID获取参数
	 * @param pid
	 * @return
	 */
	public List<ParameterBean> queryAllParameterByPid(String pid){
		String sql = "select * from parameter where pid = ?";
		System.out.println(sql);
		List<ParameterBean> userInfoBean = jdbcTemplate.query(sql,new Object[]{pid},new ParameterMapper());
		return userInfoBean;
	}

	/**
	 * 通过TYPE获取参数
	 * @param pid
	 * @return like List<ParameterBean>
	 */
	public List<ParameterBean> queryAllParameterByType(String type){
		String sql = "select * from parameter where type like %"+type+"%";
		List<ParameterBean> userInfoBean = jdbcTemplate.query(sql,new ParameterMapper());
		return userInfoBean;
	}

	/**
	 * 通过PID和CID获取参数
	 * @param pid
	 * @param cid
	 * @return List<ParameterBean>
	 */
	public List<ParameterBean> queryAllParameterByPidAndCid(String pid,String cid){
		String sql = "select * from parameter where pid = ? AND cid = ?";
		System.out.println(sql);
		List<ParameterBean> userInfoBean = jdbcTemplate.query(sql,new Object[]{pid,cid},new ParameterMapper());
		return userInfoBean;
	}
	
	/**
	 * 添加新的参数
	 * @param pid
	 * @param type
	 * @param cid
	 * @param text
	 * @param value
	 * @param select
	 * @param disable
	 * @return
	 */
	public boolean add(String pid,String type,String cid,String text,String value,String select,String disable) {
		String sql = "INSERT INTO parameter (pid, type, cid,text, value, select, disable) VALUES (?, ?, ?, ?, ?, ?);";
		System.out.println(sql);
		return jdbcTemplate.update(sql, new Object[] {pid, type, cid, text,value, select, disable}) == 1;
	}
	/**
	 * 通过PID和CID更新数据
	 * @param Npid 新PID
	 * @param type 新CID
	 * @param Ncid 
	 * @param text
	 * @param value
	 * @param select
	 * @param disable
	 * @param Opid 旧PID
	 * @param Ocid 旧CID
	 * @return
	 */
	public boolean update(String Npid,String type,String Ncid,String text,String value,String select,String disable,String Opid,String Ocid) {
		String sql = "UPDATE parameter set pid=?, type=?, cid=?, text=?,value=?, select=?, disable=? where pid=? And cid=?";
		System.out.println(sql);
		return jdbcTemplate.update(sql, new Object[] { Npid, type, Ncid, text, value, select, disable, Opid, Ocid}) == 1;
	}
	
	/**
	 * 通过PID和CID删除数据
	 * @param role_id
	 * @return
	 */
	public boolean del( String pid,String cid) {
		String sql = "DELETE from parameter where pid=? And cid=?";
		System.out.println(sql);
		return jdbcTemplate.update(sql, new Object[] { pid,cid}) == 1;
	}
	
	
}
