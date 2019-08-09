package com.workplan.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.workplan.bean.WeakCurrentProjectsRequestForPaymentBean;
import com.workplan.bean.WeakCurrentProjectsSimpleBean;
import com.workplan.dao.WeakCurrentProjectsSimpleDao.WeakCurrentProjectsSimpleAuditStatus;

public class WeakCurrentProjectsRequestForPaymentDao {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	
	/**
	 * 添加或更新付款申请单
	 * @param bean
	 * @return
	 */
	public void addOrUpdateWCP_RFP_info(WeakCurrentProjectsRequestForPaymentBean bean) {
		String sql = "INSERT wcp_request_for_payment " +
				"( rid,project_code, project_area_department, supplierOrCompanyName, supplierOrCompanyNumber, agent, bank, theBankAccount, theNetworkCode, typeOfReceivingUnit, natureOfPayment, becauseOfPayment, theContractNumber, batch, amountPaid, totalAmountPayable, writeOffTheAdvancePayment, amountOfThisPayment, capital, numberOfDocumentsAndAttachments, paymentRequest, FA_Entry, FA_Audit, termsOfPayment, GL_AP_Entry, GL_AP_Audit, approvalOfRelevantDepartments, financeDepartmentApproval, responsiblePersonApproval, signatureOfPayee, cashiersSignature, inputDate,audit_sta,audit_info) " +
				" VALUES " +
				"( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)  ON DUPLICATE KEY UPDATE " +
				"project_area_department=?,supplierOrCompanyName=?,supplierOrCompanyNumber=?,agent=?,bank=?,theBankAccount=?,theNetworkCode=?,typeOfReceivingUnit=?,natureOfPayment=?,becauseOfPayment=?,theContractNumber=?,batch=?,amountPaid=?,totalAmountPayable=?,writeOffTheAdvancePayment=?,amountOfThisPayment=?,capital=?,numberOfDocumentsAndAttachments=?,paymentRequest=?,FA_Entry=?,FA_Audit=?,termsOfPayment=?,GL_AP_Entry=?,GL_AP_Audit=?,approvalOfRelevantDepartments=?,financeDepartmentApproval=?,responsiblePersonApproval=?,signatureOfPayee=?,cashiersSignature=?,inputDate=?,audit_sta=?,audit_info=?";
		jdbcTemplate.update(sql, new Object[] { 
				bean.getRid(),
				bean.getProject_code(),
				bean.getProject_area_department(),
				bean.getSupplierOrCompanyName(),
				bean.getSupplierOrCompanyNumber(),
				bean.getAgent(),
				bean.getBank(),
				bean.getTheBankAccount(),
				bean.getTheNetworkCode(),
				bean.getTypeOfReceivingUnit(),
				bean.getNatureOfPayment(),
				bean.getBecauseOfPayment(),
				bean.getTheContractNumber(),
				bean.getBatch(),
				bean.getAmountPaid(),
				bean.getTotalAmountPayable(),
				bean.getWriteOffTheAdvancePayment(),
				bean.getAmountOfThisPayment(),
				bean.getCapital(),
				bean.getNumberOfDocumentsAndAttachments(),
				bean.getPaymentRequest(),
				bean.getFA_Entry(),
				bean.getFA_Audit(),
				bean.getTermsOfPayment(),
				bean.getGL_AP_Entry(),
				bean.getGL_AP_Audit(),
				bean.getApprovalOfRelevantDepartments(),
				bean.getFinanceDepartmentApproval(),
				bean.getResponsiblePersonApproval(),
				bean.getSignatureOfPayee(),
				bean.getCashiersSignature(),
				bean.getInputDate(),
				bean.getAudit_sta(),
				bean.getAudit_info(),

				bean.getProject_area_department(),
				bean.getSupplierOrCompanyName(),
				bean.getSupplierOrCompanyNumber(),
				bean.getAgent(),
				bean.getBank(),
				bean.getTheBankAccount(),
				bean.getTheNetworkCode(),
				bean.getTypeOfReceivingUnit(),
				bean.getNatureOfPayment(),
				bean.getBecauseOfPayment(),
				bean.getTheContractNumber(),
				bean.getBatch(),
				bean.getAmountPaid(),
				bean.getTotalAmountPayable(),
				bean.getWriteOffTheAdvancePayment(),
				bean.getAmountOfThisPayment(),
				bean.getCapital(),
				bean.getNumberOfDocumentsAndAttachments(),
				bean.getPaymentRequest(),
				bean.getFA_Entry(),
				bean.getFA_Audit(),
				bean.getTermsOfPayment(),
				bean.getGL_AP_Entry(),
				bean.getGL_AP_Audit(),
				bean.getApprovalOfRelevantDepartments(),
				bean.getFinanceDepartmentApproval(),
				bean.getResponsiblePersonApproval(),
				bean.getSignatureOfPayee(),
				bean.getCashiersSignature(),
				bean.getInputDate(),
				bean.getAudit_sta(),
				bean.getAudit_info()
		}) ;
	}
	
	
	/**
	 * 通过RID更新项目的审批状态
	 */
	public boolean updateWCP_RFP_AuditStaAndAuditInfoByRid(String rid,int audit_sta,String audit_info,String project_code,BigDecimal amountOfThisPayment) {
		//BOSS通过，需要将此次付款数据添加到总表
		if(audit_sta==2141){
			String[] sql = new String[2];
			sql[0] = "UPDATE wcp_request_for_payment SET  audit_sta = "+audit_info+" ,audit_info = '"+audit_info+"' WHERE rid = '"+rid+"'";
			sql[1] = "UPDATE weak_current_projects_detail SET received_amount = received_amount + "+amountOfThisPayment+" WHERE project_code = '"+project_code+"'";
			jdbcTemplate.batchUpdate(sql);
			return true;
		}else {
			String sqlString="UPDATE wcp_request_for_payment SET  audit_sta = ? ,audit_info = ? WHERE rid = ?";
			return jdbcTemplate.update(sqlString, new Object[]{audit_sta,audit_info,rid})==1;
		}
	}

