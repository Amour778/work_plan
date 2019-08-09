package com.workplan.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.workplan.bean.WeakCurrentProjectsBonusSharesBean;
public class WeakCurrentProjectsBonusSharesDao {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 数据模板：所有数据
	 * @author 01059101
	 *
	 */
	class WeakCurrentProjectsBonusSharesAllMapper implements RowMapper<WeakCurrentProjectsBonusSharesBean> {
		public WeakCurrentProjectsBonusSharesBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WeakCurrentProjectsBonusSharesBean info = new WeakCurrentProjectsBonusSharesBean();
			info.setID(rs.getString("ID"));
			info.setProject_code(rs.getString("project_code"));
			info.setUser_id(rs.getString("user_id"));
			info.setUser_name(rs.getString("user_name"));
			info.setProportion(rs.getString("proportion"));
			info.setMoney(rs.getString("money"));
			info.setReturned_money(rs.getString("returned_money"));
			return info;
		}
	}



	/**
	 * 批量添加
	 * project_code,user_id,user_name,proportion,money,returned_money
	 * @param list
	 */
	public int batchInsertBonusShares(final List<WeakCurrentProjectsBonusSharesBean> list) {
		try {
		String v_sql = "insert into wcp_bonus_shares(project_code,user_id,user_name,proportion,money,returned_money) values(?,?,?,?,?,?)";
		jdbcTemplate.batchUpdate(v_sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setString(1, list.get(i).getProject_code());
				ps.setString(2, list.get(i).getUser_id());
				ps.setString(3, list.get(i).getUser_name());
				ps.setString(4, list.get(i).getProportion());
				ps.setString(5, list.get(i).getMoney());
				ps.setString(6, list.get(i).getReturned_money());
			}
			public int getBatchSize() {
				return list.size();
			}
		});
			return 0;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 1;
		}
	}
	
	/**
	 * 通过project_code删除数据
	 * @param project_code
	 * @return
	 */
	public boolean delWCProjectsBonusSharesByProjectCodeAndType(String project_code) {
		String sql = "DELETE from wcp_bonus_shares where project_code = ?";
		return jdbcTemplate.update(sql, new Object[] {project_code})==1;
	}

	/**
	 * 通过ID删除数据
	 * 
	 * @param userlist
	 */
	public int batchDeleteByID(final List<WeakCurrentProjectsBonusSharesBean> list) {
		try {
			String sql = "DELETE from wcp_bonus_shares where ID = ? ";
			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				public int getBatchSize() {
					return list.size();
				}

				public void setValues(PreparedStatement ps, int i)
						throws SQLException {
					ps.setString(1, list.get(i).getID());
				}
			});
			return 0;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 1;
		}
	}
	
	
	
	/**
	 * 搜索：数据
	 * @return List<WeakCurrentProjectsBonusSharesBean>
	 */
	public List<WeakCurrentProjectsBonusSharesBean> queryAllInfoWithProjectCode(String project_code) {
		String sql = "SELECT * FROM wcp_bonus_shares WHERE project_code =  ? ";
		return jdbcTemplate.query(sql, new Object[]{project_code}, new WeakCurrentProjectsBonusSharesAllMapper());
	}

	/**
	 * 搜索：数据总行数
	 * @return List<WeakCurrentProjectsSimpleBean>
	 */
	public int queryAllInfoCountsByProjectCode(String project_code) {
		String sql = "SELECT * FROM wcp_bonus_shares WHERE project_code =  ?";
		List<WeakCurrentProjectsBonusSharesBean>  _list = new ArrayList<WeakCurrentProjectsBonusSharesBean>();
		_list=jdbcTemplate.query(sql, new Object[]{project_code},  new WeakCurrentProjectsBonusSharesAllMapper());
		int counts=0;
		counts=_list.size();
		return counts;
	}
	

}
