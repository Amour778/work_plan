package com.workplan.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.workplan.bean.WorkItemFirstBean;

public class WorkItemFirstDao {

	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 数据模板：所有数据
	 * @author 01059101
	 *
	 */
	class WorkItemFirstMapper implements RowMapper<WorkItemFirstBean> {
		public WorkItemFirstBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WorkItemFirstBean info = new WorkItemFirstBean();
			info.setID(rs.getString("ID"));
			info.setItemId(rs.getString("item_id"));
			info.setItemName(rs.getString("item_name"));
			return info;
		}

	}
	
	/**
	 * 获取所有数据
	 * @return List<WorkItemFirstBean>
	 */
	public List<WorkItemFirstBean> queryAll() {
		String sql = "select * from item_first";
		return jdbcTemplate.query(sql, new WorkItemFirstMapper());
	}


	/**
	 * 根据条件获取所有数据
	 * @return List<WorkItemFirstBean>
	 */
	public List<WorkItemFirstBean> queryAllToTable(String item_name,int page,int limit) {
		String sql="";
		if(item_name==""||item_name==null){
			sql = "select * from item_first limit ? , ?";
			return jdbcTemplate.query(sql, new Object[] { (page - 1) * limit,
					limit }, new WorkItemFirstMapper());
		}else {
			sql = "select * from item_first where item_name like ? limit ? , ?";
			return jdbcTemplate.query(sql, new Object[] { "%" + item_name + "%",
					(page - 1) * limit, limit }, new WorkItemFirstMapper());
		}
		
	}
	/**
	 * 根据条件获取所有数据的总条数
	 * @return List<WorkItemFirstBean>
	 */
	public List<WorkItemFirstBean> queryAllToTableCounts(String item_name) {
		String sql="";
		if(item_name==""||item_name==null){
		sql  = "select * from item_first";
			return jdbcTemplate.query(sql, new WorkItemFirstMapper());
		}else {
			sql  = "select * from item_first where item_name like ? ";
			return jdbcTemplate.query(sql, new Object[] { "%" + item_name + "%" },
					new WorkItemFirstMapper());
			
		}
	}

	/**
	 * 添加数据
	 * @param item_id
	 * @param item_name
	 * @return true or false
	 */
	public boolean add(String item_id,String item_name) {
		String sql = "insert into item_first(item_id,item_name) values(?,?)";
		return jdbcTemplate.update(sql, new Object[] { item_id, item_name}) == 1;
	}


	/**
	 * 根据ID更新数据
	 * @param item_id
	 * @param item_name
	 * @return true or false
	 */
	public boolean updata(String item_id,String item_name) {
		String sql = "UPDATE item_first set item_name= ? where item_id = ? ";
		return jdbcTemplate.update(sql, new Object[] {item_name, item_id }) == 1;
	}


	/**
	 * 删除数据
	 * @param item_id
	 * @return true or false
	 */
	public boolean deleteByItemId(String item_id) {
		String sql = "DELETE from item_first where item_first.item_id = ?";
		return jdbcTemplate.update(sql, new Object[] { item_id}) == 1;
	}
	
	   
	


	/**
	 * 批量删除
	 * 
	 * @param userlist
	 */
	public int batchDeleteByItemId(final List<WorkItemFirstBean> list) {
		try {
			String sql = "DELETE from item_first where item_id = ?";
			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				public int getBatchSize() {
					return list.size();
				}

				public void setValues(PreparedStatement ps, int i)
						throws SQLException {
					System.out.println("list.get("+i+").getItemId()="+list.get(i).getItemId());
					ps.setString(1, list.get(i).getItemId());
				}
			});
			return 0;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 1;
		}
	}
	
}