	/**
	 * 根据RID删除数据
	 * @param rid
	 * @return
	 */
	public int deleteWCPRFP(String rid) {
		return jdbcTemplate.update("DELETE from wcp_request_for_payment where rid=?", rid);
	}
	/**
	 * 根据项目ID获取所有的付款申请单
	 * @param project_code
	 * @return
	 */
	public List<WeakCurrentProjectsRequestForPaymentBean> queryAllWCP_RFP_ByProjectCode(String project_code) {
		String sql = "SELECT " +
		"wcp_request_for_payment.rid, " +
		"wcp_request_for_payment.project_code, " +
		//"wcp_request_for_payment.project_area_department, " +
		"wcp_request_for_payment.supplierOrCompanyName, " +
		"wcp_request_for_payment.supplierOrCompanyNumber, " +
		"wcp_request_for_payment.inputDate, " +
		"wcp_request_for_payment.audit_sta, " +
		"wcp_request_for_payment.audit_info, " +
		"weak_current_projects_detail.received_amount AS amountOfThisPayment, " +//已收款项
		"weak_current_projects_simple.project_area_department AS project_area_department, " +//部门
		"weak_current_projects_simple.project_quotation AS totalAmountPayable " +//应收款项
		"FROM " +
		"weak_current_projects_detail " +
		"INNER JOIN wcp_request_for_payment ON wcp_request_for_payment.project_code = weak_current_projects_detail.project_code " +
		"INNER JOIN weak_current_projects_simple ON wcp_request_for_payment.project_code = weak_current_projects_simple.project_code " +
		"WHERE wcp_request_for_payment.project_code=?";
		System.out.println(sql);
		List<WeakCurrentProjectsRequestForPaymentBean> list = jdbcTemplate.query(sql,new Object[] { project_code },new ShowLyauiTableInfoWCPRFPMapper());
			return list;
	}
	/**
	 * 添加报销申请单时需要的数据
	 * @param project_code
	 * @return
	 */
	public List<WeakCurrentProjectsRequestForPaymentBean> queryWCP_info_ToAdd_RFP_ByProjectCode(String project_code) {
		String sql = "SELECT\n" +
		"weak_current_projects_simple.project_code, " +
		"weak_current_projects_simple.project_area_department, " +
		"weak_current_projects_simple.project_quotation AS totalAmountPayable, " +
		"weak_current_projects_detail.received_amount AS amountPaid " +
		"FROM " +
		"weak_current_projects_detail " +
		"INNER JOIN weak_current_projects_simple ON weak_current_projects_simple.project_code = weak_current_projects_detail.project_code "+
		"WHERE weak_current_projects_simple.project_code=?";
		List<WeakCurrentProjectsRequestForPaymentBean> list = jdbcTemplate.query(sql,new Object[] { project_code },new AddRFPInfoNeededMapper());
			return list;
	}
	
	
	/**
	 * 根据RID获取Bean数据
	 * @param id
	 * @return
	 */
	public WeakCurrentProjectsRequestForPaymentBean queryOneWCP_RFP_ByRid(String rid) {
		String sql = "select * from wcp_request_for_payment where rid=?";
		List<WeakCurrentProjectsRequestForPaymentBean> list = jdbcTemplate.query(sql,new Object[] { rid },new AllInfoWCPRFPMapper());
		if (list != null && list.size() > 0) {
			WeakCurrentProjectsRequestForPaymentBean WeakCurrentProjectsRequestForPaymentBean = list.get(0);
			return WeakCurrentProjectsRequestForPaymentBean;
		} else {
			return null;
		}
	}
	

	
	/**
	 * 获取审批状态
	 * @return List<WeakCurrentProjectsSimpleBean>
	 */
	public int WCPRFPAuditSta(String rid) {
		String sql =  "SELECT audit_sta FROM wcp_request_for_payment WHERE rid =  ?";
		System.out.println(sql);
		List<WeakCurrentProjectsRequestForPaymentBean> list=jdbcTemplate.query(sql, new Object[]{rid}, new WCPRFPAuditStaMapper());
		int audit_sta = 0;
		try {
			audit_sta = list.get(0).getAudit_sta();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return audit_sta;
	}
	
	//所有数据
	class AllInfoWCPRFPMapper implements RowMapper<WeakCurrentProjectsRequestForPaymentBean> {
		public WeakCurrentProjectsRequestForPaymentBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WeakCurrentProjectsRequestForPaymentBean info = new WeakCurrentProjectsRequestForPaymentBean();
			info.setRid(rs.getString("rid"));
			info.setProject_code(rs.getString("project_code"));
			info.setProject_area_department(rs.getString("project_area_department"));
			info.setSupplierOrCompanyName(rs.getString("supplierOrCompanyName"));
			info.setSupplierOrCompanyNumber(rs.getString("supplierOrCompanyNumber"));
			info.setAgent(rs.getString("agent"));
			info.setBank(rs.getString("bank"));
			info.setTheBankAccount(rs.getString("theBankAccount"));
			info.setTheNetworkCode(rs.getString("theNetworkCode"));
			info.setTypeOfReceivingUnit(rs.getString("typeOfReceivingUnit"));
			info.setNatureOfPayment(rs.getString("natureOfPayment"));
			info.setBecauseOfPayment(rs.getString("becauseOfPayment"));
			info.setTheContractNumber(rs.getString("theContractNumber"));
			info.setBatch(rs.getString("batch"));
			info.setAmountPaid(rs.getBigDecimal("amountPaid"));
			info.setTotalAmountPayable(rs.getBigDecimal("totalAmountPayable"));
			info.setWriteOffTheAdvancePayment(rs.getBigDecimal("writeOffTheAdvancePayment"));
			info.setAmountOfThisPayment(rs.getBigDecimal("amountOfThisPayment"));
			info.setCapital(rs.getString("capital"));
			info.setNumberOfDocumentsAndAttachments(rs.getInt("numberOfDocumentsAndAttachments"));
			info.setPaymentRequest(rs.getString("paymentRequest"));
			info.setFA_Entry(rs.getString("FA_Entry"));
			info.setFA_Audit(rs.getString("FA_Audit"));
			info.setTermsOfPayment(rs.getString("termsOfPayment"));
			info.setGL_AP_Entry(rs.getString("GL_AP_Entry"));
			info.setGL_AP_Audit(rs.getString("GL_AP_Audit"));
			info.setApprovalOfRelevantDepartments(rs.getString("approvalOfRelevantDepartments"));
			info.setFinanceDepartmentApproval(rs.getString("financeDepartmentApproval"));
			info.setResponsiblePersonApproval(rs.getString("responsiblePersonApproval"));
			info.setSignatureOfPayee(rs.getString("signatureOfPayee"));
			info.setCashiersSignature(rs.getString("cashiersSignature"));
			info.setInputDate(rs.getString("inputDate"));
			info.setAudit_sta(rs.getInt("audit_sta"));
			info.setAudit_info(rs.getString("audit_info"));
			return info;
		}
	}

