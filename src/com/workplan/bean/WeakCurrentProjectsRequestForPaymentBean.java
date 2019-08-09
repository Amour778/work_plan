package com.workplan.bean;

import java.math.BigDecimal;

/**
 * 成本报销bean
 * 
 * @author 01059101
 * 
 */

public class WeakCurrentProjectsRequestForPaymentBean {

	// 付款申请单ID
	private String rid;

	// 项目代码
	private String project_code;

	// 申请部门/费用所属部门
	private String project_area_department;

	// 供应商或公司
	private String supplierOrCompanyName;

	// 供应商或公司代码
	private String supplierOrCompanyNumber;

	// 经办人
	private String agent;

	// 开户银行
	private String bank;

	// 银行账号
	private String theBankAccount;

	// 网络代码
	private String theNetworkCode;

	// 收款单位类别
	private String typeOfReceivingUnit;

	// 付款性质
	private String natureOfPayment;

	// 付款原因
	private String becauseOfPayment;

	// 合同号码
	private String theContractNumber;

	// 批次
	private String batch;

	// 已付款金额(不含本次)
	private BigDecimal amountPaid;

	// 合同或应付款总额
	private BigDecimal totalAmountPayable;

	// 冲销前期预付款金额
	private BigDecimal writeOffTheAdvancePayment;

	// 小写
	private BigDecimal amountOfThisPayment;

	// 大写
	private String capital;

	// 单据及附件张数
	private long numberOfDocumentsAndAttachments;

	// 付款要求
	private String paymentRequest;

	// FA录入
	private String FA_Entry;

	// FA审核
	private String FA_Audit;

	// 付款方式
	private String termsOfPayment;

	// GL/AP录入
	private String GL_AP_Entry;

	// GL/AP审核
	private String GL_AP_Audit;

	// 相关部门审批
	private String approvalOfRelevantDepartments;

	// 财务部门审批
	private String financeDepartmentApproval;

	// 负责人审批
	private String responsiblePersonApproval;

	// 收款人签名
	private String signatureOfPayee;

	// 出纳员签名
	private String cashiersSignature;

	// 年月日
	private String inputDate;

	// 审核状态
	private int audit_sta;

	// 审核备注信息
	private String audit_info;

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getProject_code() {
		return project_code;
	}

	public void setProject_code(String projectCode) {
		project_code = projectCode;
	}

	public String getProject_area_department() {
		return project_area_department;
	}

	public void setProject_area_department(String projectAreaDepartment) {
		project_area_department = projectAreaDepartment;
	}

	public String getSupplierOrCompanyName() {
		return supplierOrCompanyName;
	}

	public void setSupplierOrCompanyName(String supplierOrCompanyName) {
		this.supplierOrCompanyName = supplierOrCompanyName;
	}

	public String getSupplierOrCompanyNumber() {
		return supplierOrCompanyNumber;
	}

