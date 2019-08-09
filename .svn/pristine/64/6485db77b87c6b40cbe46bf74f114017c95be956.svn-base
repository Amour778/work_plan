package com.workplan.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.workplan.bean.Shnett_EdianzuBean;

public class Shnett_EdianzuDao {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	class ShnettEdianzuMapper implements RowMapper<Shnett_EdianzuBean> {
		public Shnett_EdianzuBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			Shnett_EdianzuBean info = new Shnett_EdianzuBean();
			info.setID(rs.getInt("ID"));
			info.setImages(rs.getString("images"));
			info.setEvent_type(rs.getString("event_type"));
			info.setStartDate(rs.getString("startDate"));
			info.setStartTime(rs.getString("startTime"));
			info.setEndDate(rs.getString("endDate"));
			info.setEndTime(rs.getString("endTime"));
			info.setCompany_name(rs.getString("company_name"));
			info.setRegion_province(rs.getString("region_province"));
			info.setRegion_city(rs.getString("region_city"));
			info.setRegion_area(rs.getString("region_area"));
			info.setCompany_address(rs.getString("company_address"));
			info.setCompany_contacts(rs.getString("company_contacts"));
			info.setCompany_contact_phone(rs.getString("company_contact_phone"));
			info.setOrders_number(rs.getString("orders_number"));
			info.setName_of_engineer(rs.getString("name_of_engineer"));
			info.setNumber_of_inspection(rs.getString("number_of_inspection"));
			info.setInsert_time(rs.getString("insert_time").substring(0, rs.getString("insert_time").length()-2));
			info.setInsert_userid(rs.getString("insert_userid"));
			info.setCourier_number(rs.getString("courier_number"));
			info.setCar_fare(rs.getBigDecimal("car_fare"));
			info.setArea_of_engineer(rs.getString("area_of_engineer"));
			return info;
		}

	}

	class ShnettEdianzuBeanMapper implements RowMapper<Shnett_EdianzuBean> {
		public Shnett_EdianzuBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			Shnett_EdianzuBean info = new Shnett_EdianzuBean();
			info.setID(rs.getInt("ID"));
			info.setImages(rs.getString("images"));
			info.setEvent_type(rs.getString("event_type"));
			info.setStartDate(rs.getString("startDate"));
			info.setStartTime(rs.getString("startTime"));
			info.setEndDate(rs.getString("endDate"));
			info.setEndTime(rs.getString("endTime"));
			info.setCompany_name(rs.getString("company_name"));
			info.setRegion_province(rs.getString("region_province"));
			info.setRegion_city(rs.getString("region_city"));
			info.setRegion_area(rs.getString("region_area"));
			info.setCompany_address(rs.getString("company_address"));
			info.setCompany_contacts(rs.getString("company_contacts"));
			info.setCompany_contact_phone(rs.getString("company_contact_phone"));
			info.setOrders_number(rs.getString("orders_number"));
			info.setNumber_of_inspection(rs.getString("number_of_inspection"));
			info.setInsert_time(rs.getString("insert_time").substring(0, rs.getString("insert_time").length()-2));
			info.setInsert_userid(rs.getString("insert_userid"));
			info.setCourier_number(rs.getString("courier_number"));
			info.setCar_fare(rs.getBigDecimal("car_fare"));
			return info;
		}

	}
	class ShnettEdianzuSimpleMapper implements RowMapper<Shnett_EdianzuBean> {
		public Shnett_EdianzuBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			Shnett_EdianzuBean info = new Shnett_EdianzuBean();
			info.setID(rs.getInt("ID"));
			info.setEvent_type(rs.getString("event_type"));
			info.setStartDate(rs.getString("startDate"));
			info.setStartTime(rs.getString("startTime"));
			info.setEndDate(rs.getString("endDate"));
			info.setEndTime(rs.getString("endTime"));
			info.setCompany_name(rs.getString("company_name"));
			return info;
		}

	}

	
	/**
	 * 添加新数据
	 * @param images
	 * @param event_type
	 * @param startDate
	 * @param startTime
	 * @param endDate
	 * @param endTime
	 * @param company_name
	 * @param company_address
	 * @param company_contacts
	 * @param company_contact_phone
	 * @param orders_number
	 * @param number_of_inspection
	 * @param insert_userid
	 * @param courier_number
	 * @param car_fare
	 * @return
	 */
	public boolean insert(String images,
			String event_type,
			String startDate,
			String startTime,
			String endDate,
			String endTime,
			String company_name,
			String region_province,
			String region_city,
			String region_area,
			String company_address,
			String company_contacts,
			String company_contact_phone,
			String orders_number,
			String number_of_inspection,
			String insert_userid,
			String courier_number,
			String car_fare) {
		String sql = "insert into shnett_edianzu(images," +
				"event_type," +
				"startDate," +
				"startTime," +
				"endDate," +
				"endTime," +
				"company_name," +
				"region_province," +
				"region_city," +
				"region_area," +
				"company_address," +
				"company_contacts," +
				"company_contact_phone," +
				"orders_number," +
				"number_of_inspection," +
				"insert_userid," +
				"courier_number," +
				"car_fare) values(" +
				"?,?,?,?,?," +
				"?,?,?,?,?," +
				"?,?,?,?,?," +
				"?,?,?)";
		return jdbcTemplate.update(sql, new Object[] {images,event_type,startDate,startTime,endDate,endTime,company_name,region_province,region_city,region_area,
				company_address,company_contacts,company_contact_phone,orders_number,number_of_inspection,insert_userid,courier_number,car_fare}) == 1;
	}
	String SQL="SELECT "+
	"shnett_edianzu.ID, "+
	"shnett_edianzu.images, "+
	"shnett_edianzu.event_type, "+
	"shnett_edianzu.startDate, "+
	"shnett_edianzu.startTime, "+
	"shnett_edianzu.endDate, "+
	"shnett_edianzu.endTime, "+
	"shnett_edianzu.company_name, "+
	"shnett_edianzu.region_province, "+
	"shnett_edianzu.region_city, "+
	"shnett_edianzu.region_area, "+
	"shnett_edianzu.company_address, "+
	"shnett_edianzu.company_contacts, "+
	"shnett_edianzu.company_contact_phone, "+
	"shnett_edianzu.orders_number, "+
	"shnett_edianzu.courier_number, "+
	"shnett_edianzu.number_of_inspection, "+
	"shnett_edianzu.insert_time, "+
	"shnett_edianzu.insert_userid, "+
	"shnett_edianzu.car_fare, "+
	"userinfo.user_area_wechat_edianzu AS area_of_engineer, "+
	"userinfo.user_name AS name_of_engineer "+
	"FROM "+
	"shnett_edianzu LEFT JOIN userinfo ON shnett_edianzu.insert_userid = userinfo.user_id "+
	"WHERE "+
	"1 = 1 AND " ;
	/**
	 * 下拉框搜索：数据
	 * @param USER_AREA
	 * @param EVENT_TYPE
	 * @param USER_ID
	 * @param StartDate
	 * @param EndDate
	 * @param page
	 * @param limit
	 * @return
	 */
	public List<Shnett_EdianzuBean> queryAllInfoBySearchCondition(
			ArrayList<String> USER_AREA,
			ArrayList<String> EVENT_TYPE,
			ArrayList<String> USER_ID,
			String StartDate,
			String EndDate,
			int page,int limit) {
		String sql = SQL;
		if(USER_AREA.size()==1){
			sql+=" userinfo.user_area_wechat_edianzu = \""+USER_AREA.get(0).toString()+"\" And ";
		}else if (USER_AREA.size()>1) {
			sql+="(";
			for(int a= 0;a<USER_AREA.size();a++){
				sql+=" userinfo.user_area_wechat_edianzu = \""+USER_AREA.get(a).toString()+"\" or ";
			}
			sql=sql.substring(0,sql.length()-3)+") And ";
		}

		if(EVENT_TYPE.size()==1){
			sql+=" shnett_edianzu.event_type = \""+EVENT_TYPE.get(0).toString()+"\" And ";
		}else if (EVENT_TYPE.size()>1) {
			sql+="(";
			for(int a= 0;a<EVENT_TYPE.size();a++){
				sql+=" shnett_edianzu.event_type = \""+EVENT_TYPE.get(a).toString()+"\" or ";
			}
			sql=sql.substring(0,sql.length()-3)+") And ";
		}

		if(USER_ID.size()==1){
			sql+=" shnett_edianzu.insert_userid = \""+USER_ID.get(0).toString()+"\" And ";
		}else if (USER_ID.size()>1) {
			sql+="(";
			for(int a= 0;a<USER_ID.size();a++){
				sql+=" shnett_edianzu.insert_userid = \""+USER_ID.get(a).toString()+"\" or ";
			}
			sql=sql.substring(0,sql.length()-3)+") And ";
		}
		
		if(StartDate!=null&&EndDate!=null){
			sql+="( shnett_edianzu.startDate between \""+StartDate+"\" And \""+EndDate+"\"";
			sql+=" AND shnett_edianzu.endDate between \""+StartDate+"\" And \""+EndDate+"\" ) And";
		}
		
		sql+=" 1=1  ORDER BY ID DESC limit ? , ? ";
		System.out.println(sql);
		return jdbcTemplate.query(sql, new Object[]{(page - 1)*limit,limit}, new ShnettEdianzuMapper());
	}

	/**
	 * 下拉框搜索：数据总行数
	 * @return List<WeakCurrentProjectsSimpleBean>
	 */
	public int queryAllInfoCountsBySearchCondition(
			ArrayList<String> USER_AREA,
			ArrayList<String> EVENT_TYPE,
			ArrayList<String> USER_ID,
			String StartDate,
			String EndDate) {
		String sql = SQL;
		if(USER_AREA.size()==1){
			sql+=" userinfo.user_area_wechat_edianzu = \""+USER_AREA.get(0).toString()+"\" And ";
		}else if (USER_AREA.size()>1) {
			sql+="(";
			for(int a= 0;a<USER_AREA.size();a++){
				sql+=" userinfo.user_area_wechat_edianzu = \""+USER_AREA.get(a).toString()+"\" or ";
			}
			sql=sql.substring(0,sql.length()-3)+") And ";
		}

		if(EVENT_TYPE.size()==1){
			sql+=" shnett_edianzu.event_type = \""+EVENT_TYPE.get(0).toString()+"\" And ";
		}else if (EVENT_TYPE.size()>1) {
			sql+="(";
			for(int a= 0;a<EVENT_TYPE.size();a++){
				sql+=" shnett_edianzu.event_type = \""+EVENT_TYPE.get(a).toString()+"\" or ";
			}
			sql=sql.substring(0,sql.length()-3)+") And ";
		}

		if(USER_ID.size()==1){
			sql+=" shnett_edianzu.insert_userid = \""+USER_ID.get(0).toString()+"\" And ";
		}else if (USER_ID.size()>1) {
			sql+="(";
			for(int a= 0;a<USER_ID.size();a++){
				sql+=" shnett_edianzu.insert_userid = \""+USER_ID.get(a).toString()+"\" or ";
			}
			sql=sql.substring(0,sql.length()-3)+") And ";
		}
		
		if(StartDate!=null&&EndDate!=null){
			sql+="( shnett_edianzu.startDate between \""+StartDate+"\" And \""+EndDate+"\"";
			sql+=" AND shnett_edianzu.endDate between \""+StartDate+"\" And \""+EndDate+"\" ) And";
		}
		sql+=" 1=1 ";
		System.out.println(sql);
		List<Shnett_EdianzuBean>  _list = new ArrayList<Shnett_EdianzuBean>();
		_list=jdbcTemplate.query(sql,  new ShnettEdianzuMapper());
		int counts=0;
		counts=_list.size();
		return counts;
	}
	/**
	 * 导出时的搜索：数据，去掉limit限制
	 * @param USER_AREA
	 * @param EVENT_TYPE
	 * @param USER_ID
	 * @param StartDate
	 * @param EndDate
	 * @param page
	 * @param limit
	 * @return
	 */
	public List<Shnett_EdianzuBean> queryAllInfoBySearchConditionToExcelExport(
			ArrayList<String> USER_AREA,
			ArrayList<String> EVENT_TYPE,
			ArrayList<String> USER_ID,
			String StartDate,
			String EndDate) {
		String sql = SQL;
		if(USER_AREA.size()==1){
			sql+=" userinfo.user_area_wechat_edianzu = \""+USER_AREA.get(0).toString()+"\" And ";
		}else if (USER_AREA.size()>1) {
			sql+="(";
			for(int a= 0;a<USER_AREA.size();a++){
				sql+=" userinfo.user_area_wechat_edianzu = \""+USER_AREA.get(a).toString()+"\" or ";
			}
			sql=sql.substring(0,sql.length()-3)+") And ";
		}

		if(EVENT_TYPE.size()==1){
			sql+=" shnett_edianzu.event_type = \""+EVENT_TYPE.get(0).toString()+"\" And ";
		}else if (EVENT_TYPE.size()>1) {
			sql+="(";
			for(int a= 0;a<EVENT_TYPE.size();a++){
				sql+=" shnett_edianzu.event_type = \""+EVENT_TYPE.get(a).toString()+"\" or ";
			}
			sql=sql.substring(0,sql.length()-3)+") And ";
		}

		if(USER_ID.size()==1){
			sql+=" shnett_edianzu.insert_userid = \""+USER_ID.get(0).toString()+"\" And ";
		}else if (USER_ID.size()>1) {
			sql+="(";
			for(int a= 0;a<USER_ID.size();a++){
				sql+=" shnett_edianzu.insert_userid = \""+USER_ID.get(a).toString()+"\" or ";
			}
			sql=sql.substring(0,sql.length()-3)+") And ";
		}
		
		if(StartDate!=null&&EndDate!=null){
			sql+="( shnett_edianzu.startDate between \""+StartDate+"\" And \""+EndDate+"\"";
			sql+=" AND shnett_edianzu.endDate between \""+StartDate+"\" And \""+EndDate+"\" ) And";
		}
		
		sql+=" 1=1  ORDER BY ID DESC ";
		System.out.println(sql);
		return jdbcTemplate.query(sql, new ShnettEdianzuMapper());
	}

	/**
	 * 微信小程序api-搜索用户最近几条数据
	 * @param USER_ID
	 * @param page
	 * @param limit
	 * @return
	 */
	public List<Shnett_EdianzuBean> queryAllInfoByUserIDWithLimit(String USER_ID,int page,int limit) {
		String sql ="select * FROM shnett_edianzu where insert_userid =? ORDER BY ID DESC limit ? , ? ";
		System.out.println(sql);
		return jdbcTemplate.query(sql, new Object[]{USER_ID,(page - 1)*limit,limit}, new ShnettEdianzuSimpleMapper());
	}
	/**
	 * 微信小程序api-根据ID搜索用户的特定某条数据
	 * @param USER_ID
	 * @param page
	 * @param limit
	 * @return
	 */
	public List<Shnett_EdianzuBean> queryOneInfoByID(String USER_ID,String ID) {
		String sql ="select * FROM shnett_edianzu where insert_userid = ? And ID = ?";
		System.out.println(sql);
		return jdbcTemplate.query(sql, new Object[]{USER_ID,ID}, new ShnettEdianzuBeanMapper());
	}
	/**
	 * 微信小程序api-根据ID、userid删除数据
	 * @param USER_ID
	 * @param page
	 * @param limit
	 * @return
	 */
	public boolean deleteOneInfoByUSER_IDAndID(String USER_ID,String ID) {
		String sql ="delete from shnett_edianzu where  insert_userid = ? And ID = ?";
		System.out.println(sql);
		return jdbcTemplate.update(sql, new Object[] {USER_ID,ID}) == 1;
	}
	/**
	 * 微信小程序api-根据日期段搜索用户的特定某条数据
	 * @param USER_ID
	 * @param StartDate
	 * @param EndDate
	 * @return
	 */
	public List<Shnett_EdianzuBean> queryAllInfoByStartDateAndEndDate(String USER_ID,String StartDate,String EndDate) {
		String sql ="select * FROM shnett_edianzu where insert_userid = ? And ( (startDate between ? And ? ) AND ( endDate between ? And ?) )ORDER BY ID ";
		System.out.println(sql);
		return jdbcTemplate.query(sql, new Object[]{USER_ID,StartDate,EndDate,StartDate,EndDate}, new ShnettEdianzuBeanMapper());
	}

	public boolean updataOneInfoByUserIdAndID(String images, String event_type,
			String startDate, String startTime, String endDate, String endTime,
			String company_name, String region_province, String region_city,
			String region_area, String company_address,
			String company_contacts, String company_contact_phone,
			String orders_number, String number_of_inspection,
			String courier_number, String car_fare, String insert_userid,
			String ID) {
		String sql = "UPDATE shnett_edianzu set images=?," +
				"event_type=?," +
				"startDate=?," +
				"startTime=?," +
				"endDate=?," +
				"endTime=?," +
				"company_name=?," +
				"region_province=?," +
				"region_city=?," +
				"region_area=?," +
				"company_address=?," +
				"company_contacts=?," +
				"company_contact_phone=?," +
				"orders_number=?," +
				"number_of_inspection=?," +
				"courier_number=?," +
				"car_fare=? where " +
				"insert_userid=? and ID = ?";
		return jdbcTemplate.update(sql, new Object[] { images, event_type,
				startDate, startTime, endDate, endTime, company_name,
				region_province, region_city, region_area, company_address,
				company_contacts, company_contact_phone, orders_number,
				number_of_inspection, courier_number, car_fare, insert_userid,
				ID }) == 1;
	}
	
}
