package com.workplan.handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.workplan.bean.ParameterBean;
import com.workplan.bean.UserInfoBean;
import com.workplan.bean.WeakCurrentProjectsDetailBean;
import com.workplan.bean.WeakCurrentProjectsSimpleBean;
import com.workplan.dao.UserInfoDao;
import com.workplan.dao.WeakCurrentProjectsDetailDao;
import com.workplan.dao.WeakCurrentProjectsSimpleDao;
import com.workplan.tools.ArithUtil;
import com.workplan.tools.GetDateTimeNow;
import com.workplan.tools.GsonUtil;
import com.workplan.tools.ResultMessage;
@Controller
public class WeakCurrentProjectsDetailHandler {
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	WeakCurrentProjectsSimpleDao weakSimpleDao = (WeakCurrentProjectsSimpleDao) context.getBean("WeakCurrentProjectsSimpleDao");
	WeakCurrentProjectsDetailDao weakDetailDao = (WeakCurrentProjectsDetailDao) context.getBean("WeakCurrentProjectsDetailDao");
	UserInfoDao userInfoDao = (UserInfoDao) context.getBean("UserInfoDao");
	
	List<WeakCurrentProjectsSimpleBean> weakSimpleList = new ArrayList<WeakCurrentProjectsSimpleBean>();
	List<WeakCurrentProjectsDetailBean> weakDetailList = new ArrayList<WeakCurrentProjectsDetailBean>();
	WeakCurrentProjectsApprovaLogHandler app_log=new WeakCurrentProjectsApprovaLogHandler();
	List<ParameterBean> parameterlist =new ArrayList<ParameterBean>();
	ParameterHandler parameterHandler =new ParameterHandler();
	GsonUtil gsonUtil=new GsonUtil();
	
	/**
	 * 新增立项：管理员审核通过后，就会将立项信息添加到详情表
	 * @param info
	 * @return
	 
	public Map<String, String> addWeakCurrentProjectsDetail(String project_code,BigDecimal cost_share) {
		System.out.println("project_code="+project_code);
		System.out.println("cost_share="+cost_share);
		Map<String, String> map=new HashMap<String, String>();
		try {
			if(weakDetailDao.insertWeakCurrentProjectsDetail(project_code, cost_share)){
				map.put("sta", "true");
			    map.put("info", "将立项信息添加到详情表成功");
			}else {
				map.put("sta", "false");
			    map.put("info", "将立项信息添加到详情表失败");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());

			map.put("sta", "false");
		    map.put("info", e.getMessage());
		}
		return map;
	}
	*/
	

