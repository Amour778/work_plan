package com.workplan.handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.workplan.bean.ParameterBean;
import com.workplan.bean.UserInfoBean;
import com.workplan.bean.WeakCurrentProjectsActualExpenditureBean;
import com.workplan.bean.WeakCurrentProjectsApprovaLogBean;
import com.workplan.bean.WeakCurrentProjectsDetailBean;
import com.workplan.bean.WeakCurrentProjectsSimpleBean;
import com.workplan.dao.WeakCurrentProjectsActualExpenditureDao;
import com.workplan.dao.WeakCurrentProjectsDetailDao;
import com.workplan.dao.WeakCurrentProjectsSimpleDao;
import com.workplan.tools.ArithUtil;
import com.workplan.tools.GetDateTimeNow;
import com.workplan.tools.GsonUtil;
import com.workplan.tools.ResultMessage;
@Controller
public class WeakCurrentProjectsActualExpenditureHandler {
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	WeakCurrentProjectsActualExpenditureDao weakActaulDao = (WeakCurrentProjectsActualExpenditureDao) context.getBean("WeakCurrentProjectsActualExpenditureDao");
	WeakCurrentProjectsSimpleDao weakSimpleDao = (WeakCurrentProjectsSimpleDao) context.getBean("WeakCurrentProjectsSimpleDao");
	WeakCurrentProjectsDetailDao weakDetailDao = (WeakCurrentProjectsDetailDao) context.getBean("WeakCurrentProjectsDetailDao");

	List<WeakCurrentProjectsActualExpenditureBean> weakActaulList = new ArrayList<WeakCurrentProjectsActualExpenditureBean>();
	List<WeakCurrentProjectsSimpleBean> weakSimpleList = new ArrayList<WeakCurrentProjectsSimpleBean>();
	List<WeakCurrentProjectsDetailBean> weakDetailList = new ArrayList<WeakCurrentProjectsDetailBean>();
	List<WeakCurrentProjectsApprovaLogBean> weakLogList = new ArrayList<WeakCurrentProjectsApprovaLogBean>();
	
