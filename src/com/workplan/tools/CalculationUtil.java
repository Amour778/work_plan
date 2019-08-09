package com.workplan.tools;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

/**
 * 弱电项目专用类
 * 
 * @author 01059101 
 * 此类用户涉及到的计算内容 分三个阶段计算：
 *  ①立项：通过 
 *  ②实际开支：财务通过 
 *  ③申请结项：通过
 * 
 */
public class CalculationUtil {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/**
	 * ①立项：通过<br/>
	 * subtotal_cost,project_quotation,project_quotation,simple_tax,simple_tax,simple_tax,project_code<br/>
	 * 费用小计=材料+人工(外请)+固定资产分摊+其他项<br/>
	 * subtotal_cost = material + allocation_of_fixed_assets + other_items<br/>
	 * 成本占比=费用小计/项目报价<br/>
	 * cost_share = subtotal_cost / project_quotation<br/>
	 * 税价全额 = 项目报价<br/>
	 * full_amount_of_tax = project_quotation<br/>
	 * 管理成本2% = 税价全额*0.02<br/>
	 * management_costs = full_amount_of_tax * 0.02<br/>
	 * 税额 6~11%=税价全额/(1+simple_tax)*simple_tax<br/>
	 * tax_money = full_amount_of_tax / ( 1 + simple_tax ) * simple_tax<br/>
	 * 本金=税价全额/(1+simple_tax)<br/>
	 * principal= full_amount_of_tax / ( 1 + simple_tax )<br/>
	 * 待收金额=税价全额*0.05<br/>
	 * amount_to_be_collected = full_amount_of_tax*0.05
	 */
	public static String Sql_HaveWCP() {
		String SQL_HaveWCP=
			"UPDATE weak_current_projects_detail "+
			"SET " +
			"cost_share = ? / ? , "+
			"full_amount_of_tax = ? , "+
			"management_costs = full_amount_of_tax * 0.02 , "+
			"tax_money = full_amount_of_tax / (1 + ? ) * ? , "+
			"principal = full_amount_of_tax / (1 + ? ), "+
			"amount_to_be_collected = full_amount_of_tax * 0.05 "+
			"WHERE "+
			"project_code = ?";
		System.out.println("SQL_HaveWCP="+SQL_HaveWCP);
		return SQL_HaveWCP;
	}
	

		/**
		 * ②实际开支：审核通过<br/>
		 * item,item,money,project_code<br/>
		 */
	public static String SQL_WCPAE(String item) {
		String SQL_WCPAE=
			"UPDATE weak_current_projects_detail set "+ item +" = "+item+" + ?, " +
			"subtotal_labor_material_costs = material_cost_including_tax + " +
			"outsourcing_including_tax + " +
			"transport_fees + " +
			"allocation_of_fixed_assets_of_instruments + " +
			"entertain " +
			"WHERE project_code = ? ";
		System.out.println("SQL_WCPAE="+SQL_WCPAE);
		return SQL_WCPAE;
	}

	/**
	 * ③申请结项：通过<br/>
	 * (税额 6~11% - 抵税金额(增值税专用发票))  >0<br/>
	 * tax_money - tax_credit_amount > 0<br/>
	 * item,item,money,project_code
	 */
	public static String SQL_WCPFinalGreaterThanZero() {
		String SQL_WCPFinalGreaterThanZero=""+
			"UPDATE weak_current_projects_detail "+
			"SET tax_reimbursement = ((tax_money - tax_credit_amount) * 1.12 + full_amount_of_tax * 0.0005) + subtotal_labor_material_costs * 0.0005, "+
			"subtotal_management_fees_and_taxes = management_costs + tax_reimbursement, "+
			"real_gross_profit = full_amount_of_tax - subtotal_labor_material_costs - subtotal_management_fees_and_taxes, "+
			"real_gross_profit_per = real_gross_profit / full_amount_of_tax, "+
			"surplus_gross_profit = real_gross_profit - remaining_stock, "+
			"surplus_gross_profit_per = surplus_gross_profit / full_amount_of_tax, "+
			"net_profit = real_gross_profit * 0.75, "+
			"net_profit_per = net_profit / full_amount_of_tax, "+
			"quality_assurance_funds = net_profit * returned_money_point, "+
			"personal = net_profit * 0.25, "+
			"capital_pool = net_profit * 0.25, "+
			"company = net_profit * 0.50, "+
			"personal_warranty = personal * returned_money_point, "+
			"personal_actual_distribution = personal - personal_warranty "+
			"WHERE "+
			"project_code = ?";
		System.out.println("SQL_WCPFinalGreaterThanZero="+SQL_WCPFinalGreaterThanZero);
		return SQL_WCPFinalGreaterThanZero;
	}

	/**
	 * ③申请结项：通过<br/>
	 * (税额 6~11% - 抵税金额(增值税专用发票))  <=0<br/>
	 * item,item,money,project_code<br/>
	 */
	public static String SQL_WCPFinalLessThanOrEqualToZero() {
	String SQL_WCPFinalGreaterThanZero=""+
		"UPDATE weak_current_projects_detail "+
		"SET tax_reimbursement = full_amount_of_tax * 0.0005 + subtotal_labor_material_costs * 0.0005, "+
		"subtotal_management_fees_and_taxes = management_costs + tax_reimbursement, "+
		"real_gross_profit = full_amount_of_tax - tax_reimbursement - subtotal_management_fees_and_taxes, "+
		"real_gross_profit_per = real_gross_profit / full_amount_of_tax, "+
		"surplus_gross_profit = real_gross_profit - remaining_stock, "+
		"surplus_gross_profit_per = surplus_gross_profit / full_amount_of_tax, "+
		"net_profit = real_gross_profit * 0.75, "+
		"net_profit_per = net_profit / full_amount_of_tax, "+
		"quality_assurance_funds = net_profit * returned_money_point, "+
		"personal = net_profit * 0.25, "+
		"capital_pool = net_profit * 0.25, "+
		"company = net_profit * 0.50, "+
		"personal_warranty = personal * returned_money_point, "+
		"personal_actual_distribution = personal - personal_warranty "+
		"WHERE "+
		"project_code = ?";
	System.out.println("SQL_WCPFinalGreaterThanZero="+SQL_WCPFinalGreaterThanZero);
	return SQL_WCPFinalGreaterThanZero;
	}
	
	

