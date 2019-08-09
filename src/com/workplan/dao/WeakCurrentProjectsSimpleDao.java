package com.workplan.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.workplan.bean.WeakCurrentProjectsSimpleBean;


public class WeakCurrentProjectsSimpleDao {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 数据模板：所有数据
	 * @author 01059101
	 *
	 */
	class WeakCurrentProjectsSimpleAllMapper implements RowMapper<WeakCurrentProjectsSimpleBean> {
		public WeakCurrentProjectsSimpleBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WeakCurrentProjectsSimpleBean info = new WeakCurrentProjectsSimpleBean();
			info.setId(rs.getInt("id"));
			info.setProject_code(rs.getString("project_code"));
			info.setProject_area_department(rs.getString("project_area_department"));
			info.setDistrict_name(rs.getString("district_name"));
			info.setItem_classification(rs.getString("item_classification"));
			info.setUstomer_name(rs.getString("ustomer_name"));
			info.setPlanned_start_time(rs.getString("planned_start_time"));
			info.setPlanned_end_time(rs.getString("planned_end_time"));
			info.setProject_name(rs.getString("project_name"));
			info.setProject_cycle(rs.getString("project_cycle"));
			info.setProject_quotation(rs.getBigDecimal("project_quotation"));
			info.setProject_leader(rs.getString("project_leader"));
			info.setMaterial(rs.getBigDecimal("material"));
			info.setLabor(rs.getBigDecimal("labor"));
			info.setAllocation_of_fixed_assets(rs.getBigDecimal("allocation_of_fixed_assets"));
			info.setOther_items(rs.getBigDecimal("other_items"));
			info.setSubtotal_cost(rs.getBigDecimal("subtotal_cost"));
			info.setSimple_tax(rs.getBigDecimal("simple_tax"));
			info.setSubmitter(rs.getString("submitter"));
			info.setSubmission_time(rs.getString("submission_time"));
			info.setAudit_status(rs.getInt("audit_status"));
			info.setAudit_info(rs.getString("audit_info"));
			info.setIsclose(rs.getInt("isclose"));
			info.setPredict_gross_profit(rs.getBigDecimal("predict_gross_profit"));
			info.setPredict_gross_profit_per(rs.getBigDecimal("predict_gross_profit_per"));
			info.setPredict_net_profit(rs.getBigDecimal("predict_net_profit"));
			info.setPredict_net_profit_per(rs.getBigDecimal("predict_net_profit_per"));
			info.setSpecial_vat_invoice_amount(rs.getBigDecimal("special_vat_invoice_amount"));
			info.setOrdinary_invoice_amount(rs.getBigDecimal("ordinary_invoice_amount"));
			info.setSupplier_invoice_tax(rs.getBigDecimal("supplier_invoice_tax"));
			info.setSupplier_invoice(rs.getBigDecimal("supplier_invoice"));
			info.setEdit_remark(rs.getString("edit_remark"));
			return info;
		}

	}
	/**
	 * 数据模板：项目名称
	 * @author 01059101
	 *
	 */
	class WeakCurrentProjectsSimpleOnlyProjectName implements RowMapper<WeakCurrentProjectsSimpleBean> {
		public WeakCurrentProjectsSimpleBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WeakCurrentProjectsSimpleBean info = new WeakCurrentProjectsSimpleBean();
			info.setProject_code(rs.getString("project_code"));
			info.setProject_name(rs.getString("project_name"));
			info.setProject_leader(rs.getString("project_leader"));
			return info;
		}
	}
	/**
	 * 数据模板：审核状态
	 * @author 01059101
	 *
	 */
	class WeakCurrentProjectsSimpleAuditStatus implements RowMapper<WeakCurrentProjectsSimpleBean> {
		public WeakCurrentProjectsSimpleBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WeakCurrentProjectsSimpleBean info = new WeakCurrentProjectsSimpleBean();
			info.setAudit_status(rs.getInt("audit_status"));
			return info;
		}
	}
	

	/**
	 * 获取项目审批状态
	 * @return List<WeakCurrentProjectsSimpleBean>
	 */
	public int WCPSAuditStatus(String project_code) {
		String sql =  "SELECT audit_status FROM weak_current_projects_simple WHERE project_code =  ?";
		System.out.println(sql);
		List<WeakCurrentProjectsSimpleBean> list=jdbcTemplate.query(sql, new Object[]{project_code}, new WeakCurrentProjectsSimpleAuditStatus());
		int audit_status=0;
		try {
			audit_status=list.get(0).getAudit_status();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return audit_status;
	}

	
	/**
	 * 立项、添加历史记录
	 * @param bean
	 * @return
	 */
	public int[] insertWeakCurrentProjectsSimple(WeakCurrentProjectsSimpleBean bean) {
		String[] sql = new String[2];
		sql[0]="INSERT INTO weak_current_projects_simple(project_code,project_area_department,district_name,item_classification,ustomer_name," +
				"planned_start_time,planned_end_time,project_name,project_cycle,project_quotation," +
				"project_leader,material,labor,allocation_of_fixed_assets,other_items,subtotal_cost,simple_tax,submitter," +
				"predict_gross_profit,predict_gross_profit_per,predict_net_profit,predict_net_profit_per," +
				"special_vat_invoice_amount,ordinary_invoice_amount,supplier_invoice_tax,supplier_invoice,edit_remark)VALUES('"+bean.getProject_code()+"','"+bean.getProject_area_department()+"','"+bean.getDistrict_name()+"','"+bean.getItem_classification()+"','"+bean.getUstomer_name()+"','"+bean.getPlanned_start_time()+"','"+bean.getPlanned_end_time()+"','"+bean.getProject_name()+"','"+bean.getProject_cycle()+"','"+bean.getProject_quotation()+"','"+bean.getProject_leader()+"','"+bean.getMaterial()+"','"+bean.getLabor()+"','"+bean.getAllocation_of_fixed_assets()+"','"+bean.getOther_items()+"','"+bean.getSubtotal_cost()+"','"+bean.getSimple_tax()+"','"+bean.getSubmitter()+"','"+bean.getPredict_gross_profit()+"','"+bean.getPredict_gross_profit_per()+"','"+bean.getPredict_net_profit()+"','"+bean.getPredict_net_profit_per()+"','"+bean.getSpecial_vat_invoice_amount()+"','"+bean.getOrdinary_invoice_amount()+"','"+bean.getSupplier_invoice_tax()+"','"+bean.getSupplier_invoice()+"','"+bean.getEdit_remark()+"')";
		sql[1]="INSERT INTO weak_current_projects_simple_history(project_code,project_area_department,district_name,item_classification,ustomer_name," +
		"planned_start_time,planned_end_time,project_name,project_cycle,project_quotation," +
		"project_leader,material,labor,allocation_of_fixed_assets,other_items,subtotal_cost,simple_tax,submitter," +
		"predict_gross_profit,predict_gross_profit_per,predict_net_profit,predict_net_profit_per," +
		"special_vat_invoice_amount,ordinary_invoice_amount,supplier_invoice_tax,supplier_invoice,edit_remark)VALUES('"+bean.getProject_code()+"','"+bean.getProject_area_department()+"','"+bean.getDistrict_name()+"','"+bean.getItem_classification()+"','"+bean.getUstomer_name()+"','"+bean.getPlanned_start_time()+"','"+bean.getPlanned_end_time()+"','"+bean.getProject_name()+"','"+bean.getProject_cycle()+"','"+bean.getProject_quotation()+"','"+bean.getProject_leader()+"','"+bean.getMaterial()+"','"+bean.getLabor()+"','"+bean.getAllocation_of_fixed_assets()+"','"+bean.getOther_items()+"','"+bean.getSubtotal_cost()+"','"+bean.getSimple_tax()+"','"+bean.getSubmitter()+"','"+bean.getPredict_gross_profit()+"','"+bean.getPredict_gross_profit_per()+"','"+bean.getPredict_net_profit()+"','"+bean.getPredict_net_profit_per()+"','"+bean.getSpecial_vat_invoice_amount()+"','"+bean.getOrdinary_invoice_amount()+"','"+bean.getSupplier_invoice_tax()+"','"+bean.getSupplier_invoice()+"','"+bean.getEdit_remark()+"')";
		return jdbcTemplate.batchUpdate(sql);
	}

	/**
	 * 下拉框搜索：数据
	 * @return List<WeakCurrentProjectsSimpleBean>
	 */
	public List<WeakCurrentProjectsSimpleBean> queryAllInfoWithFirstBySearchCondition(ArrayList<String> AuditStatus,ArrayList<String> ProjectCode,
			int page,int limit, String submitter,String project_area_department) {
		String sql =  "SELECT * FROM weak_current_projects_simple WHERE";
		if(AuditStatus.size()==1){
			sql+=" audit_status = "+AuditStatus.get(0).toString()+" And ";
		}else if (AuditStatus.size()>1) {
			sql+="(";
			for(int a= 0;a<AuditStatus.size();a++){
				sql+=" audit_status = "+AuditStatus.get(a).toString()+" or ";
			}
			sql=sql.substring(0,sql.length()-3)+") And ";
		}

		if(ProjectCode.size()==1){
			sql+=" project_code = '"+ProjectCode.get(0).toString()+"' And ";
		}else if (ProjectCode.size()>1) {
			sql+="(";
			for(int a= 0;a<ProjectCode.size();a++){
				sql+=" project_code = '"+ProjectCode.get(a).toString()+"' or ";
			}
			sql=sql.substring(0,sql.length()-3)+") And ";
		}

		if(submitter!=null){
			sql+="( submitter = '"+submitter+"' OR project_leader='"+submitter+"' ) And ";
		}
		if(project_area_department!=null){
			sql+="( project_area_department = '"+project_area_department+"' ) And ";
		}
		sql+=" 1=1  limit ? , ?";
		System.out.println(sql);
		return jdbcTemplate.query(sql, new Object[]{(page - 1)*limit,limit}, new WeakCurrentProjectsSimpleAllMapper());
	}

	/**
	 * 下拉框搜索：数据总行数
	 * @return List<WeakCurrentProjectsSimpleBean>
	 */
	public int queryAllInfoCountsBySearchCondition(ArrayList<String> AuditStatus,ArrayList<String> ProjectCode , String submitter,String project_area_department) {
		String sql =  "SELECT "+
						"*"+
					" FROM "+
						" weak_current_projects_simple "+
					" WHERE ";
		if(AuditStatus.size()==1){
			sql+=" audit_status = "+AuditStatus.get(0).toString()+" And ";
		}else if (AuditStatus.size()>1) {
			sql+="(";
			for(int a= 0;a<AuditStatus.size();a++){
				sql+=" audit_status = "+AuditStatus.get(a).toString()+" or ";
			}
			sql=sql.substring(0,sql.length()-3)+") And ";
		}
		if(ProjectCode.size()==1){
			sql+=" project_code = '"+ProjectCode.get(0).toString()+"' And ";
		}else if (ProjectCode.size()>1) {
			sql+="(";
			for(int a= 0;a<ProjectCode.size();a++){
				sql+=" project_code = '"+ProjectCode.get(a).toString()+"' or ";
			}
			sql=sql.substring(0,sql.length()-3)+") And ";
		}
		
		if(submitter!=null){
			sql+="( submitter = '"+submitter+"' OR project_leader='"+submitter+"' ) And ";
		}
		if(project_area_department!=null){
			sql+="( project_area_department = '"+project_area_department+"' ) And ";
		}
		sql+=" 1=1";
		System.out.println(sql);
		List<WeakCurrentProjectsSimpleBean>  second_list = new ArrayList<WeakCurrentProjectsSimpleBean>();
		second_list=jdbcTemplate.query(sql,  new WeakCurrentProjectsSimpleAllMapper());
		int counts=0;
		counts=second_list.size();
		return counts;
	}
	
	/**
	 * 下拉框搜索：数据
	 * @return List<WeakCurrentProjectsSimpleBean>
	 */
	public List<WeakCurrentProjectsSimpleBean> queryAllProjectName() {
		String sql =  "SELECT * FROM weak_current_projects_simple where audit_status != 1030";
		System.out.println(sql);
		return jdbcTemplate.query(sql, new WeakCurrentProjectsSimpleOnlyProjectName());
	}
	
	/**
	 * 搜索：通过project_code获取所有数据
	 * @return List<WeakCurrentProjectsSimpleBean>
	 */
	public List<WeakCurrentProjectsSimpleBean> queryProjectInfoByProjectCode(String project_code) {
		String sql =  "SELECT * FROM weak_current_projects_simple where project_code= ?";
		System.out.println(sql);
		return jdbcTemplate.query(sql, new Object[]{project_code}, new WeakCurrentProjectsSimpleAllMapper());
	}

	
	/**
	 * 搜索History：通过project_code获取所有数据
	 * @return List<WeakCurrentProjectsSimpleBean>
	 */
	public List<WeakCurrentProjectsSimpleBean> queryHistoryProjectInfoByProjectCode(String project_code) {
		String sql =  "SELECT * FROM weak_current_projects_simple_history where project_code= ?";
		System.out.println(sql);
		return jdbcTemplate.query(sql, new Object[]{project_code}, new WeakCurrentProjectsSimpleAllMapper());
	}
	

	/**
	 * 根据project_code更新所有数据
	 * @param bean
	 * @param project_code
	 * @return
	 */
	public boolean updataWeakCurrentProjectsSimpleInfoByProjectCode(WeakCurrentProjectsSimpleBean bean,String project_code) {
		String sql = "UPDATE weak_current_projects_simple set " +
					"project_area_department=?,"+
					"district_name=?,"+
					"item_classification=?,"+
					"ustomer_name=?,"+
					"planned_start_time=?,"+
					"planned_end_time=?,"+
					"project_name=?,"+
					"project_cycle=?,"+
					"project_quotation=?,"+
					"project_leader=?,"+
					"material=?,"+
					"labor=?,"+
					"allocation_of_fixed_assets=?,"+
					"other_items=?,"+
					"subtotal_cost=?,"+
					"simple_tax=?,"+
					"submitter=?,"+
					"submission_time=?,"+
					"audit_status=?,"+
					"audit_info=?,"+
					"isclose=?,"+
					"predict_gross_profit=?,"+
					"predict_gross_profit_per=?,"+
					"predict_net_profit=?,"+
					"predict_net_profit_per=?,"+
					"special_vat_invoice_amount=?,"+
					"ordinary_invoice_amount=?,"+
					"supplier_invoice_tax=?,"+
					"supplier_invoice=?," +
					"edit_remark=? "+
				" where " +
				"project_code = ? ";
		return jdbcTemplate.update(sql, new Object[] {
				bean.getProject_area_department(),
				bean.getDistrict_name(),
				bean.getItem_classification(),
				bean.getUstomer_name(),
				bean.getPlanned_start_time(),
				bean.getPlanned_end_time(),
				bean.getProject_name(),
				bean.getProject_cycle(),
				bean.getProject_quotation(),
				bean.getProject_leader(),
				bean.getMaterial(),
				bean.getLabor(),
				bean.getAllocation_of_fixed_assets(),
				bean.getOther_items(),
				bean.getSubtotal_cost(),
				bean.getSimple_tax(),
				bean.getSubmitter(),
				bean.getSubmission_time(),
				bean.getAudit_status(),
				bean.getAudit_info(),
				bean.getIsclose(),
				bean.getPredict_gross_profit(),
				bean.getPredict_gross_profit_per(),
				bean.getPredict_net_profit(),
				bean.getPredict_net_profit_per(),
				bean.getSpecial_vat_invoice_amount(),
				bean.getOrdinary_invoice_amount(),
				bean.getSupplier_invoice_tax(),
				bean.getSupplier_invoice(),
				bean.getEdit_remark(),
				project_code }) == 1;
	}
	
	/**
	 * 根据project_code更新审核数据
	 * @param audit_status
	 * @param audit_info 默认为N
	 * @param project_code
	 * @return
	 */
	public boolean updataWeakCurrentProjectsSimpleAuditStatusByProjectCode(String audit_status ,String audit_info ,String project_code) {
		String sql = "UPDATE weak_current_projects_simple set audit_status= ?, audit_info= ? where  project_code = ? ";
		System.out.println(sql);
		return jdbcTemplate.update(sql, new Object[] {audit_status,audit_info, project_code }) == 1;
	}
	
	/**
	 * 获取最后一个数据的编码
	 * @return List<WeakCurrentProjectsSimpleBean>
	 */
	public List<WeakCurrentProjectsSimpleBean> queryInfoDesc() {
		String sql =  "SELECT * FROM weak_current_projects_simple ORDER BY id DESC";
		System.out.println(sql);
		return jdbcTemplate.query(sql, new WeakCurrentProjectsSimpleOnlyProjectName());
	}
	
	/**
	 * 判断项目编码是否存在
	 * @return List<WeakCurrentProjectsSimpleBean>
	 */
	public List<WeakCurrentProjectsSimpleBean> queryInfoDescToCode(String project_code) {
		String sql =  "SELECT project_code,project_name,project_leader FROM weak_current_projects_simple where project_code = ?";
		System.out.println(sql);
		return jdbcTemplate.query(sql,new Object[]{project_code}, new WeakCurrentProjectsSimpleOnlyProjectName());
	}
	
	/**
	 * 撤项：删除 除weak_current_projects_simple表 之外的所有数据
	 * @param project_code
	 * @return
	 */
	public int[] withdrawal_item(String project_code,int audit_status ,String audit_info) {
		String[] sql=new String[2];
	sql[0]="DELETE weak_current_projects_detail, "+
					"weak_current_projects_simple_history, "+
					"wcp_actual_expenditure, "+
					"wcp_bonus_shares "+
					"FROM "+
					"weak_current_projects_detail "+
					"LEFT JOIN weak_current_projects_simple_history ON weak_current_projects_detail.project_code = weak_current_projects_simple_history.project_code "+
					"LEFT JOIN wcp_actual_expenditure ON weak_current_projects_detail.project_code = wcp_actual_expenditure.project_code "+
					"LEFT JOIN wcp_bonus_shares ON weak_current_projects_detail.project_code = wcp_bonus_shares.project_code "+
					"WHERE "+
					"weak_current_projects_detail.project_code = '"+project_code+"' ";
	sql[1]="UPDATE weak_current_projects_simple set audit_status= "+audit_status+", audit_info= '"+audit_info+"' where  project_code = '"+project_code+"' ";
	return  jdbcTemplate.batchUpdate(sql);
	}
}