	WeakCurrentProjectsActualExpenditureBean weakActaulBean = new WeakCurrentProjectsActualExpenditureBean();
	GetDateTimeNow getDateTimeNow = new GetDateTimeNow();
	GsonUtil gsonUtil=new GsonUtil();
	ArithUtil arithUtil = new ArithUtil();
	WeakCurrentProjectsApprovaLogHandler app_log=new WeakCurrentProjectsApprovaLogHandler();
	List<ParameterBean> parameterlist =new ArrayList<ParameterBean>();
	ParameterHandler parameterHandler =new ParameterHandler();
/*	*//**
	 * 多选下拉框
	 * @return 
	 *//*
	@RequestMapping(value = "/getWeakCurrentProjectsActualExpenditureFormSelectsInfo",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getWeakCurrentProjectsActualExpenditureFormSelectsInfo() {
		weakSimpleList=weakSimpleDao.queryAllProjectName();
		String reString="";
		//状态：默认不选中
		reString+="{\"name\": \"审核状态\", \"type\": \"optgroup\"},";
		parameterlist=parameterHandler.queryAllParameterByPidList("15");
		if(parameterlist.size()==0){
			reString="{\"code\":1,\"msg\":\"审核状态参数获取异常!15\",\"data\":[]}";
			return reString;
		}
		for (int i = 0; i < parameterlist.size(); i++) {
			reString+="{\"name\": \""+parameterlist.get(i).getText()+"\", \"value\": \""+parameterlist.get(i).getValue()+"\",\"selected\":\""+parameterlist.get(i).getSelect()+"\",\"disabled\":\""+parameterlist.get(i).getDisable()+"\"},";
		}
		reString+="{\"name\": \"待审核\", \"value\": \"AuDiTsTaTuS|2010,AuDiTsTaTuS|2020,AuDiTsTaTuS|2030,AuDiTsTaTuS|2040\",\"selected\":\"selected\"}," +
				"{\"name\": \"已通过\", \"value\": \"AuDiTsTaTuS|2041\",\"selected\":\"\"}," +
				"{\"name\": \"已驳回\", \"value\": \"AuDiTsTaTuS|2012,AuDiTsTaTuS|2022,AuDiTsTaTuS|2032,AuDiTsTaTuS|2042\",\"selected\":\"selected\"},";

		if(SecurityUtils.getSubject().hasRole("wck_per_user")){//用户是否为普通的立项用户
			reString+="{\"name\": \"提交人\", \"type\": \"optgroup\"},";
			reString+="{\"name\": \""+(String) SecurityUtils.getSubject().getPrincipal()+"\", \"value\": \"SuBmItTeR|"+(String) SecurityUtils.getSubject().getPrincipal()+"\",\"selected\":\"selected\",\"disabled\":\"disabled\"},";
		}
		//项目名称
		reString+="{\"name\": \"项目名称\", \"type\": \"optgroup\"},";
		for(int a= 0;a<weakSimpleList.size();a++){
			reString+="{\"name\": \""+weakSimpleList.get(a).getProject_name()+"\", \"value\":\"PrOjEcTcOde|"+weakSimpleList.get(a).getProject_code()+"\",\"selected\":\"\"},";
		}
		
		reString="{\"code\":0,\"msg\":\"success\",\"data\":["+reString.substring(0, reString.length()-1)+"]}";
		System.out.println(reString);
		return reString;
	}
	*//**
	 * 搜索：根据条件获取数据
	 * @param SearchCondition
	 * @param page
	 * @param limit
	 * @return
	 *//*
	@RequestMapping(value = "/queryWeakCurrentProjectsActualExpenditure",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryWeakCurrentProjectsActualExpenditure(String SearchCondition,int page, int limit) {
		System.out.println("SearchCondition="+SearchCondition);
		if(SearchCondition==null){
			System.out.println("SearchCondition==null");
			SearchCondition="";
		}
		String arr[]=SearchCondition.split(",");
		weakActaulList = new ArrayList<WeakCurrentProjectsActualExpenditureBean>();
		if(SearchCondition!=null&&SearchCondition!=""){
			System.out.println("有"+arr.length+"个条件");
		}else {
			System.out.println(arr.length+"没有条件");
			return ResultMessage.ListtoLayuiTable(0, gsonUtil.List2Json(weakActaulList));
		}
		for(int a=0;a<arr.length;a++){
			System.out.println("arr[a]="+arr[a].toString());
		}
		ArrayList<String> AuditStatus = new ArrayList<String>();
		ArrayList<String> ProjectCode = new ArrayList<String>();
		for(int a=0;a<arr.length;a++){
			if(arr[a].toString().indexOf("AuDiTsTaTuS")!=-1){
				AuditStatus.add(arr[a].split("\\|")[1]);//这里之所以用"\\|"，是因为"|"是特殊字符
			}else if (arr[a].toString().indexOf("PrOjEcTcOde")!=-1) {
				ProjectCode.add(arr[a].split("\\|")[1]);
				
			}
		}
		String submitter=null;
		if(SecurityUtils.getSubject().hasRole("wck_per_user")){//用户是否为普通的立项用户
			submitter=(String) SecurityUtils.getSubject().getPrincipal();
		}
		int Counts= weakActaulDao.queryAllInfoCountsBySearchCondition(AuditStatus, ProjectCode,submitter);
		weakActaulList = weakActaulDao.queryAllInfoWithFirstBySearchCondition(AuditStatus, ProjectCode, page, limit,submitter);
		GsonUtil gsonUtil=new GsonUtil();
		return ResultMessage.ListtoLayuiTable(Counts, gsonUtil.List2Json(weakActaulList));
	}*/

