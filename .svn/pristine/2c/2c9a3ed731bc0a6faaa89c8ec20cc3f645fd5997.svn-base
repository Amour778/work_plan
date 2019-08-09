package com.workplan.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.workplan.bean.WeakCurrentProjectsDetailBean;
import com.workplan.tools.CalculationUtil;

public class WeakCurrentProjectsDetailDao {
	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	String SQL="SELECT "+
				//"weak_current_projects_detail.project_code, "+
				"weak_current_projects_detail.project_progress, "+
				"weak_current_projects_detail.project_progress_remake, "+
				"weak_current_projects_detail.cost_share, "+
				"weak_current_projects_detail.actual_completion_date, "+
				"weak_current_projects_detail.project_closing_day, "+
				"weak_current_projects_detail.remaining_stock, "+
				"weak_current_projects_detail.real_gross_profit, "+
				"weak_current_projects_detail.real_gross_profit_per, "+
				"weak_current_projects_detail.surplus_gross_profit, "+
				"weak_current_projects_detail.surplus_gross_profit_per, "+
				"weak_current_projects_detail.quality_assurance_funds, "+
				"weak_current_projects_detail.net_profit, "+
				"weak_current_projects_detail.net_profit_per, "+
				"weak_current_projects_detail.received_amount, "+
				"weak_current_projects_detail.amount_to_be_collected, "+
				"weak_current_projects_detail.personal, "+
				"weak_current_projects_detail.capital_pool, "+
				"weak_current_projects_detail.company, "+
				"weak_current_projects_detail.returned_money_ishave, "+
				"weak_current_projects_detail.returned_money_date, "+
				"weak_current_projects_detail.returned_money_point, "+
				"weak_current_projects_detail.reminder_date, "+
				"weak_current_projects_detail.returned_money, "+
				"weak_current_projects_detail.returned_money_remark, "+
				"weak_current_projects_detail.personal_warranty, "+
				"weak_current_projects_detail.personal_actual_distribution, "+
				"weak_current_projects_detail.material_cost_including_tax, "+
				"weak_current_projects_detail.tax_credit_amount, "+
				"weak_current_projects_detail.outsourcing_including_tax, "+
				"weak_current_projects_detail.transport_fees, "+
				"weak_current_projects_detail.allocation_of_fixed_assets_of_instruments, "+
				"weak_current_projects_detail.entertain, "+
				"weak_current_projects_detail.subtotal_labor_material_costs, "+
				"weak_current_projects_detail.management_costs, "+
				"weak_current_projects_detail.tax_reimbursement, "+
				"weak_current_projects_detail.subtotal_management_fees_and_taxes, "+
				"weak_current_projects_detail.principal, "+
				"weak_current_projects_detail.tax_money, "+
				"weak_current_projects_detail.full_amount_of_tax, "+
				"weak_current_projects_detail.audit_status, "+
				"weak_current_projects_detail.audit_info, "+
				"weak_current_projects_simple.id, "+
				"weak_current_projects_simple.project_code, "+
				"weak_current_projects_simple.project_area_department, "+
				"weak_current_projects_simple.district_name, "+
				"weak_current_projects_simple.item_classification, "+
				"weak_current_projects_simple.ustomer_name, "+
				"weak_current_projects_simple.planned_start_time, "+
				"weak_current_projects_simple.planned_end_time, "+
				"weak_current_projects_simple.project_name, "+
				"weak_current_projects_simple.project_cycle, "+
				"weak_current_projects_simple.project_quotation, "+
				"weak_current_projects_simple.project_leader, "+
				"weak_current_projects_simple.material, "+
				"weak_current_projects_simple.labor, "+
				"weak_current_projects_simple.allocation_of_fixed_assets, "+
				"weak_current_projects_simple.other_items, "+
				"weak_current_projects_simple.subtotal_cost, "+
				"weak_current_projects_simple.simple_tax, "+
				"weak_current_projects_simple.submitter, "+
				"weak_current_projects_simple.submission_time, "+
				"weak_current_projects_simple.supplier_invoice, "+
				"weak_current_projects_simple.supplier_invoice_tax, "+
				"weak_current_projects_simple.ordinary_invoice_amount, "+
				"weak_current_projects_simple.special_vat_invoice_amount, "+
				"weak_current_projects_simple.predict_net_profit_per, "+
				"weak_current_projects_simple.predict_net_profit, "+
				"weak_current_projects_simple.predict_gross_profit_per, "+
				"weak_current_projects_simple.predict_gross_profit, "+
				//"weak_current_projects_simple.audit_status, "+
				//"weak_current_projects_simple.audit_info, "+
				"weak_current_projects_simple.isclose "+
			"FROM "+
				"weak_current_projects_detail, "+
				"weak_current_projects_simple "+
			"WHERE "+
				"weak_current_projects_simple.project_code = weak_current_projects_detail.project_code And ";

