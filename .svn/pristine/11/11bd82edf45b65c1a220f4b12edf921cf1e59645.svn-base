package com.workplan.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.workplan.bean.ProgressBean;

public class ProgressDao {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 数据模板：所有数据
	 * @author 01059101
	 *
	 */
	class ProgressMapper implements RowMapper<ProgressBean> {
		public ProgressBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			ProgressBean info = new ProgressBean();
			info.setID(rs.getLong("ID"));
			info.setItem_id(rs.getString("item_id"));
			info.setItem_progress(rs.getString("item_progress"));
			info.setItem_date(rs.getString("item_date"));
			info.setEdit_user(rs.getString("edit_user"));
			return info;
		}

	}
	

	/**
	 * 获取所有数据(倒叙)
	 * @param id
	 * ProgressMapper()
	 * @return List<ProgressBean>
	 */
	public List<ProgressBean> queryAllDESC(String id) {
		String sql = "select * from progress where item_id=? ORDER BY ID DESC";
		return jdbcTemplate.query(sql,new Object[]{id}, new ProgressMapper());
	}

	/**
	 * 获取所有数据(顺叙)
	 * @param id
	 * ProgressMapper()
	 * @return List<ProgressBean>
	 */
	public List<ProgressBean> queryAll(String id) {
		String sql = "select * from progress where item_id=? ORDER BY ID";
		return jdbcTemplate.query(sql,new Object[]{id}, new ProgressMapper());
	}


	/**
	 * 添加数据ByString
	 * @param item_id
	 * @param item_progress
	 * @param item_date
	 * @param edit_user
	 * @return true or false
	 */
	public boolean add(String item_id,String item_progress,String item_date,String edit_user) {
		String sql = "insert into progress(item_id,item_progress,item_date,edit_user) values(?,?,?,?)";
		return jdbcTemplate.update(sql, new Object[] { item_id,item_progress,item_date,edit_user}) == 1;
	}



	/**
	 * 添加数据ByBean
	 * @param bean
	 * @return
	 */
	public boolean addByBean(ProgressBean bean) {
		String sql = "insert into progress(item_id,item_progress,item_date,edit_user) values(?,?,?,?)";
		return jdbcTemplate.update(sql, new Object[] { bean.getItem_id(),bean.getItem_progress(),bean.getItem_date(),bean.getEdit_user()}) == 1;
	}

	/**
	 * 通过一级事项ID批量删除
	 * 
	 * @param userlist
	 */
	public int batchDeleteByItemId(final List<ProgressBean> list) {
		try {
			String sql = "DELETE from progress where item_id = ?";
			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				public int getBatchSize() {
					return list.size();
				}

				public void setValues(PreparedStatement ps, int i)
						throws SQLException {
					ps.setString(1, list.get(i).getItem_id());
				}
			});
			return 0;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 1;
		}
	}

	   
}
