package com.workplan.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.workplan.bean.WorkItemFirstBean;
import com.workplan.bean.WorkItemSecondSimpleBean;

public class WorkItemSecondDao {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 数据模板：表中的所有数据
	 * @author 01059101
	 *
	 */
	class WorkItemSecondMapper implements RowMapper<WorkItemSecondSimpleBean> {
		public WorkItemSecondSimpleBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WorkItemSecondSimpleBean info = new WorkItemSecondSimpleBean();
			info.setID(rs.getLong("ID"));
			info.setSuperior_item_id(rs.getString("superior_item_id"));
			info.setItemId(rs.getString("item_id"));
			info.setItemName(rs.getString("item_name"));
			info.setOrganization(rs.getString("organization"));
			info.setWorkContent(rs.getString("work_content"));
			info.setStep(rs.getString("step"));
			info.setCompletionCycle(rs.getString("completion_cycle"));
			info.setPersonLiable(rs.getString("person_liable"));
			info.setPartner(rs.getString("partner"));
			info.setStartTime(rs.getString("start_time"));
			info.setCompletionTime(rs.getString("completion_time"));
			info.setState(rs.getInt("state"));
			info.setCreationDate(rs.getString("creation_date"));
			return info;
		}

	}

	/**
	 * 数据模板：与一级事项匹配后的数据
	 * @author 01059101
	 *
	 */
	class WorkItemSecondMapperWithFirst implements RowMapper<WorkItemSecondSimpleBean> {
		public WorkItemSecondSimpleBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WorkItemSecondSimpleBean info = new WorkItemSecondSimpleBean();
			info.setSuperior_item_id(rs.getString("superior_item_id"));
			info.setSuperior_item_name(rs.getString("superior_item_name"));
			info.setItemId(rs.getString("item_id"));
			info.setItemName(rs.getString("item_name"));
			info.setOrganization(rs.getString("organization"));
			info.setWorkContent(rs.getString("work_content"));
			info.setStep(rs.getString("step"));
			info.setCompletionCycle(rs.getString("completion_cycle"));
			info.setPersonLiable(rs.getString("person_liable"));
			info.setPartner(rs.getString("partner"));
			info.setStartTime(rs.getString("start_time"));
			info.setCompletionTime(rs.getString("completion_time"));
			info.setState(rs.getInt("state"));
			info.setCreationDate(rs.getString("creation_date"));
			return info;
		}
	}
	/**

	/**
	 * 数据模板：导出数据，与一级事项匹配后的数据
	 * @author 01059101
	 *
	 */
	class WorkItemExportMapper implements RowMapper<WorkItemSecondSimpleBean> {
		public WorkItemSecondSimpleBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WorkItemSecondSimpleBean info = new WorkItemSecondSimpleBean();
			info.setSuperior_item_name(rs.getString("superior_item_name"));
			info.setItemId(rs.getString("item_id"));
			info.setItemName(rs.getString("item_name"));
			info.setOrganization(rs.getString("organization"));
			info.setWorkContent(rs.getString("work_content"));
			info.setStep(rs.getString("step"));
			info.setCompletionCycle(rs.getString("completion_cycle"));
			info.setPersonLiable(rs.getString("person_liable"));
			info.setPartner(rs.getString("partner"));
			info.setStartTime(rs.getString("start_time"));
			info.setCompletionTime(rs.getString("completion_time"));
			info.setState(rs.getInt("state"));
			return info;
		}
	}
	/**
	 * 数据模板：与一级事项匹配后的数据总条数
	 * @author 01059101
	 *
	 */
	class WorkItemSecondMapperWithFirstCounts implements RowMapper<WorkItemSecondSimpleBean> {
		public WorkItemSecondSimpleBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WorkItemSecondSimpleBean info = new WorkItemSecondSimpleBean();
			info.setItemId(rs.getString("item_id"));
			return info;
		}

	}
	/**
	 * 数据模板：获取步骤信息
	 * @author 01059101
	 *
	 */
	class WorkItemSecondMapperWithStep implements RowMapper<WorkItemSecondSimpleBean> {
		public WorkItemSecondSimpleBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WorkItemSecondSimpleBean info = new WorkItemSecondSimpleBean();
			info.setStep(rs.getString("step"));
			return info;
		}
	}
	/**
	 * 数据模板：添加步骤需要的相关信息
	 * @author 01059101
	 *
	 */
	class WorkItemSecondMapperWithStepNeedsInfo implements RowMapper<WorkItemSecondSimpleBean> {
		public WorkItemSecondSimpleBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WorkItemSecondSimpleBean info = new WorkItemSecondSimpleBean();
			info.setSuperior_item_name(rs.getString("superior_item_name"));
			info.setItemId(rs.getString("item_id"));
			info.setItemName(rs.getString("item_name"));
			info.setWorkContent(rs.getString("work_content"));
			info.setStep(rs.getString("step"));
			return info;
		}
	}

	/**
	 * 数据模板：添加步骤需要的相关信息
	 * @author 01059101
	 *
	 */
	class WorkItemSecondMapperWithItemIdAndItemNameInfo implements RowMapper<WorkItemSecondSimpleBean> {
		public WorkItemSecondSimpleBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WorkItemSecondSimpleBean info = new WorkItemSecondSimpleBean();
			info.setItemId(rs.getString("item_id"));
			info.setItemName(rs.getString("item_name"));
			return info;
		}
	}
	

	/**
	 * 数据模板：事项状态
	 * @author 01059101
	 *
	 */
	class WorkItemSecondStateMapper implements RowMapper<WorkItemSecondSimpleBean> {
		public WorkItemSecondSimpleBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WorkItemSecondSimpleBean info = new WorkItemSecondSimpleBean();
			info.setState(rs.getInt("state"));
			return info;
		}

	}
	/**
	 * 数据模板：首页展示
	 * @author 01059101
	 *
	 */
	class WorkItemSecondToMain implements RowMapper<WorkItemSecondSimpleBean> {
		public WorkItemSecondSimpleBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WorkItemSecondSimpleBean info = new WorkItemSecondSimpleBean();
			info.setItemId(rs.getString("item_id"));
			info.setState(rs.getInt("state"));
			return info;
		}

	}
	/**
	 * 获取所有数据
	 * @return List<WorkItemSecondBean> ID/事项ID/工作内容
	 */
	public List<WorkItemSecondSimpleBean> queryWorkItemSecondMapperWithItemIdAndItemNameInfo() {
		String sql = "select * from item_second";
		return jdbcTemplate.query(sql, new WorkItemSecondMapperWithItemIdAndItemNameInfo());
	}


	/**
	 * 获取所有数据
	 * @return List<WorkItemSecondBean> ID/事项ID/工作内容
	 */
	public List<WorkItemSecondSimpleBean> queryAll() {
		String sql = "select * from item_second";
		return jdbcTemplate.query(sql, new WorkItemSecondMapper());
	}


	/**
	 * 获取所有数据
	 * @param item_id
	 * @return
	 */
	public List<WorkItemSecondSimpleBean> queryAllByItemId(String item_id) {
		String sql = "select * from item_second where item_id = ?";
		return jdbcTemplate.query(sql,new Object[]{item_id}, new WorkItemSecondMapper());
	}



	/**
	 * 获取进程状态
	 * @return List<WorkItemSecondBean> State
	 */
	public List<WorkItemSecondSimpleBean> queryInfoState(String item_id) {
		String sql = "select state from item_second where item_id=?";
		return jdbcTemplate.query(sql,new Object[]{item_id}, new WorkItemSecondStateMapper());
	}

	/**
	 * 获取进程状态
	 * @return List<WorkItemSecondBean> State
	 */
	public List<WorkItemSecondSimpleBean> queryInfoStateWithSuperiorItemId(String superior_item_id) {
		String sql = "select state from item_second where superior_item_id=?";
		return jdbcTemplate.query(sql,new Object[]{superior_item_id}, new WorkItemSecondStateMapper());
	}


	/**
	 * 添加数据
	 * @param item_id
	 * @param item_name
	 * @return true or false
	 */
	public boolean add(String superior_item_id,String item_id,String item_name,String organization,String work_content,
			String step,String completion_cycle,String person_liable,String partner,String start_time,String completion_time
			,Integer state,String creation_date) {
		String sql = "insert into item_second(superior_item_id,item_id, item_name, organization, work_content, step, completion_cycle, person_liable, partner, start_time, completion_time,state,creation_date) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, new Object[] {superior_item_id,item_id, item_name, organization, work_content, step, completion_cycle, person_liable, partner, start_time, completion_time,state,creation_date }) == 1;
	}
	
	

	/**
	 * 选择了事项和状态：数据
	 * @return List<WorkItemSecondBean> ID/事项ID/工作内容
	 */
	public List<WorkItemSecondSimpleBean> queryAllInfoWithFirstBySearchCondition(ArrayList<String> Work_State,ArrayList<String> First_id,ArrayList<String> Second_id,int page,int limit) {
		String sql =  "SELECT "+
						"item_second.superior_item_id,"+
						"item_first.item_name as superior_item_name,"+
						"item_second.item_id,"+
						"item_second.item_name,"+
						"item_second.organization,"+
						"item_second.work_content,"+
						"item_second.step,"+
						"item_second.completion_cycle,"+
						"userinfo.user_name as person_liable,"+
						"item_second.partner,"+
						"item_second.start_time,"+
						"item_second.completion_time,"+
						"item_second.state,"+
						"item_second.creation_date"+
					" FROM "+
						"item_first,item_second LEFT JOIN userinfo ON item_second.person_liable = userinfo.user_id"+
					" WHERE "+
						"item_second.superior_item_id = item_first.item_id And ";
		System.out.println(Work_State.size()==1);
		System.out.println(Work_State.size()>1);
		if(Work_State.size()==1){
			sql+=" item_second.state = "+Work_State.get(0).toString()+" And ";
		}else if (Work_State.size()>1) {
			sql+="(";
			for(int a= 0;a<Work_State.size();a++){
				sql+=" item_second.state = "+Work_State.get(a).toString()+" or ";
			}
			sql=sql.substring(0,sql.length()-3)+" ) And ";
		}

		System.out.println(First_id.size()==1);
		System.out.println(First_id.size()>1);
		if(First_id.size()==1){
			sql+=" item_first.item_id = "+First_id.get(0).toString()+" And ";
		}else if (First_id.size()>1) {
			sql+="(";
			for(int a= 0;a<First_id.size();a++){
				sql+=" item_first.item_id = "+First_id.get(a).toString()+" or ";
			}
			sql=sql.substring(0,sql.length()-3)+" ) And ";
		}

		System.out.println(Second_id.size()==1);
		System.out.println(Second_id.size()>1);
		System.out.println("【0】"+sql);
		if(Second_id.size()==1){
			sql+=" item_second.item_id = "+Second_id.get(0).toString()+" And ";
		}else if (Second_id.size()>1) {
			System.out.println("【1】"+sql);
			sql+="(";
			for(int a= 0;a<Second_id.size();a++){
				sql+=" item_second.item_id = "+Second_id.get(a).toString()+" or ";
			}
			System.out.println("【2】"+sql);
			sql=sql.substring(0,sql.length()-3)+" ) And ";
			System.out.println("【3】"+sql);
		}
		
		sql+=" 1=1  limit ? , ? ";
		System.out.println(sql);
		return jdbcTemplate.query(sql, new Object[]{(page - 1)*limit,limit}, new WorkItemSecondMapperWithFirst());
	}

	/**
	 * 选择了事项和状态：数据总行数
	 * @return List<WorkItemSecondBean> ID/事项ID/工作内容
	 */
	public int queryAllInfoCountsBySearchCondition(ArrayList<String> Work_State,ArrayList<String> First_id,ArrayList<String> Second_id) {
		String sql =  "SELECT "+
						"COUNT(item_second.item_id) as item_id"+
					" FROM "+
						"item_first,item_second"+
					" WHERE "+
						"item_second.superior_item_id = item_first.item_id And ";
		if(Work_State.size()==1){
			sql+=" item_second.state = "+Work_State.get(0).toString()+" And ";
		}else if (Work_State.size()>1) {
			sql+="(";
			for(int a= 0;a<Work_State.size();a++){
				sql+=" item_second.state = "+Work_State.get(a).toString()+" or ";
			}
			sql=sql.substring(0,sql.length()-3)+") And ";
		}
		

		if(First_id.size()==1){
			sql+=" item_first.item_id = "+First_id.get(0).toString()+" And ";
		}else if (First_id.size()>1) {
			sql+="(";
			for(int a= 0;a<First_id.size();a++){
				sql+=" item_first.item_id = "+First_id.get(a).toString()+" or ";
			}
			sql=sql.substring(0,sql.length()-3)+") And ";
		}
		if(Second_id.size()==1){
			sql+=" item_second.item_id = "+Second_id.get(0).toString()+" And ";
		}else if (Second_id.size()>1) {
			sql+="(";
			for(int a= 0;a<Second_id.size();a++){
				sql+=" item_second.item_id = "+Second_id.get(a).toString()+" or ";
			}
			sql=sql.substring(0,sql.length()-3)+") And ";
		}
		
		sql+=" 1=1";
		
		System.out.println(sql);
		List<WorkItemSecondSimpleBean>  second_list = new ArrayList<WorkItemSecondSimpleBean>();
		second_list=jdbcTemplate.query(sql,  new WorkItemSecondMapperWithFirstCounts());
		int counts=0;
		if(second_list.size()==1)
			counts=Integer.parseInt(second_list.get(0).getItemId().toString());
		return counts;
	}


	/**
	 * 根据ID更新事项名称
	 * @param item_name 事项名称
	 * @param item_id 事项ID
	 * @return true or false
	 */
	public boolean updataItemInfoByItemId(String item_id,String superior_item_id,
			String item_name,String organization,String work_content,String completion_cycle,
			String person_liable,String partner,String start_time,String completion_time) {
		String sql = "UPDATE item_second set superior_item_id= ? ," +
				"item_name= ? ," +
				"organization= ? ," +
				"work_content= ? ," +
				"completion_cycle= ? ," +
				"person_liable= ? ," +
				"partner= ? ," +
				"start_time= ? ," +
				"completion_time= ?  where item_id = ? ";
		return jdbcTemplate.update(sql, new Object[] {superior_item_id,
				item_name,organization,work_content,completion_cycle,
				person_liable,partner,start_time,completion_time, item_id }) == 1;
	}

	/**
	 * 根据ID更新事项状态
	 * @param state 事项状态
	 * @param item_id 事项ID
	 * @return
	 */
	public boolean updataItemState(int state,String item_id) {
		String sql = "UPDATE item_second set state= ? where item_id = ? ";
		return jdbcTemplate.update(sql, new Object[] {state, item_id }) == 1;
	}


	/**
	 * 删除数据
	 * @param item_id
	 * @return true or false
	 */
	public boolean deleteByItemId(String item_id) {
		String sql = "DELETE from item_second where item_id = ?";
		return jdbcTemplate.update(sql, new Object[] { item_id}) == 1;
	}

	/**
	 * 通过一级事项ID批量删除
	 * 
	 * @param userlist
	 */
	public int batchDeleteBySuperiorItemId(final List<WorkItemFirstBean> list) {
		try {
			String sql = "DELETE from item_second where superior_item_id = ?";
			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				public int getBatchSize() {
					return list.size();
				}

				public void setValues(PreparedStatement ps, int i)
						throws SQLException {
					ps.setString(1, list.get(i).getItemId());
				}
			});
			return 0;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 1;
		}
	}

	/**
	 * 通过二级事项ID批量删除二级事项
	 * 
	 * @param userlist
	 */
	public int batchDeleteByItemId(final List<WorkItemSecondSimpleBean> list) {
		try {
			String sql = "DELETE from item_second where item_id = ?";
			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				public int getBatchSize() {
					return list.size();
				}

				public void setValues(PreparedStatement ps, int i)
						throws SQLException {
					ps.setString(1, list.get(i).getItemId());
				}
			});
			return 0;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 1;
		}
	}


	/**
	 * 获取 一级事项名称/二级事项名称和ID/工作内容/步骤
	 * @return 事项状态不为已完成的所有二级事项相关内容
	 */ 
	public List<WorkItemSecondSimpleBean> queryWorkContentWithFirstIdAndSecondId() {
		String sql =  "SELECT "+
							"item_first.item_name AS superior_item_name, "+
							"item_second.item_id, "+
							"item_second.item_name, "+
							"item_second.work_content, "+
							"item_second.step "+
						"FROM "+
							"item_first, "+
							"item_second "+
						"WHERE "+
							"item_second.superior_item_id = item_first.item_id  AND item_second.state != 1 ";
		
		System.out.println(sql);
		return jdbcTemplate.query(sql, new WorkItemSecondMapperWithStepNeedsInfo());
	}


	/**
	 * 获取步骤信息
	 * @return String 
	 */ 
	public List<WorkItemSecondSimpleBean> queryStepByItemId(String item_id) {
		String sql =  "SELECT step FROM item_second WHERE item_id = ?";
		return jdbcTemplate.query(sql,new Object[]{item_id}, new WorkItemSecondMapperWithStep());
	}


	/**
	 * 根据ID更新事项步骤
	 * @param step 步骤
	 * @param item_id 事项ID
	 * @return true or false
	 */
	public boolean updataItemStep(String step,String item_id) {
		String sql = "UPDATE item_second set step= ? where item_id = ? ";
		return jdbcTemplate.update(sql, new Object[] {step, item_id }) == 1;
	}

	/**
	 * 获取 首页展示数据
	 * @return 首页展示数据
	 */ 
	public List<WorkItemSecondSimpleBean> queryWorkContentToMain() {
		String sql =  "select state, COUNT(*) as 'item_id' from item_second group by state";
		System.out.println(sql);
		return jdbcTemplate.query(sql, new WorkItemSecondToMain());
	}
	
	/**
	 * 导出所有事项内容
	 * @return 事项的进度将由此结果进行搜索获取
	 */
	public List<WorkItemSecondSimpleBean> queryFirstAndSeconedAllInfoNotProgress() {
		String sql="SELECT item_first.item_name AS 'superior_item_name', item_second.item_id,item_second.item_name, item_second.organization, item_second.work_content, item_second.step, item_second.completion_cycle, item_second.person_liable, item_second.partner, item_second.start_time, item_second.completion_time, item_second.state AS state FROM item_first, item_second WHERE item_first.item_id = item_second.superior_item_id ORDER BY item_first.item_id;";
		return jdbcTemplate.query(sql,new WorkItemExportMapper());
	}

	/**
	 * 事项还有即将过期
	 * @param nowDateTimeAddSettingDate
	 * @param user_name
	 * @return
	 */
	public List<WorkItemSecondSimpleBean> queryTimeOutItem(int Date,String user_name) {
		
		//String sql ="SELECT item_id, item_name FROM item_second WHERE completion_time BETWEEN date_sub(curdate(), INTERVAL ? DAY)  AND curdate() AND state != '1' AND ( person_liable = ? OR find_in_set(?, partner))";
		String sql ="SELECT item_id, item_name FROM item_second WHERE completion_time BETWEEN curdate()  AND date_sub(curdate(), INTERVAL ? DAY) AND state != '1' AND ( person_liable = ? OR find_in_set(?, partner))";
		return jdbcTemplate.query(sql,new Object[]{Date,user_name,user_name},new WorkItemSecondMapperWithItemIdAndItemNameInfo());
		
	}
	
	/**
	 * 事项已经过期
	 * @param nowDateTimeAddSettingDate
	 * @param user_name
	 * @return
	 */
	public List<WorkItemSecondSimpleBean> queryTimeOutItem(String user_name) {
		String sql ="SELECT item_id, item_name FROM item_second WHERE completion_time < curdate() AND state != '1' AND ( person_liable = ? OR find_in_set(?, partner))";
		return jdbcTemplate.query(sql,new Object[]{user_name,user_name},new WorkItemSecondMapperWithItemIdAndItemNameInfo());
		
	}
	
}