	//添加报销申请单时需要的数据
	class AddRFPInfoNeededMapper implements RowMapper<WeakCurrentProjectsRequestForPaymentBean> {
		public WeakCurrentProjectsRequestForPaymentBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WeakCurrentProjectsRequestForPaymentBean info = new WeakCurrentProjectsRequestForPaymentBean();
			info.setProject_code(rs.getString("project_code"));
			info.setProject_area_department(rs.getString("project_area_department"));
			info.setAmountPaid(rs.getBigDecimal("amountPaid"));
			info.setTotalAmountPayable(rs.getBigDecimal("totalAmountPayable"));
			return info;
		}
	}
	//前端layuiTable需要的数据
	class ShowLyauiTableInfoWCPRFPMapper implements RowMapper<WeakCurrentProjectsRequestForPaymentBean> {
		public WeakCurrentProjectsRequestForPaymentBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WeakCurrentProjectsRequestForPaymentBean info = new WeakCurrentProjectsRequestForPaymentBean();
			info.setRid(rs.getString("rid"));
			info.setProject_code(rs.getString("project_code"));
			info.setProject_area_department(rs.getString("project_area_department"));
			info.setSupplierOrCompanyName(rs.getString("supplierOrCompanyName"));
			info.setSupplierOrCompanyNumber(rs.getString("supplierOrCompanyNumber"));
			info.setAmountOfThisPayment(rs.getBigDecimal("amountOfThisPayment"));
			info.setTotalAmountPayable(rs.getBigDecimal("totalAmountPayable"));
			info.setInputDate(rs.getString("inputDate"));
			info.setAudit_sta(rs.getInt("audit_sta"));
			info.setAudit_info(rs.getString("audit_info"));
			return info;
		}
	}
	//前端layuiTable需要的数据
	class WCPRFPAuditStaMapper implements RowMapper<WeakCurrentProjectsRequestForPaymentBean> {
		public WeakCurrentProjectsRequestForPaymentBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WeakCurrentProjectsRequestForPaymentBean info = new WeakCurrentProjectsRequestForPaymentBean();
			info.setAudit_sta(rs.getInt("audit_sta"));
			info.setAmountOfThisPayment(rs.getBigDecimal("amountOfThisPayment"));
			return info;
		}
	}

}
