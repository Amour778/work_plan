package com.workplan.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.workplan.bean.WeakCurrentProjectsApprovaLogBean;

public class WeakCurrentProjectsApprovaLogDao {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	class WeakCurrentProjectsApprovaLogMapper implements RowMapper<WeakCurrentProjectsApprovaLogBean> {
		public WeakCurrentProjectsApprovaLogBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WeakCurrentProjectsApprovaLogBean info = new WeakCurrentProjectsApprovaLogBean();
			info.setId(rs.getString("id"));
			info.setItem_id(rs.getString("item_id"));
			info.setItem(rs.getString("item"));
			info.setApproval_time(rs.getString("approval_time").substring(0, 19));
			info.setApproval_sta(rs.getString("approval_sta"));
			info.setApproval_user(rs.getString("approval_user"));
			info.setRemark(rs.getString("remark"));
			return info;
		}

	}

	public List<WeakCurrentProjectsApprovaLogBean> queryWCPALog(String item_id) {
		String sql = "SELECT id,item_id,item,approval_time,approval_sta,approval_user,remark FROM wcp_approval_log where item_id = ? ORDER BY id DESC";
		return jdbcTemplate.query(sql,new Object[]{item_id},new WeakCurrentProjectsApprovaLogMapper());
	}


	public boolean addWCPALog(String item_id,String item,String approval_time,String approval_sta,String approval_user,String remark) {
		System.out.println("记录的操作为："+item_id+","+item+","+approval_time+","+approval_sta+","+approval_user+","+remark);
		String sql = "INSERT INTO wcp_approval_log " +
		"(item_id,item,approval_time,approval_sta,approval_user,remark)" +
		"VALUES" +
		"(?,?,?,?,?,?)";
		System.out.println(sql);
		return  jdbcTemplate.update(sql, new Object[] { item_id, item, approval_time, approval_sta, approval_user, remark})==1;
	}

	/**
	 * 通过组ID获取审核数据
	 * @param group_id
	 * @return
	 */
	public List<WeakCurrentProjectsApprovaLogBean>  getLogByWCPActualGroupId(String group_id) {
		String sql ="SELECT " +
		"wcp_approval_log.id, " +
		"wcp_approval_log.item_id, " +
		"wcp_approval_log.item, " +
		"wcp_approval_log.approval_time, " +
		"wcp_approval_log.approval_sta, " +
		"wcp_approval_log.approval_user, " +
		"wcp_approval_log.remark "+
		"FROM " +
		"wcp_actual_group " +
		"INNER JOIN wcp_actual_expenditure ON wcp_actual_group.group_id = wcp_actual_expenditure.group_id " +
		"INNER JOIN wcp_approval_log ON wcp_actual_expenditure.actual_id = wcp_approval_log.item_id " +
		"WHERE wcp_actual_group.group_id=? " +
		"ORDER BY " +
		"wcp_approval_log.approval_time DESC";
		System.out.println(sql);
		return jdbcTemplate.query(sql,new Object[]{group_id},new WeakCurrentProjectsApprovaLogMapper());
	}
}
