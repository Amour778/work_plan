package com.workplan.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.workplan.bean.WeakCurrentProjectsActualExpenditureBean;
public class WeakCurrentProjectsActualExpenditureDao {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	String SQL="SELECT "+
				" weak_current_projects_simple.project_code, "+
				" weak_current_projects_simple.item_classification, "+
				" weak_current_projects_simple.project_name, "+
				" weak_current_projects_simple.project_leader, "+
				" weak_current_projects_simple.project_area_department, "+
				" wcp_actual_expenditure.actual_id, "+
				" wcp_actual_expenditure.group_id, "+
				" wcp_actual_expenditure.project_code, "+
				//" wcp_actual_expenditure.cost_department, "+
				" wcp_actual_expenditure.cost_matters, "+
				" wcp_actual_expenditure.amount_in_original_currency, "+
				" wcp_actual_expenditure.idle_stock, "+
				" wcp_actual_expenditure.tax_rate, "+
				" wcp_actual_expenditure.amount_of_tax, "+
				" wcp_actual_expenditure.principal, "+
				" wcp_actual_expenditure.bill_type, "+
				" wcp_actual_expenditure.invoice_number, "+
				" wcp_actual_expenditure.date_of_occurrence, "+
				" wcp_actual_expenditure.purpose_of_occurrence, "+
				" wcp_actual_expenditure.reimbursement_number, "+
				" wcp_actual_expenditure.name_of_applicant, "+
				" wcp_actual_expenditure.applicant_department, "+
				" wcp_actual_expenditure.date_of_application, "+
				" wcp_actual_expenditure.audit_status, "+
				" wcp_actual_expenditure.audit_info, "+
				" wcp_actual_expenditure.edit_remark "+
				" FROM "+
				" weak_current_projects_simple , "+
				" wcp_actual_expenditure "+
				" WHERE "+
				" weak_current_projects_simple.project_code = wcp_actual_expenditure.project_code AND " ;
	/**
	 * 数据模板：所有数据
	 * @author 01059101
	 *
	 */
	class WeakCurrentProjectsActualExpenditureAllMapper implements RowMapper<WeakCurrentProjectsActualExpenditureBean> {
		public WeakCurrentProjectsActualExpenditureBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WeakCurrentProjectsActualExpenditureBean info = new WeakCurrentProjectsActualExpenditureBean();
			info.setGroup_id(rs.getString("group_id"));
			info.setActual_id(rs.getString("actual_id"));
			info.setProject_code(rs.getString("project_code"));
			info.setProject_name(rs.getString("project_name"));
			info.setProject_area_department(rs.getString("project_area_department"));
			//info.setCost_department(rs.getString("cost_department"));
			info.setItem_classification(rs.getString("item_classification"));
			info.setCost_matters(rs.getString("cost_matters"));
			info.setAmount_in_original_currency(rs.getBigDecimal("amount_in_original_currency"));
			info.setIdle_stock(rs.getBigDecimal("idle_stock"));
			info.setTax_rate(rs.getBigDecimal("tax_rate"));
			info.setAmount_of_tax(rs.getBigDecimal("amount_of_tax"));
			info.setPrincipal(rs.getBigDecimal("principal"));
			info.setBill_type(rs.getString("bill_type"));
			info.setInvoice_number(rs.getString("invoice_number"));
			info.setDate_of_occurrence(rs.getString("date_of_occurrence"));
			info.setPurpose_of_occurrence(rs.getString("purpose_of_occurrence"));
			info.setReimbursement_number(rs.getString("reimbursement_number"));
			info.setName_of_applicant(rs.getString("name_of_applicant"));
			info.setApplicant_department(rs.getString("applicant_department"));
			info.setDate_of_application(rs.getString("date_of_application"));
			info.setProject_leader(rs.getString("project_leader"));
			info.setAudit_status(rs.getInt("audit_status"));
			info.setAudit_info(rs.getString("audit_info"));
			info.setEdit_remark(rs.getString("edit_remark"));
			//info.setThe_last_time(rs.getString("the_last_time"));
			return info;
		}
	}
	/**
	 * 数据模板：所有数据
	 * @author 01059101
	 *
	 */
	class WeakCurrentProjectsActualExpenditureSimpleMapper implements RowMapper<WeakCurrentProjectsActualExpenditureBean> {
		public WeakCurrentProjectsActualExpenditureBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WeakCurrentProjectsActualExpenditureBean info = new WeakCurrentProjectsActualExpenditureBean();
			info.setGroup_id(rs.getString("group_id"));
			info.setActual_id(rs.getString("actual_id"));
			info.setProject_code(rs.getString("project_code"));
			//info.setProject_area_department(rs.getString("project_area_department"));
			//info.setCost_department(rs.getString("cost_department"));
			info.setCost_matters(rs.getString("cost_matters"));
			info.setAmount_in_original_currency(rs.getBigDecimal("amount_in_original_currency"));
			info.setIdle_stock(rs.getBigDecimal("idle_stock"));
			info.setTax_rate(rs.getBigDecimal("tax_rate"));
			info.setAmount_of_tax(rs.getBigDecimal("amount_of_tax"));
			info.setPrincipal(rs.getBigDecimal("principal"));
			info.setBill_type(rs.getString("bill_type"));
			info.setInvoice_number(rs.getString("invoice_number"));
			info.setDate_of_occurrence(rs.getString("date_of_occurrence"));
			info.setPurpose_of_occurrence(rs.getString("purpose_of_occurrence"));
			info.setReimbursement_number(rs.getString("reimbursement_number"));
			info.setName_of_applicant(rs.getString("name_of_applicant"));
			info.setApplicant_department(rs.getString("applicant_department"));
			info.setDate_of_application(rs.getString("date_of_application"));
			info.setAudit_status(rs.getInt("audit_status"));
			info.setAudit_info(rs.getString("audit_info"));
			info.setEdit_remark(rs.getString("edit_remark"));
			//info.setThe_last_time(rs.getString("the_last_time"));
			return info;
		}
	}
	/**
	 * 数据模板：审批状态
	 * @author 01059101
	 *
	 */
	class WeakCurrentProjectsActualAuditStatusMapper implements RowMapper<WeakCurrentProjectsActualExpenditureBean> {
		public WeakCurrentProjectsActualExpenditureBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WeakCurrentProjectsActualExpenditureBean info = new WeakCurrentProjectsActualExpenditureBean();
			info.setAudit_status(rs.getInt("audit_status"));
			return info;
		}
	}
	/**
	 * 数据模板：组ID
	 * @author 01059101
	 *
	 */
	class WeakCurrentProjectsActualActualIdMapper implements RowMapper<WeakCurrentProjectsActualExpenditureBean> {
		public WeakCurrentProjectsActualExpenditureBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WeakCurrentProjectsActualExpenditureBean info = new WeakCurrentProjectsActualExpenditureBean();
			info.setGroup_id(rs.getString("group_id"));
			return info;
		}
	}


	/**
	 * 批量添加
	 * project_code,user_id,user_name,proportion,money,type
	 * @param list
	 */
	public String batchInsertActualExpenditure(final List<WeakCurrentProjectsActualExpenditureBean> list) {
		try {
		String v_sql = "insert into wcp_actual_expenditure(project_code " +
						//",cost_department" +
						",cost_matters" +
						",amount_in_original_currency" +
						",idle_stock" +
						",tax_rate" +
						",amount_of_tax" +
						",principal" +
						",bill_type" +
						",invoice_number" +
						",date_of_occurrence" +
						",purpose_of_occurrence" +
						",reimbursement_number" +
						",name_of_applicant" +
						",applicant_department" +
						",date_of_application" +
						//",the_last_time" +
						",actual_id"+
						",group_id) values (?,?,?,?,?" +
						",?,?,?,?,?" +
						",?,?,?,?,?" +
						",?,?)";
		jdbcTemplate.batchUpdate(v_sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				ps.setString(1, list.get(i).getProject_code());
				//ps.setString(2, list.get(i).getCost_department());
				ps.setString(2, list.get(i).getCost_matters());
				ps.setBigDecimal(3, list.get(i).getAmount_in_original_currency());
				ps.setBigDecimal(4, list.get(i).getIdle_stock());
				ps.setBigDecimal(5, list.get(i).getTax_rate());
				ps.setBigDecimal(6, list.get(i).getAmount_of_tax());
				ps.setBigDecimal(7, list.get(i).getPrincipal());
				ps.setString(8, list.get(i).getBill_type());
				ps.setString(9, list.get(i).getInvoice_number());
				ps.setString(10, list.get(i).getDate_of_occurrence());
				ps.setString(11, list.get(i).getPurpose_of_occurrence());
				ps.setString(12, list.get(i).getReimbursement_number());
				ps.setString(13, list.get(i).getName_of_applicant());
				ps.setString(14, list.get(i).getApplicant_department());
				ps.setString(15, list.get(i).getDate_of_application());
				//ps.setString(17, list.get(i).getThe_last_time());
				ps.setString(16, list.get(i).getActual_id());
				ps.setString(17, list.get(i).getGroup_id());
			}
			public int getBatchSize() {
				return list.size();
			}
		});
			return "0";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}

	
	


	/**
	 * 下拉框搜索：数据
	 * @param AuditStatus
	 * @param ProjectCode
	 * @param submitter
	 * @param project_area_department
	 * @return List<WeakCurrentProjectsSimpleBean>
	 */
	public List<WeakCurrentProjectsActualExpenditureBean> queryAllInfoWithFirstBySearchCondition(ArrayList<String> AuditStatus,ArrayList<String> ProjectCode,String submitter,String project_area_department) {
		String sql = SQL;
		if(AuditStatus.size()==1){
			sql+=" wcp_actual_expenditure.audit_status REGEXP \""+AuditStatus.get(0).toString()+"\" And ";
		}else if (AuditStatus.size()>1) {
			sql+="(";
			for(int a= 0;a<AuditStatus.size();a++){
				sql+=" wcp_actual_expenditure.audit_status REGEXP \""+AuditStatus.get(a).toString()+"\" or ";
			}
			sql=sql.substring(0,sql.length()-3)+") And ";
		}

		if(ProjectCode.size()==1){
			sql+=" wcp_actual_expenditure.project_code = \""+ProjectCode.get(0).toString()+"\" And ";
		}else if (ProjectCode.size()>1) {
			sql+="(";
			for(int a= 0;a<ProjectCode.size();a++){
				sql+=" wcp_actual_expenditure.project_code = \""+ProjectCode.get(a).toString()+"\" or ";
			}
			sql=sql.substring(0,sql.length()-3)+") And ";
		}
		if(submitter!=null){
			sql+="( weak_current_projects_simple.submitter= \""+submitter +"\" OR  weak_current_projects_simple.project_leader= \""+submitter +"\")AND ";
		}
		if(project_area_department!=null){
			sql+=" weak_current_projects_simple.project_area_department= \""+project_area_department +"\" AND ";
		}
		
		sql+=" 1=1  ORDER BY wcp_actual_expenditure.project_code DESC";
		System.out.println(sql);
		return jdbcTemplate.query(sql, new Object[]{}, new WeakCurrentProjectsActualExpenditureAllMapper());
	}
	/**
	 * 通过组ID获取数据
	 * @return List<WeakCurrentProjectsSimpleBean>
	 */
	public List<WeakCurrentProjectsActualExpenditureBean> queryAllInfoWithFirstByGroupId(String group_id) {
		String sql = SQL;
		sql+=" wcp_actual_expenditure.group_id= ? ORDER BY wcp_actual_expenditure.project_code DESC";
		System.out.println(sql);
		System.out.println(group_id);
		return jdbcTemplate.query(sql, new Object[]{group_id}, new WeakCurrentProjectsActualExpenditureAllMapper());
	}
	
	/**
	 * 根据ID更新审核数据
	 * @param audit_status
	 * @param audit_info
	 * @param actual_id
	 * @return
	 */
	public boolean updataWeakCurrentProjectsActualExpenditureAuditStatusByProjectCode(String audit_status ,String audit_info ,String actual_id) {
		String sql = "UPDATE wcp_actual_expenditure set audit_status= ?, audit_info= ? where  actual_id = ? ";
		return jdbcTemplate.update(sql, new Object[] {audit_status,audit_info, actual_id }) == 1;
	}
	


	/**
	 * 通过ID搜索数据WeakCurrentProjectsActualExpenditureSimpleMapper()
	 * @return List<WeakCurrentProjectsSimpleBean>
	 */
	public List<WeakCurrentProjectsActualExpenditureBean> querySimpleInfoById(String actual_id) {
		String sql = "select * from wcp_actual_expenditure where actual_id = ?";
		System.out.println(sql);
		return jdbcTemplate.query(sql, new Object[]{(actual_id)}, new WeakCurrentProjectsActualExpenditureSimpleMapper());
	}


	/**
	 * 通过project_code搜索数据WeakCurrentProjectsActualExpenditureSimpleMapper()
	 * 审核状态不等于2041(审核通过)
	 * @return List<WeakCurrentProjectsSimpleBean>
	 */
	public List<WeakCurrentProjectsActualExpenditureBean> querySimpleInfoByProject_code(String project_code) {
		String sql = "select * from wcp_actual_expenditure where project_code = ? and audit_status != 2041 ";
		System.out.println(sql);
		return jdbcTemplate.query(sql, new Object[]{(project_code)}, new WeakCurrentProjectsActualExpenditureSimpleMapper());
	}

	/**
	 * 通过project_code搜索数据
	 * 审核状态等于2041(审核通过)
	 * @return List<WeakCurrentProjectsSimpleBean>
	 */
	public List<WeakCurrentProjectsActualExpenditureBean> queryWeakActualReimbursementByProjectCode(String project_code) {
		String sql =SQL+ " wcp_actual_expenditure.project_code = ? and wcp_actual_expenditure.audit_status = 2041 ";
		System.out.println(sql);
		return jdbcTemplate.query(sql, new Object[]{(project_code)}, new WeakCurrentProjectsActualExpenditureAllMapper());
	}

	

	/**
	 * 根据ID更新数据
	 * @param actual_id
	 * @param list
	 * @return
	 */
	public boolean updataWeakCurrentProjectsActualExpenditureInfoByID(String actual_id, List<WeakCurrentProjectsActualExpenditureBean> list) {
		String sql = "UPDATE wcp_actual_expenditure set " +
				"bill_type= ? ,"+
				//"cost_matters= ? ,"+
				"amount_in_original_currency= ? ,"+
				"idle_stock= ? ,"+
				"tax_rate= ? ,"+
				"invoice_number= ? ,"+
				"date_of_occurrence= ? ,"+
				"purpose_of_occurrence= ? ,"+
				"amount_of_tax= ? ,"+
				"principal = ?  ,"+
				"edit_remark = ? ,audit_status= ?, audit_info= ?  where  actual_id = ? ";
		return jdbcTemplate.update(sql, new Object[] {
				list.get(0).getBill_type(),
				//list.get(0).getCost_matters(),
				list.get(0).getAmount_in_original_currency(),
				list.get(0).getIdle_stock(),
				list.get(0).getTax_rate(),
				list.get(0).getInvoice_number(),
				list.get(0).getDate_of_occurrence(),
				list.get(0).getPurpose_of_occurrence(),
				list.get(0).getAmount_of_tax(),
				list.get(0).getPrincipal(),
				list.get(0).getEdit_remark(),"2010", "N",
				actual_id }) == 1;
	}


	/**
	 * 通过ID搜索数据WeakCurrentProjectsActualExpenditureSimpleMapper()
	 * @return List<WeakCurrentProjectsSimpleBean>
	 
	public int queryIsOrNotTheLastTimeByProjectCode(String project_code) {
		String sql = "select * from wcp_actual_expenditure where project_code = ? And the_last_time ='Y' And audit_status != '2012'";
		System.out.println(sql);
		return jdbcTemplate.query(sql, new Object[]{(project_code)}, new WeakCurrentProjectsActualExpenditureSimpleMapper()).size();
	}
*/
	/**
	 * 通过ID获取审批状态
	 * @param actual_id
	 * @return
	 */
	public int WCPAAuditStatus(String actual_id) {
		String sql = "select audit_status from wcp_actual_expenditure where actual_id=?";
		System.out.println(sql);
		int audit_status=0;
		try {
			List<WeakCurrentProjectsActualExpenditureBean> list=jdbcTemplate.query(sql, new Object[]{actual_id}, new WeakCurrentProjectsActualAuditStatusMapper());
			audit_status=list.get(0).getAudit_status();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return audit_status;
	}

	/**
	 * 通过ID获取组ID
	 * @param actual_id
	 * @return
	 */
	public List<WeakCurrentProjectsActualExpenditureBean> queryActualIdById(String actual_id) {
		String sql = "select group_id from wcp_actual_expenditure where actual_id=?";
		System.out.println(sql);
		return jdbcTemplate.query(sql, new Object[]{actual_id}, new WeakCurrentProjectsActualActualIdMapper());
	}
	/**
	 * 通过ID获取组内所有审批数据是否审核完毕
	 * @param actual_id
	 * @return
	 */
	public List<WeakCurrentProjectsActualExpenditureBean> queryAuditStatusByActualId(String actual_id) {
		String sql = "select group_id from wcp_actual_expenditure where group_id=(select group_id from wcp_actual_expenditure where actual_id=?)  And audit_status !=2041 ";
		System.out.println(sql);
		return jdbcTemplate.query(sql, new Object[]{actual_id}, new WeakCurrentProjectsActualActualIdMapper());
	}

	/**
	 * 通过group_id搜索数据
	 * @return List<WeakCurrentProjectsSimpleBean>
	 */
	public List<WeakCurrentProjectsActualExpenditureBean> queryWeakActualReimbursementByGroupId(String group_id) {
		String sql = "select * from wcp_actual_expenditure where group_id = ?";
		System.out.println(sql);
		return jdbcTemplate.query(sql, new Object[]{(group_id)}, new WeakCurrentProjectsActualExpenditureSimpleMapper());
	}

}