	/**
	 * 根据ID更新项目成本审核状态
	 * @return 
	 */
	@RequestMapping(value = "/updataWeakCurrentProjectsActualExpenditureAuditStatusByID",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updataWeakCurrentProjectsActualExpenditureAuditStatusByID(String audit_status,String audit_info,String id) {
		System.out.println("audit_status="+audit_status);
		System.out.println("audit_info="+audit_info);
		System.out.println("id="+id);
		
		weakActaulList= weakActaulDao.querySimpleInfoById(id);
		String project_code=weakActaulList.get(0).getProject_code();
		weakSimpleList = weakSimpleDao.queryProjectInfoByProjectCode(project_code);
		if(weakSimpleList.size()==0){
			return ResultMessage.MessageToJson(1, "项目数据不存在");
		}
		if(weakSimpleList.get(0).getAudit_status()==0){
			return ResultMessage.MessageToJson(1, "项目已撤项");
		}
		if(weakSimpleList.get(0).getAudit_status()!=1021){
			return ResultMessage.MessageToJson(1, "立项内容做了修改还未通过，不允许审批成本报销事项");
		}
		Map<String, String> returnStringMap = new HashMap<String, String>();
		int mysql_approval_status= weakActaulDao.WCPAAuditStatus(id);
		returnStringMap=WCPApprovalStatusUtil.approvalStatusCheck(mysql_approval_status,Integer.parseInt(audit_status));
		if(returnStringMap.get("BOOLEAN").equals("false")){
			return ResultMessage.MessageToJson(1, "审核失败!当前项目的审批节点为："+returnStringMap.get("INFO"));
		}
		if(weakActaulDao.updataWeakCurrentProjectsActualExpenditureAuditStatusByProjectCode(audit_status,audit_info, id)){
			WeakCurrentProjectsActualGroupHandler wcpag= new WeakCurrentProjectsActualGroupHandler();
			if(audit_status.equals("2041")){
				try {
					
					String item=weakActaulList.get(0).getCost_matters();
					BigDecimal money=weakActaulList.get(0).getAmount_in_original_currency();//价税合计
					System.out.println("project_code="+project_code);
					System.out.println("item="+item);
					System.out.println("money="+money);
					WeakCurrentProjectsDetailHandler wcpDetailHandler=new WeakCurrentProjectsDetailHandler();
					Map<String ,String> map = new HashMap<String, String>();
					map=wcpDetailHandler.updataWeakCurrentProjectsDetailProgressByProjectCode(project_code, item, money);
					//calculateGrossMarginAndProfit
					if(map.get("sta").equals("true")){
						/*weakDetailList=weakDetailDao.queryInfoByProjectCode(project_code);
						double project_quotation = Double.parseDouble(weakDetailList.get(0).getProject_quotation().toString());
						double simple_tax = Double.parseDouble(weakDetailList.get(0).getSimple_tax().toString());
						double subtotal_labor_material_costs = Double.parseDouble(weakDetailList.get(0).getSubtotal_labor_material_costs().toString());
						double tax_credit_amount = weakDetailList.get(0).getTax_credit_amount();*/
						double predict_gross_profit=0;
						double project_quotation = Double.parseDouble(weakDetailList.get(0).getProject_quotation().toString());
						weakDetailList=weakDetailDao.queryInfoByProjectCode(project_code);
						double real_gross_profit=calculateGrossMarginAndProfitWithApproved(project_code, item, Double.parseDouble(money.toString()),
								Double.parseDouble(weakDetailList.get(0).getProject_quotation().toString()),
								Double.parseDouble(weakDetailList.get(0).getSimple_tax().toString()),
								Double.parseDouble(weakDetailList.get(0).getSubtotal_labor_material_costs().toString()),
								weakDetailList.get(0).getTax_credit_amount());
						
						//实际毛利
						//double real_gross_profit = calculateGrossMarginAndProfit(project_code, item, Double.parseDouble(money.toString()), project_quotation, simple_tax, subtotal_labor_material_costs, tax_credit_amount);
						System.out.println("实际毛利="+real_gross_profit);
						//实际毛利%=实际毛利/税价全额
						double real_gross_profit_per =real_gross_profit/project_quotation;
						//净利润=实际毛利*0.75
						double net_profit =arithUtil.mul(real_gross_profit,0.75);
						//净利%=净利润/税价全额
						double net_profit_per = net_profit / project_quotation ;
						//更新实际利润等信息
						if(weakDetailDao.updataWCPDRealGrossAndNetProfitByProjectCode(real_gross_profit, real_gross_profit_per, net_profit, net_profit_per, project_code)){
							app_log.addWCPALog(id, "成本报销", audit_status, (String) SecurityUtils.getSubject().getPrincipal(), audit_info,project_code);
							wcpag.checkGroupIsApprovalFinsh(id);
							return ResultMessage.MessageToJson(0, "审核成功!");
						}else {
							app_log.addWCPALog(id, "成本报销", audit_status,(String) SecurityUtils.getSubject().getPrincipal(), audit_info,project_code);
							System.out.println("更新失败的数据:real_gross_profit="+real_gross_profit+",real_gross_profit_per="+ real_gross_profit_per+",net_profit="+ net_profit+",net_profit_per="+ net_profit_per+",project_code="+ project_code);
							wcpag.checkGroupIsApprovalFinsh(id);
							return ResultMessage.MessageToJson(1, "审核成功，更新成本成功，但是更新实际毛利相关的数据失败");
						}
					}else {
						app_log.addWCPALog(id, "成本报销", audit_status, (String) SecurityUtils.getSubject().getPrincipal(), audit_info,project_code);

						wcpag.checkGroupIsApprovalFinsh(id);
						return ResultMessage.MessageToJson(1, "审核成功，但是更新成本失败："+map.get("info"));
					}
				} catch (Exception e) {
					app_log.addWCPALog(id, "成本报销",audit_status, (String) SecurityUtils.getSubject().getPrincipal(), audit_info,project_code);
					wcpag.checkGroupIsApprovalFinsh(id);
					return ResultMessage.MessageToJson(1, "审核成功，但是更新成本失败:"+e.getMessage());
				}
			}else {
				app_log.addWCPALog(id, "成本报销",audit_status,(String) SecurityUtils.getSubject().getPrincipal(), audit_info,project_code);
				wcpag.checkGroupIsApprovalFinsh(id);
				return ResultMessage.MessageToJson(0, "审核成功!");
			}
		}else {
			return ResultMessage.MessageToJson(1, "审核失败!");
		}
	}

	/**
	 * 通过项目编号获取未审核通过的报销申请
	 * @param project_code
	 * @return
	 */
	@RequestMapping(value = "/querySimpleInfoByProject_code",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String querySimpleInfoByProject_code(String project_code) {
		System.out.println("project_code="+project_code);
		weakActaulList=weakActaulDao.querySimpleInfoByProject_code(project_code);
		int count = weakActaulList.size();
		if(count==0){
			return ResultMessage.MessageToJson(0, "无未审核的报销项");
		}else {
			return ResultMessage.MessageToJson(1, "有"+count+"条报销项目未通过审核，不可提交结项申请");
		}
				
	}
	/**
	 * 通过ID获取报销信息
	 * @param project_code
	 * @return
	 */
	@RequestMapping(value = "/querySimpleInfoByID",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String querySimpleInfoByID(String id) {
		System.out.println("id="+id);
		weakActaulList=weakActaulDao.querySimpleInfoById(id);
		if(weakActaulList.size()==1){
			return ResultMessage.ListToJson(0, weakActaulList);
		}else{
			return ResultMessage.MessageToJson(1, "获取数据异常，请重试");
		}	
	}
	/**
	 * 通过ID修改报销信息
	 * @param project_code
	 * @return
	 */
	@RequestMapping(value = "/updateActaulExpenditureInfoByID",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updateActaulExpenditureInfoByID(String id,String info) {
		System.out.println("id="+id);
		System.out.println("info="+info);
		JSONObject obj = JSONObject.fromObject(info);
		weakActaulDao = (WeakCurrentProjectsActualExpenditureDao) context.getBean("WeakCurrentProjectsActualExpenditureDao");
		weakActaulList= new ArrayList<WeakCurrentProjectsActualExpenditureBean>();
		for (int i = 0; i < obj.size(); i++) {
			weakActaulBean = new WeakCurrentProjectsActualExpenditureBean();
			//weakActaulBean.setCost_department((String)obj.get("cost_department"));
			weakActaulBean.setCost_matters((String)obj.get("cost_matters"));
			weakActaulBean.setAmount_in_original_currency(new BigDecimal((String)obj.get("amount_in_original_currency")));
			weakActaulBean.setIdle_stock(new BigDecimal((String)obj.get("idle_stock")));
			weakActaulBean.setTax_rate(new BigDecimal((String)obj.get("tax_rate")));
			weakActaulBean.setAmount_of_tax(new BigDecimal((String)obj.get("amount_of_tax")));
			weakActaulBean.setPrincipal(new BigDecimal((String)obj.get("principal")));
			weakActaulBean.setBill_type((String)obj.get("bill_type"));
			weakActaulBean.setInvoice_number((String)obj.get("invoice_number"));
			weakActaulBean.setDate_of_occurrence((String)obj.get("date_of_occurrence"));
			weakActaulBean.setPurpose_of_occurrence((String)obj.get("purpose_of_occurrence"));
			weakActaulBean.setReimbursement_number((String)obj.get("reimbursement_number"));
			weakActaulBean.setName_of_applicant((String)obj.get("name_of_applicant"));
			weakActaulBean.setApplicant_department((String)obj.get("applicant_department"));
			weakActaulBean.setEdit_remark((String)obj.get("edit_remark"));
			//weakActaulBean.setThe_last_time((String)obj.getString("the_last_time"));
			weakActaulList.add(weakActaulBean);
		}
		Map<String, String> returnStringMap = new HashMap<String, String>();
		int mysql_approval_status= weakActaulDao.WCPAAuditStatus(id);
		returnStringMap=WCPApprovalStatusUtil.approvalStatusCheck(mysql_approval_status,Integer.parseInt("2010") );
		if(returnStringMap.get("BOOLEAN").equals("false")){
			return ResultMessage.MessageToJson(1, "修改失败!当前项目的审批节点为："+returnStringMap.get("INFO"));
		}
		if(weakActaulDao.updataWeakCurrentProjectsActualExpenditureInfoByID(id,weakActaulList)){
			app_log.addWCPALog(id, "成本报销","2010",(String) SecurityUtils.getSubject().getPrincipal(), "更新成本报销内容");
			return ResultMessage.MessageToJson(0, "修改成功");
		}else{
			return ResultMessage.MessageToJson(1, "修改失败");
		}
				
	}


	
	

	/**
	 * 通过数据库ID获取报销信息，返回
	 * 1、报销提交时 的数据
	 * 2、预估毛利数据
	 * 3、实际毛利数据
	 * 
	 * @param project_code
	 * @return
	 */
	@RequestMapping(value = "/queryWeakActualInfoAndProfitsByActualID",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryWeakActualInfoAndProfitsByActualID(String id) {
		System.out.println("id="+id);
		weakActaulList=weakActaulDao.querySimpleInfoById(id);
		String resMsgString="";
		if(weakActaulList.size()==1){
			double money=Double.parseDouble(weakActaulList.get(0).getAmount_in_original_currency().toString());
			String project_code=weakActaulList.get(0).getProject_code();
			String item=weakActaulList.get(0).getCost_matters();
			double predict_gross_profit=0;
			weakDetailList=weakDetailDao.queryInfoByProjectCode(project_code);
			predict_gross_profit=calculateGrossMarginAndProfit(project_code, item, money,
					Double.parseDouble(weakDetailList.get(0).getProject_quotation().toString()),
					Double.parseDouble(weakDetailList.get(0).getSimple_tax().toString()),
					Double.parseDouble(weakDetailList.get(0).getSubtotal_labor_material_costs().toString()),
					weakDetailList.get(0).getTax_credit_amount());
			//weakDetailList=weakDetailDao.queryInfoByProjectCode(project_code);
			resMsgString = "{\"code\":0,\"info\": " +gsonUtil.List2Json(weakActaulList) +
			",\"predict_gross_profit\":"+predict_gross_profit+
			",\"real_gross_profit\":"+weakDetailList.get(0).getReal_gross_profit()+
			",\"real_gross_profit_per\":"+weakDetailList.get(0).getReal_gross_profit_per()+
			",\"net_profit\":"+weakDetailList.get(0).getNet_profit()+
			",\"net_profit_per\":"+weakDetailList.get(0).getNet_profit_per()+ 
			",\"project_quotation\":"+weakDetailList.get(0).getProject_quotation()+"}";
			return resMsgString;
		}else{
			return ResultMessage.MessageToJson(1, "获取数据异常，请重试");
		}	
	}
	

	/**
	 * 计算预估毛利，同时应该等于审核通过时的毛利
	 * 因为审核通过时是获取项目当前的数据的，所以可能出现与界面显示的预估毛利存在差别的问题
	 * 但是也避免了因数据更新未同步导致的问题
	 * @param project_code项目编码
	 * @param item上报类型
	 * @param money上报金额
	 * @return实际毛利real_gross_profit
	 */
	private double calculateGrossMarginAndProfit(String project_code,String item,double money,double project_quotation,
			double simple_tax,double subtotal_labor_material_costs,double tax_credit_amount) {
		//weakSimpleList=weakSimpleDao.queryProjectInfoByProjectCode(project_code);
		System.out.println("project_code="+project_code);
		System.out.println("item="+item);
		System.out.println("money="+money);
		//project_code = "RDWB-00001";/*项目编码*/
		//item = "抵税金额(增值税专用发票)";/*上报类型*/
		//money = 10;/*上报金额*/
		//double project_quotation =Double.parseDouble(weakDetailList.get(0).getProject_quotation().toString());/*项目报价*/
		System.out.println("project_quotation="+project_quotation);
		//double simple_tax=Double.parseDouble(weakDetailList.get(0).getSimple_tax().toString());/*税率 */
		System.out.println("simple_tax="+simple_tax);
		double management_costs =arithUtil.mul(project_quotation, 0.02);/*管理成本*/
		System.out.println("management_costs="+management_costs);
		//double subtotal_labor_material_costs=Double.parseDouble(weakDetailList.get(0).getSubtotal_labor_material_costs().toString()); /*人工费用小计*/

		System.out.println("计算前：subtotal_labor_material_costs="+subtotal_labor_material_costs);
		
		double tax_reimbursement=0;/*补税*/
		double tempValue=arithUtil.add(1 , simple_tax);
		System.out.println("1.tempValue="+tempValue);
		tempValue=project_quotation/tempValue;
		System.out.println("2.tempValue="+tempValue);
		tempValue=arithUtil.mul(tempValue, simple_tax);
		System.out.println("3.tempValue="+tempValue);
		double tax_money=tempValue;/*税额 6~tempValue% */
		System.out.println("tax_money="+tax_money);
		//double tax_credit_amount=weakDetailList.get(0).getTax_credit_amount();/*抵税金额(增值税专用发票)*/
		System.out.println("tax_credit_amount="+tax_credit_amount);
		
		if(item.equals("抵税金额(增值税专用发票)")){
			tax_credit_amount=tax_credit_amount+ money;
			//subtotal_labor_material_costs=arithUtil.sub(subtotal_labor_material_costs, money);
			if(arithUtil.sub(tax_money, tax_credit_amount)>0){
				System.out.println(1);
				tax_reimbursement =arithUtil.add(arithUtil.mul(arithUtil.sub(tax_money, arithUtil.add(tax_credit_amount, money)), 0.12), arithUtil.mul(project_quotation, 0.0005)) ;
			}else {
				System.out.println(2);
				tax_reimbursement = arithUtil.mul(subtotal_labor_material_costs, 0.0005);
			}
		}else{
			subtotal_labor_material_costs=arithUtil.add(subtotal_labor_material_costs, money);
			if(arithUtil.sub(tax_money, tax_credit_amount)>0){
				System.out.println(3);
				tempValue=arithUtil.sub(tax_money, tax_credit_amount);
				System.out.println("1.tempValue="+tempValue);
				tempValue=arithUtil.mul(tempValue,0.12);
				System.out.println("2.tempValue="+tempValue);
				tempValue=arithUtil.add(tempValue,arithUtil.mul(project_quotation, 0.0005));
				System.out.println("3.tempValue="+tempValue);
				tax_reimbursement =tempValue ;
			}else {
				System.out.println(4);
				tax_reimbursement =arithUtil.mul(subtotal_labor_material_costs, 0.0005);
			}
		}
		System.out.println("计算后：subtotal_labor_material_costs="+subtotal_labor_material_costs);
		System.out.println("计算后：tax_reimbursement="+tax_reimbursement);
		System.out.println("计算后：tax_credit_amount="+tax_credit_amount);
		//管理费及税务小计=管理成本2% +(财务填)补税
		double subtotal_management_fees_and_taxes= arithUtil.add(management_costs, tax_reimbursement);
		System.out.println("subtotal_management_fees_and_taxes="+subtotal_management_fees_and_taxes);
		//实际毛利=税价全额-人工材料费用小计-管理费及税务小计
		double real_gross_profit =  arithUtil.sub(arithUtil.sub(project_quotation, subtotal_labor_material_costs),subtotal_management_fees_and_taxes);
		System.out.println("real_gross_profit="+real_gross_profit);
		return real_gross_profit;
	}
	/**
	 * 审核通过后计算的毛利
	 * @param project_code项目编码
	 * @param item上报类型
	 * @param money上报金额
	 * @return实际毛利real_gross_profit
	 */
	private double calculateGrossMarginAndProfitWithApproved(String project_code,String item,double money,double project_quotation,
			double simple_tax,double subtotal_labor_material_costs,double tax_credit_amount) {
		//weakSimpleList=weakSimpleDao.queryProjectInfoByProjectCode(project_code);
		System.out.println("project_code="+project_code);
		System.out.println("item="+item);
		System.out.println("money="+money);
		//project_code = "RDWB-00001";/*项目编码*/
		//item = "抵税金额(增值税专用发票)";/*上报类型*/
		//money = 10;/*上报金额*/
		//double project_quotation =Double.parseDouble(weakDetailList.get(0).getProject_quotation().toString());/*项目报价*/
		System.out.println("project_quotation="+project_quotation);
		//double simple_tax=Double.parseDouble(weakDetailList.get(0).getSimple_tax().toString());/*税率 */
		System.out.println("simple_tax="+simple_tax);
		double management_costs =arithUtil.mul(project_quotation, 0.02);/*管理成本*/
		System.out.println("management_costs="+management_costs);
		//double subtotal_labor_material_costs=Double.parseDouble(weakDetailList.get(0).getSubtotal_labor_material_costs().toString()); /*人工费用小计*/

		//System.out.println("计算前：subtotal_labor_material_costs="+subtotal_labor_material_costs);
		
		double tax_reimbursement=0;/*补税*/
		double tempValue=arithUtil.add(1 , simple_tax);
		System.out.println("1.tempValue="+tempValue);
		tempValue=project_quotation/tempValue;
		System.out.println("2.tempValue="+tempValue);
		tempValue=arithUtil.mul(tempValue, simple_tax);
		System.out.println("3.tempValue="+tempValue);
		double tax_money=tempValue;/*税额 6~tempValue% */
		System.out.println("tax_money="+tax_money);
		//double tax_credit_amount=weakDetailList.get(0).getTax_credit_amount();/*抵税金额(增值税专用发票)*/
		System.out.println("tax_credit_amount="+tax_credit_amount);
		if(item.equals("抵税金额(增值税专用发票)")){
			//subtotal_labor_material_costs=arithUtil.sub(subtotal_labor_material_costs, money);
			//tax_credit_amount=tax_credit_amount+ money;
			if(arithUtil.sub(tax_money, tax_credit_amount)>0){
				System.out.println(1);
				tax_reimbursement =arithUtil.add(arithUtil.mul(arithUtil.sub(tax_money, arithUtil.add(tax_credit_amount, money)), 0.12), arithUtil.mul(project_quotation, 0.0005)) ;
			}else {
				System.out.println(2);
				tax_reimbursement = arithUtil.mul(subtotal_labor_material_costs, 0.0005);
			}
		}else{
			//subtotal_labor_material_costs=arithUtil.add(subtotal_labor_material_costs, money);
			if(arithUtil.sub(tax_money, tax_credit_amount)>0){
				System.out.println(3);
				tempValue=arithUtil.sub(tax_money, tax_credit_amount);
				System.out.println("1.tempValue="+tempValue);
				tempValue=arithUtil.mul(tempValue,0.12);
				System.out.println("2.tempValue="+tempValue);
				tempValue=arithUtil.add(tempValue,arithUtil.mul(project_quotation, 0.0005));
				System.out.println("3.tempValue="+tempValue);
				tax_reimbursement =tempValue ;
			}else {
				System.out.println(4);
				tax_reimbursement =arithUtil.mul(subtotal_labor_material_costs, 0.0005);
			}
		}
		System.out.println("计算后：subtotal_labor_material_costs="+subtotal_labor_material_costs);
		System.out.println("计算后：tax_reimbursement="+tax_reimbursement);
		System.out.println("计算后：tax_credit_amount="+tax_credit_amount);
		//管理费及税务小计=管理成本2% +(财务填)补税
		double subtotal_management_fees_and_taxes= arithUtil.add(management_costs, tax_reimbursement);
		System.out.println("subtotal_management_fees_and_taxes="+subtotal_management_fees_and_taxes);
		//实际毛利=税价全额-人工材料费用小计-管理费及税务小计
		double real_gross_profit =  arithUtil.sub(arithUtil.sub(project_quotation, subtotal_labor_material_costs),subtotal_management_fees_and_taxes);
		System.out.println("real_gross_profit="+real_gross_profit);
		return real_gross_profit;
	}
	
	
	/**
	 * 通过项目ID获取项目的报销内容
	 */
	@RequestMapping(value = "/queryWeakActualReimbursementByProjectCode",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryWeakActualReimbursementByProjectCode(String project_code) {
		weakActaulList=weakActaulDao.queryWeakActualReimbursementByProjectCode(project_code);
		if(weakActaulList.size()==0){
			return ResultMessage.MessageToJson(1, "项目无报销数据");
		}
		return ResultMessage.ListToJson(0, weakActaulList);
	}
	
	
}
