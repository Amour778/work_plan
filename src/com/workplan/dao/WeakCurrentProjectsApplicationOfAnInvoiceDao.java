package com.workplan.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.workplan.bean.WeakCurrentProjectsApplicationOfAnInvoiceBean;
/**
 * 开票申请
 * @author 01059101
 *
 */
public class WeakCurrentProjectsApplicationOfAnInvoiceDao {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 数据模板：所有数据
	 * @author 01059101
	 *
	 */
	class WCP_AOAI_AllInfoMapper implements RowMapper<WeakCurrentProjectsApplicationOfAnInvoiceBean> {
		public WeakCurrentProjectsApplicationOfAnInvoiceBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WeakCurrentProjectsApplicationOfAnInvoiceBean info = new WeakCurrentProjectsApplicationOfAnInvoiceBean();
			info.setAid(rs.getString("aid"));
			info.setUser_id(rs.getString("user_id"));
			info.setUser_name(rs.getString("user_name"));
			info.setWcp_area(rs.getString("wcp_area"));
			info.setProject_code(rs.getString("project_code"));
			info.setProject_name(rs.getString("project_name"));
			info.setInvoice_type(rs.getString("invoice_type"));
			info.setCompany_name(rs.getString("company_name"));
			info.setTaxpayer_identification_number(rs.getString("taxpayer_identification_number"));
			info.setBank_and_bank_account_number(rs.getString("bank_and_bank_account_number"));
			info.setAddress_and_telephone_number(rs.getString("address_and_telephone_number"));
			info.setName_of_make_out_an_invoice(rs.getString("name_of_make_out_an_invoice"));
			info.setSpecifications(rs.getString("specifications"));
			info.setUnit(rs.getString("unit"));
			info.setNumber(rs.getBigDecimal("number"));
			info.setInvoice_amount_excluding_tax(rs.getBigDecimal("invoice_amount_excluding_tax"));
			info.setRate(rs.getBigDecimal("rate"));
			info.setInvoice_amount_included_tax(rs.getBigDecimal("invoice_amount_included_tax"));
			info.setNote(rs.getString("note"));
			info.setAudit_status(rs.getInt("audit_status"));
			info.setAudit_info(rs.getString("audit_info"));
			return info;
		}
	}

	/**
	 * 数据模板：所有数据
	 * @author 01059101
	 *
	 */
	class WCP_AOAI_AuditStatusMapper implements RowMapper<WeakCurrentProjectsApplicationOfAnInvoiceBean> {
		public WeakCurrentProjectsApplicationOfAnInvoiceBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WeakCurrentProjectsApplicationOfAnInvoiceBean info = new WeakCurrentProjectsApplicationOfAnInvoiceBean();
			info.setAudit_status(rs.getInt("audit_status"));
			return info;
		}
	}

	/**
	 * 添加开票申请
	 * @param bean
	 * @return
	 */
	public void addWCP_AOAI_info(WeakCurrentProjectsApplicationOfAnInvoiceBean bean) {
		String sql = "INSERT INTO wcp_application_of_an_invoice (aid, user_id, user_name, wcp_area, project_code, project_name, invoice_type, company_name, taxpayer_identification_number, bank_and_bank_account_number, address_and_telephone_number, name_of_make_out_an_invoice, specifications, unit, number, invoice_amount_excluding_tax, rate, invoice_amount_included_tax, note, audit_status, audit_info) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, new Object[] { 
				bean.getAid(),
				bean.getUser_id(),
				bean.getUser_name(),
				bean.getWcp_area(),
				bean.getProject_code(),
				bean.getProject_name(),
				bean.getInvoice_type(),
				bean.getCompany_name(),
				bean.getTaxpayer_identification_number(),
				bean.getBank_and_bank_account_number(),
				bean.getAddress_and_telephone_number(),
				bean.getName_of_make_out_an_invoice(),
				bean.getSpecifications(),
				bean.getUnit(),
				bean.getNumber(),
				bean.getInvoice_amount_excluding_tax(),
				bean.getRate(),
				bean.getInvoice_amount_included_tax(),
				bean.getNote(),
				bean.getAudit_status(),
				bean.getAudit_info()
		}) ;
	}


	/**
	 * 添加开票申请
	 * @param bean
	 * @return
	 */
	public void updateWCP_AOAI_info(WeakCurrentProjectsApplicationOfAnInvoiceBean bean) {
		String sql = "UPDATE wcp_application_of_an_invoice SET user_id=?, user_name=?, wcp_area=?, project_code=?, project_name=?, invoice_type=?, company_name=?, " +
				"taxpayer_identification_number=?, bank_and_bank_account_number=?, address_and_telephone_number=?, name_of_make_out_an_invoice=?, specifications=?, " +
				"unit=?, number=?, invoice_amount_excluding_tax=?, rate=?, invoice_amount_included_tax=?, note=?, audit_status=?, audit_info=? WHERE  aid=? And project_code=?";
		jdbcTemplate.update(sql, new Object[] { 
				bean.getUser_id(),
				bean.getUser_name(),
				bean.getWcp_area(),
				bean.getProject_code(),
				bean.getProject_name(),
				bean.getInvoice_type(),
				bean.getCompany_name(),
				bean.getTaxpayer_identification_number(),
				bean.getBank_and_bank_account_number(),
				bean.getAddress_and_telephone_number(),
				bean.getName_of_make_out_an_invoice(),
				bean.getSpecifications(),
				bean.getUnit(),
				bean.getNumber(),
				bean.getInvoice_amount_excluding_tax(),
				bean.getRate(),
				bean.getInvoice_amount_included_tax(),
				bean.getNote(),
				bean.getAudit_status(),
				bean.getAudit_info(),
				bean.getAid(),
				bean.getProject_code()
		}) ;
	}
	/**
	 * 根据project_code获取Bean数据
	 * @param project_code
	 * @return
	 */
	public WeakCurrentProjectsApplicationOfAnInvoiceBean queryOneWCP_AOAI_AllInfo_ByProjectCode(String project_code) {
		String sql = "select * from wcp_application_of_an_invoice where project_code = ?";
		List<WeakCurrentProjectsApplicationOfAnInvoiceBean> list = jdbcTemplate.query(sql,new Object[] { project_code},new WCP_AOAI_AllInfoMapper());
		if (list != null && list.size() > 0) {
			WeakCurrentProjectsApplicationOfAnInvoiceBean bean = list.get(0);
			return bean;
		} else {
			return null;
		}
	}
	/**
	 * 根据aid获取审核状态
	 * @param aid
	 * @return
	 */
	public int queryWCP_AOAI_AuditStatus_ByAid(String aid) {
		String sql = "select audit_status from wcp_application_of_an_invoice where aid = ?";
		List<WeakCurrentProjectsApplicationOfAnInvoiceBean> list = jdbcTemplate.query(sql,new Object[] { aid},new WCP_AOAI_AuditStatusMapper());
		int audit_status=0;
		if (list != null && list.size() > 0) {
			audit_status = list.get(0).getAudit_status();
		}
		return audit_status;
	}
	
	/**
	 * 通过AID更新项目的审批状态
	 */
	public boolean updateWCP_AOAI_AuditStatusAndAuditInfoByAid(String aid,int audit_status,String audit_info) {
		String sqlString="UPDATE wcp_application_of_an_invoice SET  audit_status = ? ,audit_info = ? WHERE aid = ?";
		return jdbcTemplate.update(sqlString, new Object[]{audit_status,audit_info,aid})==1;
	}

}