	/**
	 * 多选下拉框
	 * @return 
	 */
	@RequestMapping(value = "/getWCProjectsDetailFormSelectsInfo",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getWCProjectsDetailFormSelectsInfo() {
		System.out.println("/getWCProjectsDetailFormSelectsInfo");
		weakSimpleList=weakSimpleDao.queryAllProjectName();
		
		String reString="";
		//状态
		reString+="{\"name\": \"审核状态\", \"type\": \"optgroup\"},";
		parameterlist=parameterHandler.queryAllParameterByPidList("14");
		if(parameterlist.size()==0){
			reString="{\"code\":1,\"msg\":\"审核状态参数获取异常!14\",\"data\":[]}";
			return reString;
		}
		for (int i = 0; i < parameterlist.size(); i++) {
			reString+="{\"name\": \""+parameterlist.get(i).getText()+"\", \"value\": \""+parameterlist.get(i).getValue()+"\",\"selected\":\""+parameterlist.get(i).getSelect()+"\",\"disabled\":\""+parameterlist.get(i).getDisable()+"\"},";
		}
		/*reString+="{\"name\": \"已立项\", \"value\": \"AuDiTsTaTuS|1021\",\"selected\":\"selected\"}," +
				"{\"name\": \"待审核\", \"value\": \"AuDiTsTaTuS|0$\",\"selected\":\"selected\"}," +
				"{\"name\": \"已通过\", \"value\": \"AuDiTsTaTuS|1$\",\"selected\":\"\"}," +
				"{\"name\": \"已驳回\", \"value\": \"AuDiTsTaTuS|2$\",\"selected\":\"selected\"}," +
				"{\"name\": \"已结项\", \"value\": \"AuDiTsTaTuS|9999\",\"selected\":\"\"},";

		*/
		if(SecurityUtils.getSubject().hasRole("ruod")){//用户为大区负责人权限
			if(!SecurityUtils.getSubject().hasRole("super_admin")){
				reString+="{\"name\": \"所属大区\", \"type\": \"optgroup\"},";
				List<UserInfoBean> userList=userInfoDao.queryAll((String) SecurityUtils.getSubject().getPrincipal());
				for (int i = 0; i < 1; i++) {
					reString+="{\"name\": \""+userList.get(i).getWcp_area()+"\", \"value\":\"PrOjEcTDePaRtMeNt|"+userList.get(i).getWcp_area()+"\",\"selected\":\"selected\",\"disabled\":\"disabled\"},";
				}
			}
		}
		if(SecurityUtils.getSubject().hasRole("wck_per_user")){//用户是否为普通的立项用户
			reString+="{\"name\": \"录入人/责任人\", \"type\": \"optgroup\"},";
			reString+="{\"name\": \""+(String) SecurityUtils.getSubject().getPrincipal()+"\", \"value\": \"SuBmItTeR|"+(String) SecurityUtils.getSubject().getPrincipal()+"\",\"selected\":\"selected\",\"disabled\":\"disabled\"},";
			//项目名称
			reString+="{\"name\": \"项目名称\", \"type\": \"optgroup\"},";
			String loginUserString=(String) SecurityUtils.getSubject().getPrincipal();
			for(int a= 0;a<weakSimpleList.size();a++){
				if(weakSimpleList.get(a).getProject_leader().equals(loginUserString)){
					reString+="{\"name\": \""+weakSimpleList.get(a).getProject_code()+"-"+weakSimpleList.get(a).getProject_name()+"\", \"value\":\"PrOjEcTcOde|"+weakSimpleList.get(a).getProject_code()+"\",\"selected\":\"\"},";
				}
			}
		}else {
			//项目名称
			reString+="{\"name\": \"项目名称\", \"type\": \"optgroup\"},";
			for(int a= 0;a<weakSimpleList.size();a++){
				reString+="{\"name\": \""+weakSimpleList.get(a).getProject_code()+"-"+weakSimpleList.get(a).getProject_name()+"\", \"value\":\"PrOjEcTcOde|"+weakSimpleList.get(a).getProject_code()+"\",\"selected\":\"\"},";
			}
		}
		
		
		reString="{\"code\":0,\"msg\":\"success\",\"data\":["+reString.substring(0, reString.length()-1)+"]}";
		System.out.println(reString);
		return reString;
	}
	/**
	 * 搜索：根据下拉框条件获取数据
	 * @param SearchCondition
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/queryWCProjectsDetailBySearchCondition",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryWCProjectsDetailBySearchCondition(String SearchCondition,int page, int limit) {
		//System.out.println("SearchCondition="+SearchCondition);
		//System.out.println("page="+page);
		//System.out.println("limit="+limit);
		if(SearchCondition==null){
			//System.out.println("SearchCondition==null");
			SearchCondition="";
		}
		weakDetailList=new ArrayList<WeakCurrentProjectsDetailBean>();
		String arr[]=SearchCondition.split(",");
		if(SearchCondition!=null&&SearchCondition!=""){
			System.out.println("有"+arr.length+"个条件");
		}else {
			System.out.println(arr.length+"没有条件");
			return ResultMessage.ListtoLayuiTable(0, gsonUtil.List2Json(weakDetailList));
		}
		for(int a=0;a<arr.length;a++){
			//System.out.println("arr[a]="+arr[a].toString());
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
		String project_area_department=null;
		if(SecurityUtils.getSubject().hasRole("ruod")){//用户为大区负责人权限
			if(!SecurityUtils.getSubject().hasRole("super_admin")){
				List<UserInfoBean> userList=userInfoDao.queryAll((String) SecurityUtils.getSubject().getPrincipal());
				for(int a= 0;a<1;a++){
					project_area_department=userList.get(a).getWcp_area();
				}
			}
		}
		String submitter=null;
		if(SecurityUtils.getSubject().hasRole("wck_per_user")){//用户是否为普通的立项用户
			submitter=(String) SecurityUtils.getSubject().getPrincipal();
		}
		
		int Counts= weakDetailDao.queryAllInfoCountsBySearchCondition(AuditStatus, ProjectCode,submitter,project_area_department);
		weakDetailList = weakDetailDao.queryAllInfoWithFirstBySearchCondition(AuditStatus, ProjectCode, page, limit,submitter,project_area_department);
		
		//String str=gsonUtil.List2Json(weakDetailList);
		//System.out.println("str="+str);
		return ResultMessage.ListtoLayuiTable(Counts, gsonUtil.List2Json(weakDetailList));
	}

	/**
	 * 通过项目编码获取项目信息
	 * @param project_code
	 * @return
	 */
	@RequestMapping(value = "/queryWCProjectsSimpleDetailAllInfoByProjectCode",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryWCProjectsSimpleDetailAllInfoByProjectCode(String project_code) {
		try {
			System.out.println("project_code="+project_code);
			weakDetailList = weakDetailDao.queryInfoByProjectCode(project_code);
			return ResultMessage.ListToJson(0, weakDetailList);
		} catch (Exception e) {
			return ResultMessage.MessageToJson(1, e.getMessage());
		}
	}
	/**
	 * 通过项目编码获取项目质保金预计回款日期和实际回款日期
	 * @param project_code
	 * @return 质保金预计回款日期,实际回款日期
	 */
	@RequestMapping(value = "/queryWCPSDReturnedMoneyDateInfoByProjectCode",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryWCPSDReturnedMoneyDateInfoByProjectCode(String project_code) {
		try {
			System.out.println("project_code="+project_code);
			String WCPDReturnedMoneyDate=weakDetailDao.WCPDReturnedMoneyDate(project_code);
			return ResultMessage.MessageToJson(0, WCPDReturnedMoneyDate);
		} catch (Exception e) {
			return ResultMessage.MessageToJson(1, "获取数据异常");
		}
	}
	/**
	 * 通过项目编码更新项目质保金实际回款日期
	 * @param reminder_date
	 * @param project_code
	 * @return
	 */
	@RequestMapping(value = "/updataWCPD_RD_ByProjectCode",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updataWCPD_RD_ByProjectCode(String reminder_date,String audit_status,String project_code) {
		try {
			if(weakDetailDao.updataWCPDReminderDateByProjectCode(reminder_date,audit_status, project_code)){
				app_log.addWCPALog(project_code, "申请质保金回款确认", audit_status, (String) SecurityUtils.getSubject().getPrincipal(), "N");
				return ResultMessage.MessageToJson(0, "提交成功");
			}else {
			return ResultMessage.MessageToJson(1, "提交失败");
			}
		} catch (Exception e) {
			return ResultMessage.MessageToJson(1, "提交失败："+e.getMessage());
		}
	}

	/**
	 * 通过项目编码获取项目应该收回的质保金金额
	 * @param project_code
	 * @return
	 */
	@RequestMapping(value = "/queryWCPDSystemReturnedMoneyByProjectCode",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryWCPDSystemReturnedMoneyByProjectCode(String project_code) {
		try {
			System.out.println("project_code="+project_code);
			BigDecimal returned_money_ishave = weakDetailDao.queryWCPDSystemReturnedMoney(project_code);
			if(returned_money_ishave==null){
				return ResultMessage.MessageToJson(1,"获取应回款金额失败");
			}else {
				return ResultMessage.MessageToJson(0, returned_money_ishave+"");
			}
		} catch (Exception e) {
			return ResultMessage.MessageToJson(1, "获取应回款金额失败："+e.getMessage());
		}
	}
	/**
	 * 通过项目编码更新项目质保金实际回款数额
	 * @param returned_money
	 * @param returned_money_remark
	 * @param project_code
	 * @return
	 */
	@RequestMapping(value = "/updataWCPD_RM_AndRemarkByProjectCode",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updataWCPD_RM_AndRemarkByProjectCode(String returned_money,String returned_money_remark,String project_code) {
		try {
			if(returned_money_remark.equals(""))
				returned_money_remark="N";
			if(weakDetailDao.updataWCPDReturnedMondyAndRemarkByProjectCode(returned_money, returned_money_remark, project_code)){
				app_log.addWCPALog(project_code, "申请质保金回款确认", "9980", (String) SecurityUtils.getSubject().getPrincipal(), returned_money_remark);
				return ResultMessage.MessageToJson(0, "提交成功");
			}else {
			return ResultMessage.MessageToJson(1, "提交失败");
			}
		} catch (Exception e) {
			return ResultMessage.MessageToJson(1, "提交失败："+e.getMessage());
		}
	}
   /**
    * 根据项目ID更新进度
    * @param project_progress 进度
    * @param project_code 项目编码
    * @param returned_money_ishave 是否有质保金
    * @param returned_money_date 质保金回款日期
    * @param returned_money_point 质保金点数
    * @param remaining_stock 余材库存
    * @param received_amount 已收款金额
    * @param amount_to_be_collected 待收金额
    * @return
    */
	@RequestMapping(value = "/updataWeakCurrentProjectsDetailProgressByProjectCode",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updataWeakCurrentProjectsDetailProgressByProjectCode(
			String project_progress,
			String project_code,
			String returned_money_ishave,
			String returned_money_date,
			String returned_money_point,
			String remaining_stock,
			String received_amount,
			String amount_to_be_collected,
			String project_progress_remake) {
		System.out.println("/updataWeakCurrentProjectsDetailProgressByProjectCode");
		System.out.println("project_progress="+project_progress);
		System.out.println("project_code="+project_code);
		if(!project_progress.equals("100")){
			returned_money_ishave="";
			returned_money_date=null;
			returned_money_point=null;
			remaining_stock=null;
			received_amount=null;
			amount_to_be_collected=null;
			project_progress_remake=null;
		}else {
			if(returned_money_ishave.equals("N")){
				returned_money_date=null;
				returned_money_point="0";
			}
		}
		System.out.println("returned_money_ishave="+returned_money_ishave);
		System.out.println("returned_money_date="+returned_money_date);
		System.out.println("returned_money_point="+returned_money_point);
		System.out.println("remaining_stock="+remaining_stock);
		System.out.println("received_amount="+received_amount);
		System.out.println("amount_to_be_collected="+amount_to_be_collected);
		System.out.println("project_progress_remake="+project_progress_remake);
		try {
			boolean updata=weakDetailDao.updataWeakCurrentProjectsDetailProgressByProjectCode(project_progress, project_code, returned_money_ishave, returned_money_date, returned_money_point, remaining_stock, received_amount, amount_to_be_collected,project_progress_remake);
			System.out.println(updata);
			if (updata==true) {
				if(project_progress.equals("100")){
					//100的进度为申请结项，申请结项将完成所有数据的计算
					//更新项目审核状态
					if(weakDetailDao.updataWeakCurrentProjectsDetailAuditStatusAndAuditInsoByProjectCode("3010", "N", project_code)){
						app_log.addWCPALog(project_code, "申请结项", "3010", (String) SecurityUtils.getSubject().getPrincipal(), "申请结项");
						//更新数据
						if(updateWCPDetailFinshInfoByProjectCode(project_code)){
							return ResultMessage.MessageToJson(0, "申请结项成功");
						}else {
							return ResultMessage.MessageToJson(1, "申请结项成功，但是更新部分数据失败");
						}
					}else {
						return ResultMessage.MessageToJson(1, "申请结项成功，审核状态更新失败");
					}
				}else {
					app_log.addWCPALog(project_code, "更新项目进度", "NULL", (String) SecurityUtils.getSubject().getPrincipal(), "更新项目进度到"+project_progress);
					return ResultMessage.MessageToJson(0, "进度更新成功");
				}
			}else {
				return ResultMessage.MessageToJson(1, "进度更新失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.MessageToJson(1, "进度更新失败："+e.getMessage());
		}
	}

	/**
	 * 立项审核通过，通过项目编码更新相关数据
	 * @param project_code
	 * @return
	
	public Map<String ,String> updateWCPDetailInfoByProjectCode(String project_code) {
		Map<String ,String> map = new HashMap<String, String>();
		try {
			System.out.println("project_code="+project_code);
			weakDetailList = weakDetailDao.queryInfoByProjectCode(project_code);
			BigDecimal subtotal_cost = weakDetailList.get(0).getSubtotal_cost();
			BigDecimal project_quotation = weakDetailList.get(0).getProject_quotation();
			BigDecimal simple_tax = weakDetailList.get(0).getSimple_tax();
			System.out.println("subtotal_cost="+subtotal_cost);
			System.out.println("project_quotation="+project_quotation);
			System.out.println("simple_tax="+simple_tax);
			System.out.println("project_code="+project_code);
			if(weakDetailDao.updateWeakCurrentProjectsDetailByProjectCode(subtotal_cost, project_quotation, simple_tax, project_code)){
				map.put("sta", "true");
			}else {
				map.put("sta", "false");
			    map.put("info", "立项后更新相关数据失败");
			}
			return map;
		} catch (Exception e) {
			System.out.println(WeakCurrentProjectsDetailHandler.class+"-updateWCPDetailInfoByProjectCode-"+e.getMessage());
			map.put("sta", "false");
		    map.put("info", e.getMessage());
			return map;
		}
	} */

	/**
	 * 根据项目ID更新成本数据
	 * WeakCurrentProjectsActualExpenditureHandler.java - updataWeakCurrentProjectsActualExpenditureAuditStatusByID
	 * @param project_code
	 * @param item 类别
	 * @param money 金额
	 * @return
	 */
	//@RequestMapping(value = "/updataWeakCurrentProjectsDetailProgressByProjectCode",produces="text/html;charset=UTF-8")
	//@ResponseBody
	public Map<String ,String> updataWeakCurrentProjectsDetailProgressByProjectCode(String project_code,String item,BigDecimal money) {
		Map<String ,String> map = new HashMap<String, String>();
	      
		try {
			//material_cost_including_tax//材料费（含税）
			//tax_credit_amount//抵税金额(增值税专用发票）
			//outsourcing_including_tax//外包人工费、水电（含税）
			//transport_fees//运杂费
			//allocation_of_fixed_assets_of_instruments//工具固定资产分摊
			//entertain//招待
			//subtotal_labor_material_costs//人工材料费用小计
			//subtotal_labor_material_costs = material_cost_including_tax+outsourcing_including_tax+transport_fees + allocation_of_fixed_assets_of_instruments+entertain-tax_credit_amount
			if(item.equals("材料费(含税)")){
					item="material_cost_including_tax";
			}else if(item.equals("抵税金额(增值税专用发票)")){
				item="tax_credit_amount";
			}else if(item.equals("外包人工费、水电(含税)")){
				item="outsourcing_including_tax";
			}else if(item.equals("运杂费")){
				item="transport_fees";
			}else if(item.equals("工具固定资产分摊")){
				item="allocation_of_fixed_assets_of_instruments";
			}else if(item.equals("招待")){
				item="entertain";
			}else {
				System.out.println(WeakCurrentProjectsDetailHandler.class+"-费用类别item未匹配到正确的值");
				map.put("sta", "false");
			    map.put("info", "item参数未匹配到正确的值");
				return map;
			}
			if(weakDetailDao.updataWeakCurrentProjectsDetailProgressByProjectCode(project_code, item, money)){
				System.out.println(WeakCurrentProjectsDetailHandler.class+"-更新成本数据成功");
				map.put("sta", "true");
			    map.put("info", "更新成本数据成功");
				return map;
			}else {
				System.out.println(WeakCurrentProjectsDetailHandler.class+"-更新成本数据失败");
				map.put("sta", "false");
			    map.put("info", "更新成本数据失败");
				return map;
			}
		} catch (Exception e) {
			System.out.println(WeakCurrentProjectsDetailHandler.class+"-updataWeakCurrentProjectsDetailProgressByProjectCode-"+e.getMessage());
			map.put("sta", "false");
		    map.put("info", e.getMessage());
			return map;
		}
	}
	


	/**
	 * 申请结项审批
	 * @param project_code
	 * @return
	 */
	@RequestMapping(value = "/updateWCPDetailClosingByProjectCode",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updateWCPDetailClosingByProjectCode(String project_code,String audit_info,String audit_status) {
		System.out.println("project_code="+project_code);

		Map<String, String> returnStringMap = new HashMap<String, String>();
		int mysql_approval_status= weakDetailDao.WCPDAuditStatus(project_code);
		returnStringMap=WCPApprovalStatusUtil.approvalStatusCheck(mysql_approval_status,Integer.parseInt(audit_status) );
		if(returnStringMap.get("BOOLEAN").equals("false")){
			return ResultMessage.MessageToJson(1, "审批失败!当前项目的审批节点为："+returnStringMap.get("INFO"));
		}
		if(weakDetailDao.updataWeakCurrentProjectsDetailAuditStatusAndAuditInsoByProjectCode(audit_status, audit_info, project_code)){
			if(audit_status.equals("3031"))//申请结项-已通过
			{
				app_log.addWCPALog(project_code, "申请结项", audit_status, (String) SecurityUtils.getSubject().getPrincipal(), audit_info);
				if(updateWCPDetailFinshInfoByProjectCode(project_code)){
					return ResultMessage.MessageToJson(0, "审核成功");
				}else {
					return ResultMessage.MessageToJson(1, "审核成功，但是更新项目数据失败");
				}
			}else {
				app_log.addWCPALog(project_code, "申请结项",audit_status, (String) SecurityUtils.getSubject().getPrincipal(), audit_info);
				return ResultMessage.MessageToJson(0, "审核成功");
			}
			
		}else {
			System.out.println("审核失败");
			return ResultMessage.MessageToJson(0, "审核失败");
		}
	}

	/**
	 * 申请结项时更新剩余数据
	 * @param project_code
	 * @return
	 * 	
	 * (税额 6~11% - 抵税金额(增值税专用发票))  >0
	 * tax_money - tax_credit_amount > 0
	 * item,item,money,project_code
	 * 
	 * (税额 6~11% - 抵税金额(增值税专用发票))  <=0
	 * item,item,money,project_code
	 *
	 */
	private boolean updateWCPDetailFinshInfoByProjectCode(String project_code) {
		weakDetailList = weakDetailDao.queryInfoByProjectCode(project_code);
		try {
			double tax_money = weakDetailList.get(0).getTax_money();
			double tax_credit_amount = weakDetailList.get(0).getTax_credit_amount();
			System.out.println("ArithUtil.sub(tax_money, tax_credit_amount) = "+tax_money+"-"+tax_credit_amount+"="+ArithUtil.sub(tax_money, tax_credit_amount));
			//(税额 6~11% - 抵税金额(增值税专用发票))  与    0   的对比
			if( ArithUtil.sub(tax_money, tax_credit_amount)> 0){
				if(weakDetailDao.colsingWeakCurrentProjectsDetailProgressByProjectCodeThanZero(project_code)){
					System.out.println("申请结项时更新剩余数据成功");
					return true;
				}else{
					System.out.println("申请结项时更新剩余数据失败");
					return false;
				}
			}else{
				if(weakDetailDao.colsingWeakCurrentProjectsDetailProgressByProjectCodeLessThanOrEqualToZero(project_code)){
					System.out.println("申请结项时更新剩余数据成功");
					return true;
				}else{
					System.out.println("申请结项时更新剩余数据失败");
					return false;
				}
			}
		} catch (Exception e) {
			System.out.println("申请结项时更新剩余数据："+e.getMessage());
			return false;
		}
	}
	 
	/**
	 * 根据用户账号和角色，提醒用户是否有质保金进入回款阶段
	 * 提早2周提醒用户
	 * 1、即将回款
	 * 2、回款超时
	 * @return
	 */
	
	@RequestMapping(value = "/getWCPDReturnedIsComing",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getWCPDReturnedIsComing() {
		weakDetailDao = (WeakCurrentProjectsDetailDao) context.getBean("WeakCurrentProjectsDetailDao");
		List<WeakCurrentProjectsDetailBean> weakDetailList = new ArrayList<WeakCurrentProjectsDetailBean>();//即将过期
		List<WeakCurrentProjectsDetailBean> weakDetailListWarn = new ArrayList<WeakCurrentProjectsDetailBean>();//已经过期
		GetDateTimeNow getDateTimeNow = new GetDateTimeNow();
		String startDateString=getDateTimeNow.getDate();
		String endDateString=getDateTimeNow.getDate(14);
		String userId=(String) SecurityUtils.getSubject().getPrincipal();
		if(SecurityUtils.getSubject().hasRole("ruof")){//用户为财务
			System.out.println("用户为财务，获取所有地区数据");
			weakDetailList=weakDetailDao.getWCPDReturnedIsComingNoDepartment(startDateString,endDateString);
			weakDetailListWarn=weakDetailDao.getWCPDReturnedIsComingNoDepartment(startDateString);
		}else if(SecurityUtils.getSubject().hasRole("ruod")){//用户为大区负责人
			System.out.println("用户为大区负责人，获取所属地区数据");
			UserInfoBean userBean;
			try {
				//通过用户工号获取用户角色
				userBean = userInfoDao.queryForInfoBean(userId);
				System.out.println("用户所属地区:"+userBean.getWcp_area());
			} catch (Exception e) {
				e.printStackTrace();
				return ResultMessage.MessageToJson(1, "获取用户所属地区数据失败："+e.getMessage());
			}
			/*if(userBean.getWcp_area().equals("")){//用户的地区为空，则返回所有数据
				weakDetailList=weakDetailDao.getWCPDReturnedIsComingNoDepartment(startDateString,endDateString);
				weakDetailListWarn=weakDetailDao.getWCPDReturnedIsComingNoDepartment(startDateString);
			}else {*/
				weakDetailList=weakDetailDao.getWCPDReturnedIsComingByDepartment(startDateString,endDateString,userBean.getWcp_area());
				weakDetailListWarn=weakDetailDao.getWCPDReturnedIsComingByDepartment(startDateString,userBean.getWcp_area());
			//}
		}else if(SecurityUtils.getSubject().hasRole("wck_per_user")){//用户为普通用户
			System.out.println("用户为普通用户，工号为:"+userId);
			weakDetailList=weakDetailDao.getWCPDReturnedIsComingByPersonal(startDateString,endDateString,userId);
			weakDetailListWarn=weakDetailDao.getWCPDReturnedIsComingByPersonal(startDateString,userId);
		}
		return ResultMessage.ListsToJson(0, weakDetailList, weakDetailListWarn);
	}
	/**
	 * 通过项目编码更新项目的质保金回款状态
	 * @param project_code
	 * @return
	 */
	@RequestMapping(value = "/updataWeakCurrentProjectsDetailReturnedMoneyByProjectCode",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updataWeakCurrentProjectsDetailReturnedMoneyByProjectCode(String audit_status ,String audit_info ,String project_code) {
			System.out.println("audit_status="+audit_status);
			System.out.println("audit_info="+audit_info);
			System.out.println("project_code="+project_code);

			Map<String, String> returnStringMap = new HashMap<String, String>();
			int mysql_approval_status= weakDetailDao.WCPDAuditStatus(project_code);
			returnStringMap=WCPApprovalStatusUtil.approvalStatusCheck(mysql_approval_status,Integer.parseInt(audit_status) );
			if(returnStringMap.get("BOOLEAN").equals("false")){
				return ResultMessage.MessageToJson(1, "审批失败!当前项目的审批节点为："+returnStringMap.get("INFO"));
			}
			try {
				if(weakDetailDao.updataWeakCurrentProjectsDetailAuditStatusAndAuditInsoByProjectCode(audit_status, audit_info, project_code)){
					app_log.addWCPALog(project_code, "质保金回款", audit_status, (String) SecurityUtils.getSubject().getPrincipal(), audit_info);
					return ResultMessage.MessageToJson(0, "审批成功");
				}else {
					return ResultMessage.MessageToJson(1, "审批失败");
				}
			} catch (Exception e) {
				return ResultMessage.MessageToJson(1, "审批失败："+e.getMessage());
			}
	}

}
