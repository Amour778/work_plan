package com.workplan.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.workplan.bean.UserInfoBean;

public class UserInfoDao {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 数据模板(所有)
	 * 
	 * @author 01059101
	 * 
	 */
	class UserInfoMapper implements RowMapper<UserInfoBean> {
		public UserInfoBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			UserInfoBean userinfo = new UserInfoBean();
			userinfo.setID(rs.getInt("ID"));
			userinfo.setUser_id(rs.getString("user_id"));
			userinfo.setUser_password(rs.getString("user_password"));
			userinfo.setUser_name(rs.getString("user_name"));
			userinfo.setUser_tel(rs.getString("user_tel"));
			userinfo.setUser_role(rs.getString("user_role"));
			userinfo.setUser_area_wechat_edianzu(rs.getString("user_area_wechat_edianzu"));
			userinfo.setUser_wechat(rs.getString("user_wechat"));
			userinfo.setWcp_area(rs.getString("wcp_area"));
			userinfo.setWcp_head(rs.getString("wcp_head"));
			userinfo.setUser_email(rs.getString("user_email"));
			userinfo.setBank(rs.getString("bank"));
			userinfo.setBank_card_account(rs.getString("bank_card_account"));
			userinfo.setBank_card_place(rs.getString("bank_card_place"));
			userinfo.setBank_card_type(rs.getString("bank_card_type"));
			return userinfo;
		}

	}

	/**
	 * 简单数据模板(无ID,密码)
	 * 
	 * @author 01059101
	 * 
	 */
	class UserInfoSimpleMapper implements RowMapper<UserInfoBean> {
		public UserInfoBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			UserInfoBean userinfo = new UserInfoBean();
			userinfo.setUser_id(rs.getString("user_id"));
			userinfo.setUser_name(rs.getString("user_name"));
			userinfo.setUser_tel(rs.getString("user_tel"));
			userinfo.setUser_role(rs.getString("user_role"));
			userinfo.setUser_area_wechat_edianzu(rs.getString("user_area_wechat_edianzu"));
			userinfo.setUser_wechat(rs.getString("user_wechat"));
			userinfo.setWcp_area(rs.getString("wcp_area"));
			userinfo.setWcp_head(rs.getString("wcp_head"));
			userinfo.setUser_email(rs.getString("user_email"));
			userinfo.setBank(rs.getString("bank"));
			userinfo.setBank_card_account(rs.getString("bank_card_account"));
			userinfo.setBank_card_place(rs.getString("bank_card_place"));
			userinfo.setBank_card_type(rs.getString("bank_card_type"));
			return userinfo;
		}
	}

	/**
	 * 简单数据模板(无ID,密码,弱电)
	 * 
	 * @author 01059101
	 * 
	 */
	class UserInfoSimplWCPeMapper implements RowMapper<UserInfoBean> {
		public UserInfoBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			UserInfoBean userinfo = new UserInfoBean();
			userinfo.setUser_id(rs.getString("user_id"));
			userinfo.setUser_name(rs.getString("user_name"));
			userinfo.setWcp_area(rs.getString("wcp_area"));
			userinfo.setWcp_head(rs.getString("wcp_head"));
			userinfo.setUser_email(rs.getString("user_email"));
			return userinfo;
		}
	}
	/**
	 * 简单数据模板(用于报销单打印)
	 * 
	 * @author 01059101
	 * 
	 */
	class UserInfoToReimbursementMapper implements RowMapper<UserInfoBean> {
		public UserInfoBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			UserInfoBean userinfo = new UserInfoBean();
			userinfo.setUser_id(rs.getString("user_id"));
			userinfo.setUser_name(rs.getString("user_name"));
			userinfo.setWcp_area(rs.getString("wcp_area"));
			userinfo.setBank(rs.getString("bank"));
			userinfo.setBank_card_account(rs.getString("bank_card_account"));
			userinfo.setBank_card_place(rs.getString("bank_card_place"));
			userinfo.setBank_card_type(rs.getString("bank_card_type"));
			return userinfo;
		}
	}

	/**
	 * 通过用户ID获取用户详细信息
	 * 
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public UserInfoBean queryForInfoBean(String user_id) throws Exception {
		String sql = "select * from userinfo where user_id = ?";
		UserInfoBean userInfoBean = jdbcTemplate.queryForObject(sql,
				new Object[] { user_id }, new UserInfoMapper());
		return userInfoBean;
	}
	/**
	 * 搜索数据，无条件,详细信息
	 * 
	 * @return List<UserInfoBean>
	 */
	public List<UserInfoBean> queryAll() {
		String sql = "select * from userinfo";
		return jdbcTemplate.query(sql, new UserInfoMapper());
	}
	/**
	 * 搜索数据，无条件,详细信息
	 * 
	 * @return List<UserInfoBean>
	 */
	public List<UserInfoBean> queryAll(String user_id) {
		String sql = "select * from userinfo where user_id=?";
		return jdbcTemplate.query(sql, new Object[]{user_id}, new UserInfoMapper());
	}

	/**
	 * 搜索基础数据，无条件,简单数据
	 * 
	 * @return List<UserInfoBean>
	 */
	public List<UserInfoBean> querySimpleUserSimpleInfo() {
		String sql = "select * from userinfo";
		return jdbcTemplate.query(sql, new UserInfoSimpleMapper());
	}

	/**
	 * 搜索数据，有条件,简单数据
	 * 
	 * @param user_id
	 * @return List<UserInfoBean>
	 */
	public List<UserInfoBean> querySimpleUserSimpleInfoByUserId(String user_id) {
		String sql = "select * from userinfo where user_id = ?";
		return jdbcTemplate.query(sql, new Object[] { user_id },
				new UserInfoSimpleMapper());
	}

	/**
	 * 搜索数据，有条件,简单数据
	 * 
	 * 用户地区不为空-微信易点租
	 * @return List<UserInfoBean>
	 */
	public List<UserInfoBean> querySimpleUserInfoByUserAreaIsNotNull() {
		String sql = "SELECT * FROM userinfo WHERE user_area_wechat_edianzu IS NOT NULL AND  user_area_wechat_edianzu <> \"\"";
		return jdbcTemplate.query(sql,new UserInfoSimpleMapper());
	}
	/**
	 * 搜索数据，根据 微信小程序OPENID
	 * 
	 * @param user_id
	 * @return List<UserInfoBean>
	 */
	public List<UserInfoBean> querySimpleUserSimpleInfoByOPENID(String OPENID) {
		String sql = "select * from userinfo where user_wechat = ?";
		return jdbcTemplate.query(sql, new Object[] { OPENID },
				new UserInfoSimpleMapper());
	}
	
	/**
	 * 获取所有符合条件的数据
	 * @param user_id
	 * @param page
	 * @param limit
	 * @return
	 */
	public List<UserInfoBean> queryAllInfoWithSearchConditionToLayui(String user_id,int page,int limit) {
		String sql = "";
		if(user_id.equals("")){
			sql = "SELECT * FROM userinfo limit ? , ?";
			return jdbcTemplate.query(sql, new Object[]{(page - 1)*limit,limit}, new UserInfoMapper());
		}else{
			sql = "SELECT * FROM userinfo  WHERE user_id = ? limit ? , ?";
			return jdbcTemplate.query(sql, new Object[]{user_id,(page - 1)*limit,limit}, new UserInfoSimpleMapper());
		}
		
	}

	/**
	 * 获取所有符合条件的数据行总数
	 * @param user_id
	 * @return
	 */
	public int queryAllInfoCountsBySearchCondition(String user_id) {
		List<UserInfoBean>  bean_list = new ArrayList<UserInfoBean>();
		String sql="";
		if(user_id.equals("")){
			sql = "SELECT * FROM userinfo";
			bean_list=jdbcTemplate.query(sql,  new UserInfoSimpleMapper());
		}else{
			sql = "SELECT * FROM userinfo  WHERE user_id = ?";
			bean_list=jdbcTemplate.query(sql, new Object[]{user_id}, new UserInfoSimpleMapper());
		}
		return bean_list.size();
	}
	

	/**
	 * 获取用户信息
	 * @param user_id
	 * @param password
	 * @return
	 */
	public int queryInfoByUserIdAndPassword(String user_id,String password) {
		List<UserInfoBean>  bean_list = new ArrayList<UserInfoBean>();
		String sql= "SELECT * FROM userinfo  WHERE user_id = ? and user_password = ?";
		bean_list=jdbcTemplate.query(sql, new Object[]{user_id,password}, new UserInfoSimpleMapper());
		return bean_list.size();
	}
	/**
	 * 批量删除
	 * @param list
	 * @return
	 */
	public int batchDeleteByUserId(final List<UserInfoBean> list) {
		try {
			String sql = "DELETE from userinfo where user_id = ?";
			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				public int getBatchSize() {
					return list.size();
				}

				public void setValues(PreparedStatement ps, int i)
						throws SQLException {
					System.out.println("list.get("+i+").getUserId()="+list.get(i).getUser_id());
					ps.setString(1, list.get(i).getUser_id());
				}
			});
			return 0;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 1;
		}
	}
	
	/**
	 * 删除
	 * 
	 * @param user_id
	 * @return false or true
	 */
	public boolean deleteUserInfo(String user_id) {
		String sql = "delete from userinfo where user_id = ?";
		return jdbcTemplate.update(sql, new Object[] { user_id }) == 1;
	}
	
	/**
	 * 批量添加
	 * user_id,user_password,user_name,user_tel
	 * @param userlist
	 */
	public int batchInsertUser(final List<UserInfoBean> userlist) {
		try {
		String v_sql = "insert into userinfo(user_id,user_password,user_name,user_tel,user_role,user_area_wechat_edianzu,wcp_area,wcp_head) values(?,?,?,?,?,?,?,?)";
		jdbcTemplate.batchUpdate(v_sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setString(1, userlist.get(i).getUser_id());
				ps.setString(2, userlist.get(i).getUser_password());
				ps.setString(3, userlist.get(i).getUser_name());
				ps.setString(4, userlist.get(i).getUser_tel());
				ps.setString(5, userlist.get(i).getUser_role());
				ps.setString(6, userlist.get(i).getUser_area_wechat_edianzu());
				ps.setString(7, userlist.get(i).getWcp_area());
				ps.setString(8, userlist.get(i).getWcp_head());
			}

			public int getBatchSize() {
				return userlist.size();
			}
		});
			return 0;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 1;
		}
	}
	
	/**
	 * 添加
	 * @param user_id
	 * @param user_name
	 * @param user_tel
	 * @param user_password
	 * @return
	 */
	public boolean add(String user_id,String user_name,String user_tel,String user_password,String user_role,String user_area_wechat_edianzu,String wcp_area,String wcp_head) {

		String sql = "insert into userinfo(user_id,user_name,user_tel,user_password,user_role,user_area_wechat_edianzu,wcp_area,wcp_head) values(?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, new Object[] { user_id ,user_name,user_tel,user_password,user_role,user_area_wechat_edianzu,wcp_area,wcp_head}) == 1;
	}

	/**
	 * 修改用户密码
	 * 
	 * @param user_id
	 * @return false or true
	 */
	public boolean changeUserPassword(String user_password,String user_id) {
		String sql = "UPDATE userinfo set user_password= ? where user_id = ? ";
		return jdbcTemplate.update(sql, new Object[] {user_password, user_id }) == 1;
	}
	/**
	 * 修改用户角色
	 * 
	 * @param user_id
	 * @return false or true
	 */
	public boolean changeUserRole(String user_role_string,String user_id) {
		String sql = "UPDATE userinfo set user_role = ? where user_id = ? ";
		return jdbcTemplate.update(sql, new Object[] {user_role_string, user_id }) == 1;
	}
	/**
	 * 修改用户手机号码
	 * 
	 * @param user_id
	 * @return false or true
	 */
	public boolean changeUserTel(String user_tel,String user_id) {
		String sql = "UPDATE userinfo set user_tel= ? where user_id = ? ";
		return jdbcTemplate.update(sql, new Object[] {user_tel, user_id }) == 1;
	}
	/**
	 * 修改用户姓名和手机号码
	 * 
	 * @param user_id
	 * @return false or true
	 */
	public boolean changeUserNameAndTel(String user_name,String user_tel,String user_id) {
		String sql = "UPDATE userinfo set user_name=?, user_tel= ? where user_id = ? ";
		return jdbcTemplate.update(sql, new Object[] {user_name,user_tel, user_id }) == 1;
	}

	/**
	 * 修改用户OPENID
	 * 
	 * @param user_id
	 * @return false or true
	 */
	public boolean changeUserOpenId(String OpenId,String user_id) {
		String sql = "UPDATE userinfo set user_wechat= ? where user_id = ? ";
		return jdbcTemplate.update(sql, new Object[] {OpenId, user_id }) == 1;
	}
	/**
	 * 通过用户姓名，来获取用户手机号码
	 * @param user_name
	 * @return
	 */
	public List<UserInfoBean> queryTelInfoWithUserName(String user_name) {
		String sql = "SELECT * FROM userinfo  WHERE user_name in (?)";
	return jdbcTemplate.query(sql, new Object[]{user_name}, new UserInfoSimpleMapper());
		
		
	}
	/**
	 * 通过用户选择的大区，获取所有用户信息
	 * @param user_name
	 * @return
	 */
	public List<UserInfoBean> queryUserByWCPArea(String wcp_area) {
		String sql = "SELECT * FROM userinfo  WHERE wcp_area =?";
	return jdbcTemplate.query(sql, new Object[]{wcp_area}, new UserInfoSimplWCPeMapper());	
	}

	/**
	 * 通过ID判断用户是否为大区负责人
	 * @param user_name
	 * @return
	 
	public List<UserInfoBean> queryUserByWCPAreaAndWCPHead(String wcp_area) {
		String sql = "SELECT * FROM userinfo  WHERE wcp_area = ? AND wcp_head ='Y' ";
	return jdbcTemplate.query(sql, new Object[]{wcp_area}, new UserInfoSimplWCPeMapper());	
	}*/
	/**
	 * 通过ID判断用户是否为大区负责人
	 * @param user_name
	 * @return
	 */
	public boolean queryUserByUserIdAndWCPHead(String user_id) {
		String sql = "SELECT * FROM userinfo  WHERE user_id = ? AND wcp_head ='Y' ";
	return jdbcTemplate.query(sql, new Object[]{user_id}, new UserInfoSimplWCPeMapper()).size()==1;	
	}

	/**
	 * 通过WCP所属地区获取大区负责人
	 * @param user_name
	 * @return
	 */
	public  List<UserInfoBean>  queryWCPHeadByWCPArea(String wcp_area) {
		String sql = "SELECT * FROM userinfo  WHERE wcp_area = ? AND wcp_head ='Y' ";
		System.out.println(sql);
	return jdbcTemplate.query(sql, new Object[]{wcp_area}, new UserInfoSimplWCPeMapper());	
	}
	/**
	 * 获取拥有某个角色的所有用户信息
	 * @param user_role
	 * @return List<UserInfoBean>
	 */
	public List<UserInfoBean> queryUserByUserRole(String user_role) {
		String sql = "SELECT * FROM userinfo WHERE FIND_IN_SET(?, user_role)";
	return jdbcTemplate.query(sql, new Object[]{user_role}, new UserInfoSimplWCPeMapper());	
	}
	/**
	 * 获取拥有某个角色的所有用户信息
	 * @param user_role
	 * @return List<UserInfoBean>
	 */
	public List<UserInfoBean> queryUserInfoToReimbursementByUserId(String user_id) {
		String sql = "SELECT * FROM userinfo WHERE user_id = ?";
	return jdbcTemplate.query(sql, new Object[]{user_id}, new UserInfoToReimbursementMapper());	
	}
	
}