	private static BigDecimal BigDecimalDivision(Object a, Object b) {
		//MYSQL 除法 ：SELECT TRUNCATE(1/0.3,2)
		//MYSQL 乘法 ：SELECT TRUNCATE(1.23*0.35,2)
	    BigDecimal result = new BigDecimal(String.valueOf(a)).divide(new BigDecimal(String.valueOf(b)), 2, BigDecimal.ROUND_HALF_UP);
	    return result;
	}
	
	// 判断是否是数字
	public static boolean isNumber(String number) {
		int index = number.indexOf(".");
		if (index < 0) {
			return StringUtils.isNumeric(number);
		} else {
			String num1 = number.substring(0, index);
			String num2 = number.substring(index + 1);

			return StringUtils.isNumeric(num1) && StringUtils.isNumeric(num2);
		}
	}
	
	/**
	 * #修改了'税价全额','费用小计','税率'相应变动的数据为：
	 */
	public static String SQL_Edit_ProjectQuotation_SubtotalCost_SimpleTtax(String project_code, BigDecimal project_quotation,BigDecimal subtotal_cost,BigDecimal simple_tax){
		
		String SQL="UPDATE weak_current_projects_detail " +
			"SET full_amount_of_tax = "+project_quotation+", cost_share = "+subtotal_cost+" / full_amount_of_tax, " +
			" management_costs = full_amount_of_tax * 0.02, " +
			" tax_money = full_amount_of_tax / (1 + "+simple_tax+") *  "+simple_tax+", principal = full_amount_of_tax / (1 +  "+simple_tax+"), " +
			" amount_to_be_collected = full_amount_of_tax * 0.05, " +
			" real_gross_profit = full_amount_of_tax - subtotal_labor_material_costs - subtotal_management_fees_and_taxes, " +
			" real_gross_profit_per = real_gross_profit / full_amount_of_tax, " +
			" net_profit = real_gross_profit * 0.75, " +
			" net_profit_per = net_profit / full_amount_of_tax, " +
			" tax_reimbursement = CASE " +
			"WHEN ( " +
			"	tax_money - tax_credit_amount > 0 " +
			") THEN " +
			"	( " +
			"		( " +
			"			tax_money - tax_credit_amount " +
			"		) * 0.12 + full_amount_of_tax * 0.0005 " +
			"	) " +
			"ELSE " +
			"	( " +
			"		subtotal_labor_material_costs * 0.0005 " +
			"	) " +
			"END " +
			"WHERE " +
			"	project_code = '"+project_code+"'";
		/*
		if(isNumber(project_quotation.toString())){
			SQL+=" project_quotation = "+project_quotation+", ";
		}else {
			SQL+=" project_quotation = project_quotation, ";
		}
		if(isNumber(subtotal_cost.toString())){
			SQL+=" subtotal_cost = "+subtotal_cost+", ";
		}else {
			SQL+=" subtotal_cost = subtotal_cost, ";
		}if(isNumber(simple_tax.toString())){
			SQL+=" simple_tax = "+simple_tax+", ";
		}else {
			SQL+=" simple_tax = "+simple_tax+", ";
		}
		
		SQL+="cost_share = subtotal_cost / project_quotation, " +
		//" #税价全额 = 项目报价" +
		"full_amount_of_tax = project_quotation, " +
		//" #管理成本2% = 税价全额*0.02" +
		"management_costs = project_quotation * 0.02, " +
		//" #税额 6~11%=税价全额/(1+税率)*税率" +
		"tax_money = project_quotation / (1 + simple_tax) * simple_tax, " +
		//" #本金=税价全额/(1+税率)" +
		"principal = project_quotation / (1 + simple_tax), " +
		//" #待收金额=税价全额*0.05" +
		"amount_to_be_collected = project_quotation * 0.05, " +
		//" #实际毛利=税价全额-人工材料费用小计-管理费及税务小计" +
		"real_gross_profit = project_quotation - subtotal_labor_material_costs - subtotal_management_fees_and_taxes, " +
		//" #实际毛利%=实际毛利/税价全额" +
		"real_gross_profit_per = real_gross_profit / project_quotation, " +
		//" #净利润=实际毛利*0.75" +
		"net_profit = real_gross_profit * 0.75, " +
		//" #净利%=净利润/税价全额" +
		"net_profit_per = net_profit / project_quotation, " +
		//" #补税" +
		"tax_reimbursement = CASE " +
		"WHEN " +
		"(tax_money - tax_credit_amount > 0) " +
		"THEN " +
		"	((tax_money - tax_credit_amount) * 0.12 + project_quotation * 0.0005) " +
		"ELSE " +
		"	(subtotal_labor_material_costs * 0.0005) " +
		" END " +
		" WHERE " +
		" project_code = ? ";
		*/
		return SQL;
	}
}