	public void setSupplierOrCompanyNumber(String supplierOrCompanyNumber) {
		this.supplierOrCompanyNumber = supplierOrCompanyNumber;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getTheBankAccount() {
		return theBankAccount;
	}

	public void setTheBankAccount(String theBankAccount) {
		this.theBankAccount = theBankAccount;
	}

	public String getTheNetworkCode() {
		return theNetworkCode;
	}

	public void setTheNetworkCode(String theNetworkCode) {
		this.theNetworkCode = theNetworkCode;
	}

	public String getTypeOfReceivingUnit() {
		return typeOfReceivingUnit;
	}

	public void setTypeOfReceivingUnit(String typeOfReceivingUnit) {
		this.typeOfReceivingUnit = typeOfReceivingUnit;
	}

	public String getNatureOfPayment() {
		return natureOfPayment;
	}

	public void setNatureOfPayment(String natureOfPayment) {
		this.natureOfPayment = natureOfPayment;
	}

	public String getBecauseOfPayment() {
		return becauseOfPayment;
	}

	public void setBecauseOfPayment(String becauseOfPayment) {
		this.becauseOfPayment = becauseOfPayment;
	}

	public String getTheContractNumber() {
		return theContractNumber;
	}

	public void setTheContractNumber(String theContractNumber) {
		this.theContractNumber = theContractNumber;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public BigDecimal getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}

	public BigDecimal getTotalAmountPayable() {
		return totalAmountPayable;
	}

	public void setTotalAmountPayable(BigDecimal totalAmountPayable) {
		this.totalAmountPayable = totalAmountPayable;
	}

	public BigDecimal getWriteOffTheAdvancePayment() {
		return writeOffTheAdvancePayment;
	}

	public void setWriteOffTheAdvancePayment(
			BigDecimal writeOffTheAdvancePayment) {
		this.writeOffTheAdvancePayment = writeOffTheAdvancePayment;
	}

	public BigDecimal getAmountOfThisPayment() {
		return amountOfThisPayment;
	}

	public void setAmountOfThisPayment(BigDecimal amountOfThisPayment) {
		this.amountOfThisPayment = amountOfThisPayment;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public long getNumberOfDocumentsAndAttachments() {
		return numberOfDocumentsAndAttachments;
	}

	public void setNumberOfDocumentsAndAttachments(
			long numberOfDocumentsAndAttachments) {
		this.numberOfDocumentsAndAttachments = numberOfDocumentsAndAttachments;
	}

	public String getPaymentRequest() {
		return paymentRequest;
	}

	public void setPaymentRequest(String paymentRequest) {
		this.paymentRequest = paymentRequest;
	}

	public String getTermsOfPayment() {
		return termsOfPayment;
	}

	public void setTermsOfPayment(String termsOfPayment) {
		this.termsOfPayment = termsOfPayment;
	}

	public String getFA_Entry() {
		return FA_Entry;
	}

	public void setFA_Entry(String fAEntry) {
		FA_Entry = fAEntry;
	}

	public String getFA_Audit() {
		return FA_Audit;
	}

	public void setFA_Audit(String fAAudit) {
		FA_Audit = fAAudit;
	}

	public String getGL_AP_Entry() {
		return GL_AP_Entry;
	}

	public void setGL_AP_Entry(String gLAPEntry) {
		GL_AP_Entry = gLAPEntry;
	}

	public String getGL_AP_Audit() {
		return GL_AP_Audit;
	}

	public void setGL_AP_Audit(String gLAPAudit) {
		GL_AP_Audit = gLAPAudit;
	}

	public String getApprovalOfRelevantDepartments() {
		return approvalOfRelevantDepartments;
	}

	public void setApprovalOfRelevantDepartments(
			String approvalOfRelevantDepartments) {
		this.approvalOfRelevantDepartments = approvalOfRelevantDepartments;
	}

	public String getFinanceDepartmentApproval() {
		return financeDepartmentApproval;
	}

	public void setFinanceDepartmentApproval(String financeDepartmentApproval) {
		this.financeDepartmentApproval = financeDepartmentApproval;
	}

	public String getResponsiblePersonApproval() {
		return responsiblePersonApproval;
	}

	public void setResponsiblePersonApproval(String responsiblePersonApproval) {
		this.responsiblePersonApproval = responsiblePersonApproval;
	}

	public String getSignatureOfPayee() {
		return signatureOfPayee;
	}

	public void setSignatureOfPayee(String signatureOfPayee) {
		this.signatureOfPayee = signatureOfPayee;
	}

	public String getCashiersSignature() {
		return cashiersSignature;
	}

	public void setCashiersSignature(String cashiersSignature) {
		this.cashiersSignature = cashiersSignature;
	}

	public String getInputDate() {
		return inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	public int getAudit_sta() {
		return audit_sta;
	}

	public void setAudit_sta(int auditSta) {
		audit_sta = auditSta;
	}

	public String getAudit_info() {
		return audit_info;
	}

	public void setAudit_info(String auditInfo) {
		audit_info = auditInfo;
	}
}