	/**
	 * 数据模板：所有数据:Simple+Detail
	 * @author 01059101
	 *
	 */
	class WeakCurrentProjectsSimpleDetailAllMapper implements RowMapper<WeakCurrentProjectsDetailBean> {
		public WeakCurrentProjectsDetailBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WeakCurrentProjectsDetailBean info = new WeakCurrentProjectsDetailBean();
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
			info.setProject_progress(rs.getString("project_progress"));
			info.setProject_progress_remake(rs.getString("project_progress_remake"));
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
			info.setIsclose(rs.getInt("isclose"));
			info.setCost_share(rs.getBigDecimal("cost_share"));
			info.setActual_completion_date(rs.getString("actual_completion_date"));
			info.setProject_closing_day(rs.getString("project_closing_day"));
			info.setRemaining_stock(rs.getBigDecimal("remaining_stock"));
			info.setReal_gross_profit(rs.getBigDecimal("real_gross_profit"));
			info.setReal_gross_profit_per(rs.getBigDecimal("real_gross_profit_per"));
			info.setSurplus_gross_profit(rs.getBigDecimal("surplus_gross_profit"));
			info.setSurplus_gross_profit_per(rs.getBigDecimal("surplus_gross_profit_per"));
			info.setQuality_assurance_funds(rs.getBigDecimal("quality_assurance_funds"));
			info.setNet_profit(rs.getBigDecimal("net_profit"));
			info.setNet_profit_per(rs.getBigDecimal("net_profit_per"));
			info.setReceived_amount(rs.getBigDecimal("received_amount"));
			info.setAmount_to_be_collected(rs.getBigDecimal("amount_to_be_collected"));
			info.setPersonal(rs.getBigDecimal("personal"));
			info.setCapital_pool(rs.getBigDecimal("capital_pool"));
			info.setCompany(rs.getBigDecimal("company"));
			info.setReturned_money_ishave(rs.getString("returned_money_ishave"));
			info.setReturned_money_date(rs.getString("returned_money_date"));
			info.setReturned_money(rs.getBigDecimal("returned_money"));
			info.setReturned_money_remark(rs.getString("returned_money_remark"));
			info.setReturned_money_point(rs.getBigDecimal("returned_money_point"));
			info.setReminder_date(rs.getString("reminder_date"));
			info.setPersonal_warranty(rs.getBigDecimal("personal_warranty"));
			info.setPersonal_actual_distribution(rs.getBigDecimal("personal_actual_distribution"));
			info.setMaterial_cost_including_tax(rs.getBigDecimal("material_cost_including_tax"));
			info.setTax_credit_amount(rs.getDouble("tax_credit_amount"));
			info.setOutsourcing_including_tax(rs.getBigDecimal("outsourcing_including_tax"));
			info.setTransport_fees(rs.getBigDecimal("transport_fees"));
			info.setAllocation_of_fixed_assets_of_instruments(rs.getBigDecimal("allocation_of_fixed_assets_of_instruments"));
			info.setEntertain(rs.getBigDecimal("entertain"));
			info.setSubtotal_labor_material_costs(rs.getBigDecimal("subtotal_labor_material_costs"));
			info.setManagement_costs(rs.getBigDecimal("management_costs"));
			info.setTax_reimbursement(rs.getBigDecimal("tax_reimbursement"));
			info.setSubtotal_management_fees_and_taxes(rs.getBigDecimal("subtotal_management_fees_and_taxes"));
			info.setPrincipal(rs.getBigDecimal("principal"));
			info.setTax_money(rs.getDouble("tax_money"));
			info.setFull_amount_of_tax(rs.getBigDecimal("full_amount_of_tax"));
			info.setAudit_status(rs.getInt("audit_status"));
			info.setAudit_info(rs.getString("audit_info"));
			info.setPredict_gross_profit(rs.getBigDecimal("predict_gross_profit"));
			info.setPredict_gross_profit_per(rs.getBigDecimal("predict_gross_profit_per"));
			info.setPredict_net_profit(rs.getBigDecimal("predict_net_profit"));
			info.setPredict_net_profit_per(rs.getBigDecimal("predict_net_profit_per"));
			info.setSpecial_vat_invoice_amount(rs.getBigDecimal("special_vat_invoice_amount"));
			info.setOrdinary_invoice_amount(rs.getBigDecimal("ordinary_invoice_amount"));
			info.setSupplier_invoice_tax(rs.getBigDecimal("supplier_invoice_tax"));
			return info;
		}
	}

	/**
	 * 数据模板：所有数据:Detail
	 * @author 01059101
	 *
	 */
	class WeakCurrentProjectsDetailAllMapper implements RowMapper<WeakCurrentProjectsDetailBean> {
		public WeakCurrentProjectsDetailBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WeakCurrentProjectsDetailBean info = new WeakCurrentProjectsDetailBean();
			info.setProject_code(rs.getString("project_code"));
			info.setCost_share(rs.getBigDecimal("cost_share"));
			info.setActual_completion_date(rs.getString("actual_completion_date"));
			info.setProject_closing_day(rs.getString("project_closing_day"));
			info.setRemaining_stock(rs.getBigDecimal("remaining_stock"));
			info.setReal_gross_profit(rs.getBigDecimal("real_gross_profit"));
			info.setReal_gross_profit_per(rs.getBigDecimal("real_gross_profit_per"));
			info.setSurplus_gross_profit(rs.getBigDecimal("surplus_gross_profit"));
			info.setSurplus_gross_profit_per(rs.getBigDecimal("surplus_gross_profit_per"));
			info.setQuality_assurance_funds(rs.getBigDecimal("quality_assurance_funds"));
			info.setNet_profit(rs.getBigDecimal("net_profit"));
			info.setNet_profit_per(rs.getBigDecimal("net_profit_per"));
			info.setReceived_amount(rs.getBigDecimal("received_amount"));
			info.setAmount_to_be_collected(rs.getBigDecimal("amount_to_be_collected"));
			info.setPersonal(rs.getBigDecimal("personal"));
			info.setCapital_pool(rs.getBigDecimal("capital_pool"));
			info.setCompany(rs.getBigDecimal("company"));
			info.setReturned_money_ishave(rs.getString("returned_money_ishave"));
			info.setReturned_money_date(rs.getString("returned_money_date"));
			info.setReturned_money_point(rs.getBigDecimal("returned_money_point"));
			info.setReturned_money(rs.getBigDecimal("returned_money"));
			info.setReturned_money_remark(rs.getString("returned_money_remark"));
			info.setReminder_date(rs.getString("reminder_date"));
			info.setPersonal_warranty(rs.getBigDecimal("personal_warranty"));
			info.setPersonal_actual_distribution(rs.getBigDecimal("personal_actual_distribution"));
			info.setMaterial_cost_including_tax(rs.getBigDecimal("material_cost_including_tax"));
			info.setTax_credit_amount(rs.getDouble("tax_credit_amount"));
			info.setOutsourcing_including_tax(rs.getBigDecimal("outsourcing_including_tax"));
			info.setTransport_fees(rs.getBigDecimal("transport_fees"));
			info.setAllocation_of_fixed_assets_of_instruments(rs.getBigDecimal("allocation_of_fixed_assets_of_instruments"));
			info.setEntertain(rs.getBigDecimal("entertain"));
			info.setSubtotal_labor_material_costs(rs.getBigDecimal("subtotal_labor_material_costs"));
			info.setManagement_costs(rs.getBigDecimal("management_costs"));
			info.setTax_reimbursement(rs.getBigDecimal("tax_reimbursement"));
			info.setSubtotal_management_fees_and_taxes(rs.getBigDecimal("subtotal_management_fees_and_taxes"));
			info.setPrincipal(rs.getBigDecimal("principal"));
			info.setTax_money(rs.getDouble("tax_money"));
			info.setFull_amount_of_tax(rs.getBigDecimal("full_amount_of_tax"));
			info.setAudit_status(rs.getInt("audit_status"));
			info.setAudit_info(rs.getString("audit_info"));
			return info;
		}
	}



	/**
	 * 数据模板：首页质保金回款超时或即将超时数据
	 * @author 01059101
	 *
	 */
	class WeakCurrentProjectsDetailReturnedMoneyFinshMapper implements RowMapper<WeakCurrentProjectsDetailBean> {
		public WeakCurrentProjectsDetailBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WeakCurrentProjectsDetailBean info = new WeakCurrentProjectsDetailBean();
			info.setProject_code(rs.getString("project_code"));
			info.setProject_name(rs.getString("project_name"));
			info.setProject_area_department(rs.getString("project_area_department"));
			info.setProject_leader(rs.getString("project_leader"));
			info.setReturned_money_date(rs.getString("returned_money_date"));
			info.setReturned_money_point(rs.getBigDecimal("returned_money_point"));
			return info;
		}
	}

	/**
	 * 数据模板：审批节点
	 * @author 01059101
	 *
	 */
	class WeakCurrentProjectsDetailAuditStatusMapper implements RowMapper<WeakCurrentProjectsDetailBean> {
		public WeakCurrentProjectsDetailBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WeakCurrentProjectsDetailBean info = new WeakCurrentProjectsDetailBean();
			info.setAudit_status(rs.getInt("audit_status"));
			return info;
		}
	}
	/**
	 * 数据模板：是否有质保金
	 * @author 01059101
	 *
	 */
	class WeakCurrentProjectsDetailReturnedMoneyIshaveMapper implements RowMapper<WeakCurrentProjectsDetailBean> {
		public WeakCurrentProjectsDetailBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WeakCurrentProjectsDetailBean info = new WeakCurrentProjectsDetailBean();
			info.setReturned_money_ishave(rs.getString("returned_money_ishave"));
			return info;
		}
	}
	/**
	 * 数据模板：系统记录的质保金=个人奖金personal*质保金点数returned_money_point
	 * @author 01059101
	 *
	 */
	class WeakCurrentProjectsDetailReturnedMoneyMapper implements RowMapper<WeakCurrentProjectsDetailBean> {
		public WeakCurrentProjectsDetailBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WeakCurrentProjectsDetailBean info = new WeakCurrentProjectsDetailBean();
			info.setReturned_money(rs.getBigDecimal("returned_money"));
			return info;
		}
	}
	/**
	 * 数据模板：项目质保金预计回款日期和实际回款日期
	 * @author 01059101
	 *
	 */
	class WeakCurrentProjectsDetailReturnedMoneyDateMapper implements RowMapper<WeakCurrentProjectsDetailBean> {
		public WeakCurrentProjectsDetailBean mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			WeakCurrentProjectsDetailBean info = new WeakCurrentProjectsDetailBean();
			info.setReturned_money_date(rs.getString("returned_money_date"));
			info.setReminder_date(rs.getString("reminder_date"));
			return info;
		}
	}
	
	/**
	 * 立项审核通过：1.更新simple表的审核状态，2.添加数据到到detail表，3.计算detail表数据
	 * @param audit_status
	 * @param audit_info
	 * @param project_code
	 * @param cost_share
	 * @param project_quotation
	 * @param subtotal_cost
	 * @param simple_tax
	 * @return
	 */
	public int[] insertWeakCurrentProjectsDetail(String audit_status,String audit_info,String project_code,BigDecimal cost_share, BigDecimal project_quotation,BigDecimal subtotal_cost,BigDecimal simple_tax) {
		String[] sql = new String[3];
		sql[0] = "UPDATE weak_current_projects_simple set audit_status= "+audit_status+", audit_info= '"+audit_status+"' where  project_code = '"+project_code+"'";
		sql[1] = "insert into weak_current_projects_detail ( project_code, cost_share) values('"+project_code+"',"+cost_share+")";
		sql[2] = CalculationUtil.SQL_Edit_ProjectQuotation_SubtotalCost_SimpleTtax( project_code,  project_quotation, subtotal_cost, simple_tax);
		return jdbcTemplate.batchUpdate(sql);
	}

	
	/**
	 * 删除立项
	 * @param project_code
	 * @return
	 */
	public boolean delWCProjectsDetailByProjectCode(String project_code) {
		String sql = "delete from weak_current_projects_detail where project_code=? ";
		return jdbcTemplate.update(sql, new Object[] {project_code}) == 1;
	}
	
	
	
	/**
	 * 下拉框搜索：数据
	 * @return List<WeakCurrentProjectsSimpleBean>
	 */
	public List<WeakCurrentProjectsDetailBean> queryAllInfoWithFirstBySearchCondition(ArrayList<String> AuditStatus,ArrayList<String> ProjectCode,
			int page,int limit,String submitter,String project_area_department) {
		String sql =  SQL;
		if(AuditStatus.size()==1){
			sql+=" weak_current_projects_detail.audit_status REGEXP \""+AuditStatus.get(0).toString()+"\" And ";
		}else if (AuditStatus.size()>1) {
			sql+="(";
			for(int a= 0;a<AuditStatus.size();a++){
				sql+=" weak_current_projects_detail.audit_status REGEXP \""+AuditStatus.get(a).toString()+"\" or ";
			}
			sql=sql.substring(0,sql.length()-3)+") And ";
		}

		if(ProjectCode.size()==1){
			sql+=" weak_current_projects_detail.project_code = \""+ProjectCode.get(0).toString()+"\" And ";
		}else if (ProjectCode.size()>1) {
			sql+="(";
			for(int a= 0;a<ProjectCode.size();a++){
				sql+=" weak_current_projects_detail.project_code = \""+ProjectCode.get(a).toString()+"\" or ";
			}
			sql=sql.substring(0,sql.length()-3)+") And ";
		}
		if(submitter!=null){
			sql+="( weak_current_projects_simple.submitter= \""+submitter +"\" OR weak_current_projects_simple.project_leader= \""+submitter +"\") AND ";
		}

		if(project_area_department!=null){
			sql+="( weak_current_projects_simple.project_area_department= \""+project_area_department +"\") AND ";
		}
		sql+=" 1=1  ORDER BY weak_current_projects_detail.project_code DESC limit ? , ? ";
		System.out.println(sql);
		return jdbcTemplate.query(sql, new Object[]{(page - 1)*limit,limit}, new WeakCurrentProjectsSimpleDetailAllMapper());
	}

	/**
	 * 下拉框搜索：数据总行数
	 * @return List<WeakCurrentProjectsSimpleBean>
	 */
	public int queryAllInfoCountsBySearchCondition(ArrayList<String> AuditStatus,ArrayList<String> ProjectCode,String submitter,String project_area_department) {
		String sql =  SQL;
			if(AuditStatus.size()==1){
				sql+=" weak_current_projects_detail.audit_status REGEXP \""+AuditStatus.get(0).toString()+"\" And ";
			}else if (AuditStatus.size()>1) {
				sql+="(";
				for(int a= 0;a<AuditStatus.size();a++){
					sql+=" weak_current_projects_detail.audit_status REGEXP \""+AuditStatus.get(a).toString()+"\" or ";
				}
				sql=sql.substring(0,sql.length()-3)+") And ";
			}

			if(ProjectCode.size()==1){
				sql+=" weak_current_projects_detail.project_code = \""+ProjectCode.get(0).toString()+"\" And ";
			}else if (ProjectCode.size()>1) {
				sql+="(";
				for(int a= 0;a<ProjectCode.size();a++){
					sql+=" weak_current_projects_detail.project_code = \""+ProjectCode.get(a).toString()+"\" or ";
				}
				sql=sql.substring(0,sql.length()-3)+") And ";
			}

			if(submitter!=null){
				sql+="( weak_current_projects_simple.submitter= \""+submitter +"\" OR weak_current_projects_simple.project_leader= \""+submitter +"\") AND ";
			}
			if(project_area_department!=null){
				sql+="( weak_current_projects_simple.project_area_department= \""+project_area_department +"\") AND ";
			}
			sql+=" 1=1 ";
		System.out.println(sql);
		List<WeakCurrentProjectsDetailBean>  _list = new ArrayList<WeakCurrentProjectsDetailBean>();
		_list=jdbcTemplate.query(sql,  new WeakCurrentProjectsSimpleDetailAllMapper());
		int counts=0;
		counts=_list.size();
		return counts;
	}
	/**
	 * 根据project_code更新审核状态
	 * @param audit_status
	 * @param audit_info 默认N
	 * @param project_code
	 * @return
	 */
	public boolean updataWeakCurrentProjectsDetailAuditStatusAndAuditInsoByProjectCode(String audit_status ,String audit_info ,String project_code) {
		String sql = "UPDATE weak_current_projects_detail set audit_status= ?, audit_info= ? where  project_code = ? ";
		return jdbcTemplate.update(sql, new Object[] {audit_status,audit_info, project_code }) == 1;
	}

	/**
	 * 根据project_code更新进度数据
	 * @param bean
	 * @param project_code
	 * @return
	 */
	public boolean updataWeakCurrentProjectsDetailProgressByProjectCode(
			String project_progress, 
			String project_code,
			String returned_money_ishave, 
			String returned_money_date,
			String returned_money_point,
			String remaining_stock,
			String received_amount,
			String amount_to_be_collected,
			String project_progress_remake ) {
		String sql = "UPDATE weak_current_projects_detail set project_progress= ? ,returned_money_ishave = ? , returned_money_date = ? , returned_money_point = ? ,remaining_stock = ? ,received_amount = ? ,amount_to_be_collected = ?,project_progress_remake=? where  project_code = ? ";
		System.out.println(sql);
		return jdbcTemplate.update(sql, new Object[] {project_progress,returned_money_ishave,returned_money_date,returned_money_point,remaining_stock,received_amount,amount_to_be_collected,project_progress_remake, project_code }) == 1;
	}
	/**
	 * 根据project_code更新实际成本金额
	 * @param project_code 项目ID
	 * @param item 列名
	 * @param money 金额
	 * @return
	 */
	public boolean updataWeakCurrentProjectsDetailProgressByProjectCode(String project_code,String item,BigDecimal money) {
		String sql=CalculationUtil.SQL_WCPAE(item);
		return jdbcTemplate.update(sql, new Object[] {money,project_code}) == 1;
	}


	/**
	 * 根据project_code更新实际毛利，实际毛利%，净利润，净利润%
	 * @param real_gross_profit
	 * @param real_gross_profit_per
	 * @param net_profit
	 * @param net_profit_per
	 * @param project_code
	 * @return
	 */
	public boolean updataWCPDRealGrossAndNetProfitByProjectCode(
			double real_gross_profit, 
			double real_gross_profit_per,
			double net_profit, 
			double net_profit_per,
			String project_code) {
		String sql = "UPDATE weak_current_projects_detail set real_gross_profit= ? ,real_gross_profit_per = ? , net_profit = ? , net_profit_per = ?  where  project_code = ? ";
		return jdbcTemplate.update(sql, new Object[] {real_gross_profit,real_gross_profit_per,net_profit,net_profit_per,project_code}) == 1;
	}
	
	
	/**
	 * 申请结项通过,更新剩余数据 >0
	 * 
	 * @param project_code 项目ID
	 * @return
	 */
	public boolean colsingWeakCurrentProjectsDetailProgressByProjectCodeThanZero(String project_code) {
		
		String sql=CalculationUtil.SQL_WCPFinalGreaterThanZero();
		return jdbcTemplate.update(sql, new Object[] {project_code}) == 1;
	}

	/**
	 * 申请结项通过,更新剩余数据<=0
	 * 
	 * @param project_code 项目ID
	 * @return
	 */
	public boolean colsingWeakCurrentProjectsDetailProgressByProjectCodeLessThanOrEqualToZero(String project_code) {
		String sql=CalculationUtil.SQL_WCPFinalLessThanOrEqualToZero();
		return jdbcTemplate.update(sql, new Object[] {project_code}) == 1;
	}

	
	/**
	 * 搜索：数据(包含simple和detail)
	 * @return List<WeakCurrentProjectsSimpleBean>
	 */
	public List<WeakCurrentProjectsDetailBean> queryInfoByProjectCode(String project_code) {
		String sql =  SQL +" weak_current_projects_detail.project_code = ? ";
		System.out.println(sql);
		return jdbcTemplate.query(sql, new Object[]{project_code}, new WeakCurrentProjectsSimpleDetailAllMapper());
	}


	/**
	 * 通过日期和责任人，提醒用户是否有质保金进入回款阶段
	 * @param StartDate 当天日期往前N天
	 * @param EndDate 当天日期
	 * @param personal  所属责任人/提交人
	 * @return
	 */
	public List<WeakCurrentProjectsDetailBean> getWCPDReturnedIsComingByPersonal(String StartDate,String EndDate,String personal) {
		String SQL="SELECT "+
		"weak_current_projects_simple.project_leader, "+
		"weak_current_projects_simple.project_name, "+
		"weak_current_projects_detail.returned_money_date, "+
		"weak_current_projects_detail.returned_money_point, "+
		"weak_current_projects_simple.project_code, "+
		"weak_current_projects_simple.project_area_department "+
	"FROM "+
		"weak_current_projects_detail, "+
		"weak_current_projects_simple "+
	"WHERE "+
		"weak_current_projects_simple.project_code = weak_current_projects_detail.project_code And " +
		"weak_current_projects_detail.audit_status=9999 And " +
		"weak_current_projects_detail.returned_money_ishave = 'Y' And "+
		"weak_current_projects_detail.returned_money_date between ?  And ? And " +
		"(weak_current_projects_simple.project_leader= ? OR weak_current_projects_simple.submitter= ?) And "  +
		"weak_current_projects_detail.reminder_date is not null ";
		System.out.println(SQL);
		return jdbcTemplate.query(SQL, new Object[]{StartDate,EndDate,personal,personal}, new WeakCurrentProjectsDetailReturnedMoneyFinshMapper());
	}



	/**
	 * 通过日期和责任人，提醒用户是否有质保金进入回款阶段
	 * @param StartDate 当天日期
	 * @param EndDate 当天日期往后N天
	 * @param personal  所属责任人/提交人
	 * @return
	 */
	public List<WeakCurrentProjectsDetailBean> getWCPDReturnedIsComingByPersonal(String Date,String personal) {
		String SQL="SELECT "+
		"weak_current_projects_simple.project_leader, "+
		"weak_current_projects_simple.project_name, "+
		"weak_current_projects_detail.returned_money_date, "+
		"weak_current_projects_detail.returned_money_point, "+
		"weak_current_projects_simple.project_code, "+
		"weak_current_projects_simple.project_area_department "+
	"FROM "+
		"weak_current_projects_detail, "+
		"weak_current_projects_simple "+
	"WHERE "+
		"weak_current_projects_simple.project_code = weak_current_projects_detail.project_code And " +
		"weak_current_projects_detail.audit_status=9999 And " +
		"weak_current_projects_detail.returned_money_ishave = 'Y' And "+
		"weak_current_projects_detail.returned_money_date < ? And " +
		"(weak_current_projects_simple.project_leader= ? OR weak_current_projects_simple.submitter= ?) And "  +
		"weak_current_projects_detail.reminder_date is not null ";
		System.out.println(SQL);
		return jdbcTemplate.query(SQL, new Object[]{Date,personal,personal}, new WeakCurrentProjectsDetailReturnedMoneyFinshMapper());
	}

	/**
	 * 通过日期和地区，提醒用户是否有质保金进入回款阶段
	 * @param StartDate 当天日期
	 * @param EndDate 当天日期往后N天
	 * @param project_area_department 所属大区
	 * @return
	 */
	public List<WeakCurrentProjectsDetailBean> getWCPDReturnedIsComingByDepartment(String StartDate,String EndDate,String project_area_department) {
		String SQL="SELECT "+
		"weak_current_projects_simple.project_leader, "+
		"weak_current_projects_simple.project_name, "+
		"weak_current_projects_detail.returned_money_date, "+
		"weak_current_projects_detail.returned_money_point, "+
		"weak_current_projects_simple.project_code, "+
		"weak_current_projects_simple.project_area_department "+
	"FROM "+
		"weak_current_projects_detail, "+
		"weak_current_projects_simple "+
	"WHERE "+
		"weak_current_projects_simple.project_code = weak_current_projects_detail.project_code And " +
		"weak_current_projects_detail.audit_status=9999 And " +
		"weak_current_projects_detail.returned_money_ishave = 'Y' And "+
		"weak_current_projects_detail.returned_money_date between ?  And ? And " +
		"weak_current_projects_simple.project_area_department= ? And " +
		"weak_current_projects_detail.reminder_date is not null ";
		System.out.println(SQL);
		return jdbcTemplate.query(SQL, new Object[]{StartDate,EndDate,project_area_department}, new WeakCurrentProjectsDetailReturnedMoneyFinshMapper());
	}
	
	/**
	 * 通过日期和地区，提醒用户是否有质保金进入回款阶段
	 * @param StartDate 当天日期
	 * @param EndDate 当天日期往后N天
	 * @param project_area_department 所属大区
	 * @return
	 */
	public List<WeakCurrentProjectsDetailBean> getWCPDReturnedIsComingByDepartment(String Date,String project_area_department) {
		String SQL="SELECT "+
		"weak_current_projects_simple.project_leader, "+
		"weak_current_projects_simple.project_name, "+
		"weak_current_projects_detail.returned_money_date, "+
		"weak_current_projects_detail.returned_money_point, "+
		"weak_current_projects_simple.project_code, "+
		"weak_current_projects_simple.project_area_department "+
	"FROM "+
		"weak_current_projects_detail, "+
		"weak_current_projects_simple "+
	"WHERE "+
		"weak_current_projects_simple.project_code = weak_current_projects_detail.project_code And " +
		"weak_current_projects_detail.audit_status=9999 And " +
		"weak_current_projects_detail.returned_money_ishave = 'Y' And "+
		"weak_current_projects_detail.returned_money_date < ? And " +
		"weak_current_projects_simple.project_area_department= ? And " +
		"weak_current_projects_detail.reminder_date is not null ";
		System.out.println(SQL);
		return jdbcTemplate.query(SQL, new Object[]{Date,project_area_department}, new WeakCurrentProjectsDetailReturnedMoneyFinshMapper());
	}
	/**
	 * 通过日期和地区，提醒用户是否有质保金进入回款阶段
	 * @param StartDate 当天日期
	 * @param EndDate 当天日期往后N天
	 * @return
	 */
	public List<WeakCurrentProjectsDetailBean> getWCPDReturnedIsComingNoDepartment(String StartDate,String EndDate) {
		String SQL="SELECT "+
		"weak_current_projects_simple.project_leader, "+
		"weak_current_projects_simple.project_name, "+
		"weak_current_projects_detail.returned_money_date, "+
		"weak_current_projects_detail.returned_money_point, "+
		"weak_current_projects_simple.project_code, "+
		"weak_current_projects_simple.project_area_department "+
	"FROM "+
		"weak_current_projects_detail, "+
		"weak_current_projects_simple "+
	"WHERE "+
		"weak_current_projects_simple.project_code = weak_current_projects_detail.project_code And " +
		"weak_current_projects_detail.audit_status=9999 And " +
		"weak_current_projects_detail.returned_money_ishave = 'Y' And "+
		"weak_current_projects_detail.returned_money_date between ?  And ? And " +
		"(weak_current_projects_detail.reminder_date is  null  OR weak_current_projects_detail.reminder_date='')";
		System.out.println(SQL);
		return jdbcTemplate.query(SQL, new Object[]{StartDate,EndDate}, new WeakCurrentProjectsDetailReturnedMoneyFinshMapper());
	}
	
	/**
	 * 通过日期和地区，提醒用户是否有质保金进入回款阶段
	 * @param Date 当天日期
	 * @return
	 */
	public List<WeakCurrentProjectsDetailBean> getWCPDReturnedIsComingNoDepartment(String Date) {
		String SQL="SELECT "+
		"weak_current_projects_simple.project_leader, "+
		"weak_current_projects_simple.project_name, "+
		"weak_current_projects_detail.returned_money_date, "+
		"weak_current_projects_detail.returned_money_point, "+
		"weak_current_projects_simple.project_code, "+
		"weak_current_projects_simple.project_area_department "+
	"FROM "+
		"weak_current_projects_detail, "+
		"weak_current_projects_simple "+
	"WHERE "+
		"weak_current_projects_simple.project_code = weak_current_projects_detail.project_code And " +
		"weak_current_projects_detail.audit_status=9999 And " +
		"weak_current_projects_detail.returned_money_ishave = 'Y' And "+
		"weak_current_projects_detail.returned_money_date < ? And " +
		"(weak_current_projects_detail.reminder_date is  null  OR weak_current_projects_detail.reminder_date='')";
		System.out.println(SQL);
		return jdbcTemplate.query(SQL, new Object[]{Date}, new WeakCurrentProjectsDetailReturnedMoneyFinshMapper());
	}
	
	/**
	 * 修改了'税价全额','费用小计','税率'相应变动的数据
	 * @param project_quotation 价税全额
	 * @param subtotal_cost费用小计
	 * @param simple_tax税率
	 * @param project_code项目编码
	 * @return
	 */
	public int SQL_Edit_ProjectQuotation_SubtotalCost_SimpleTtax(BigDecimal project_quotation,BigDecimal subtotal_cost,BigDecimal simple_tax,String project_code){
		String sql = CalculationUtil.SQL_Edit_ProjectQuotation_SubtotalCost_SimpleTtax( project_code,  project_quotation, subtotal_cost, simple_tax);
		System.out.println(SQL);
		return jdbcTemplate.update(sql);
	}

	/**
	 * 获取项目审批状态
	 * @param project_code
	 * @return
	 */
	public int WCPDAuditStatus(String project_code) {
		String SQL="SELECT audit_status FROM weak_current_projects_detail WHERE project_code = ? ";
		List<WeakCurrentProjectsDetailBean> list=jdbcTemplate.query(SQL, new Object[]{project_code}, new WeakCurrentProjectsDetailAuditStatusMapper());
		int audit_status=0;
		try {
			audit_status=list.get(0).getAudit_status();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return audit_status;
	}
	/**
	 * 获取项目质保金预计回款日期和实际回款日期
	 * @param project_code
	 * @return 质保金预计回款日期和实际回款日期
	 */
	public String WCPDReturnedMoneyDate(String project_code) {
		String SQL="SELECT returned_money_date,reminder_date FROM weak_current_projects_detail WHERE project_code = ? ";
		List<WeakCurrentProjectsDetailBean> list=jdbcTemplate.query(SQL, new Object[]{project_code}, new WeakCurrentProjectsDetailReturnedMoneyDateMapper());
		String date="";
		try {
			date=list.get(0).getReturned_money_date()+","+list.get(0).getReminder_date();
		} catch (Exception e) {
			e.printStackTrace();
			date=",";
		}
		return date;
	}
	/**
	 * 获取项目是否有质保金
	 * @param project_code
	 * @return 'N'/'Y'，默认是Y
	 */
	public String WCPDReturnedMoneyIshave(String project_code) {
		String SQL="SELECT returned_money_ishave FROM weak_current_projects_detail WHERE project_code = ? ";
		List<WeakCurrentProjectsDetailBean> list=jdbcTemplate.query(SQL, new Object[]{project_code}, new WeakCurrentProjectsDetailReturnedMoneyIshaveMapper());
		String returned_money_ishave="Y";
		try {
			returned_money_ishave=list.get(0).getReturned_money_ishave();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returned_money_ishave;
	}
	/**
	 * 获取项目应该回款的质保金金额
	 * @param project_code
	 * @return personal*returned_money_point AS returned_money
	 */
	public BigDecimal queryWCPDSystemReturnedMoney(String project_code) {
		String SQL="SELECT ROUND(personal * returned_money_point,2) AS returned_money FROM weak_current_projects_detail WHERE project_code = ? And returned_money_ishave ='Y'";
		List<WeakCurrentProjectsDetailBean> list=jdbcTemplate.query(SQL, new Object[]{project_code}, new WeakCurrentProjectsDetailReturnedMoneyMapper());
		BigDecimal returned_money_ishave = null;
		try {
			returned_money_ishave=list.get(0).getReturned_money();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returned_money_ishave;
	}
	
	/**
	 * 根据project_code更新质保金实际汇款日期
	 * @param reminder_date
	 * @param project_code
	 * @return
	 */
	public boolean updataWCPDReminderDateByProjectCode(String reminder_date,String audit_status, String project_code) {
		String sql = "UPDATE weak_current_projects_detail set reminder_date= ? ,audit_status=? where  project_code = ? ";
		return jdbcTemplate.update(sql, new Object[] {reminder_date,audit_status,project_code}) == 1;
	}
	/**
	 * 通过项目编码更新项目质保金实际回款数额，审批状态更改为9980
	 * @param returned_money
	 * @param returned_money_remark
	 * @param project_code
	 * @return
	 */
	public boolean updataWCPDReturnedMondyAndRemarkByProjectCode(String returned_money,String returned_money_remark,String project_code) {
		String sql = "UPDATE weak_current_projects_detail set returned_money= ? ,returned_money_remark=?,audit_status=?  where  project_code = ? ";
		return jdbcTemplate.update(sql, new Object[] {returned_money,returned_money_remark,"9980",project_code}) == 1;
	}
}
