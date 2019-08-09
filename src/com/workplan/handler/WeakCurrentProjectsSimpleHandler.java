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
import com.workplan.tools.GsonUtil;
import com.workplan.tools.ResultMessage;
@Controller
public class WeakCurrentProjectsSimpleHandler {
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	WeakCurrentProjectsSimpleDao weakSimpleDao = (WeakCurrentProjectsSimpleDao) context.getBean("WeakCurrentProjectsSimpleDao");
	WeakCurrentProjectsDetailDao weakDetailDao = (WeakCurrentProjectsDetailDao) context.getBean("WeakCurrentProjectsDetailDao");
	UserInfoDao userInfoDao = (UserInfoDao) context.getBean("UserInfoDao");
	
	List<WeakCurrentProjectsSimpleBean> WeakCurrentProjectsSimpleList = new ArrayList<WeakCurrentProjectsSimpleBean>();
	List<WeakCurrentProjectsDetailBean> WeakCurrentProjectsDetailList = new ArrayList<WeakCurrentProjectsDetailBean>();
	List<ParameterBean> parameterlist =new ArrayList<ParameterBean>();
	
	WeakCurrentProjectsDetailHandler wcpd=new WeakCurrentProjectsDetailHandler();
	WeakCurrentProjectsApprovaLogHandler app_log=new WeakCurrentProjectsApprovaLogHandler();
	
	ParameterHandler parameterHandler =new ParameterHandler();
	MailHandler mailHandler = new MailHandler();
	/**
	 * 新增立项
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "/addWeakCurrentProjectsSimple",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addWeakCurrentProjectsSimple(String info) {
		String reString="";
		System.out.println("info="+info);
		if(info==null||info.equals("")){
			return ResultMessage.errString(1, "后台未接收到需要提交的数据");
		}
		WeakCurrentProjectsSimpleBean bean = GsonUtil.parseJsonWithGson(info, WeakCurrentProjectsSimpleBean.class);
		bean.setSubmitter((String) SecurityUtils.getSubject().getPrincipal());//提交人
		//RDWB-00075编号：根据最后一个项目编码生成新的项目编码
		WeakCurrentProjectsSimpleList=weakSimpleDao.queryInfoDesc();
		String RDWB="";
		if(WeakCurrentProjectsSimpleList.size()==0){
			RDWB="RDWB-00001";
		}else {
			RDWB=RDWB_Code(WeakCurrentProjectsSimpleList.get(0).getProject_code());
		}
		bean.setProject_code(RDWB);
		try {
			weakSimpleDao.insertWeakCurrentProjectsSimple(bean);
			reString=ResultMessage.MessageToJson(0, "立项成功");
			app_log.addWCPALog(RDWB, "申请立项","1010", (String) SecurityUtils.getSubject().getPrincipal(), bean.getEdit_remark());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			reString=ResultMessage.MessageToJson(1, "立项添加失败："+e.getMessage());
		}
		return reString;
	}
	
	/**
	 * 多选下拉框
	 * @return 
	 */
	@RequestMapping(value = "/getWeakCurrentProjectsSimpleFormSelectsInfo",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getWeakCurrentProjectsSimpleFormSelectsInfo() {
		System.out.println("/getPannramaMultiselect");
		WeakCurrentProjectsSimpleList=weakSimpleDao.queryAllProjectName();
		
		String reString="";
		//状态：默认不选中
		reString+="{\"name\": \"审核状态\", \"type\": \"optgroup\"},";
		parameterlist=parameterHandler.queryAllParameterByPidList("13");
		if(parameterlist.size()==0){
			reString="{\"code\":1,\"msg\":\"审核状态参数获取异常!13\",\"data\":[]}";
			return reString;
		}
		for (int i = 0; i < parameterlist.size(); i++) {
			reString+="{\"name\": \""+parameterlist.get(i).getText()+"\", \"value\": \""+parameterlist.get(i).getValue()+"\",\"selected\":\""+parameterlist.get(i).getSelect()+"\",\"disabled\":\""+parameterlist.get(i).getDisable()+"\"},";
		}
		/*reString+="{\"name\": \"待审核\", \"value\": \"AuDiTStAtUs|1010,AuDiTStAtUs|1020\",\"selected\":\"selected\"}" +
				",{\"name\": \"已驳回\", \"value\": \"AuDiTStAtUs|1012,AuDiTStAtUs|1022\",\"selected\":\"selected\"}"+
				",{\"name\": \"已立项\", \"value\": \"AuDiTStAtUs|1021\",\"selected\":\"\"}" +
				",{\"name\": \"已撤项\", \"value\": \"AuDiTStAtUs|1030\",\"selected\":\"\"},";
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
			reString+="{\"name\": \"提交人/责任人\", \"type\": \"optgroup\"},";
			reString+="{\"name\": \""+(String) SecurityUtils.getSubject().getPrincipal()+"\", \"value\": \"SuBmItTeR|"+(String) SecurityUtils.getSubject().getPrincipal()+"\",\"selected\":\"selected\",\"disabled\":\"disabled\"},";
		}
		//项目名称
		reString+="{\"name\": \"项目名称\", \"type\": \"optgroup\"},";
		for(int a= 0;a<WeakCurrentProjectsSimpleList.size();a++){
			reString+="{\"name\": \""+WeakCurrentProjectsSimpleList.get(a).getProject_code()+"-"+WeakCurrentProjectsSimpleList.get(a).getProject_name()+"\", \"value\":\"PrOjEcTcOde|"+WeakCurrentProjectsSimpleList.get(a).getProject_code()+"\",\"selected\":\"\"},";
		}
		
		reString="{\"code\":0,\"msg\":\"success\",\"data\":["+reString.substring(0, reString.length()-1)+"]}";
		System.out.println(reString);
		return reString;
	}


	/**
	 * 搜索：根据条件获取数据
	 * @param SearchCondition
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/queryWeakCurrentProjectsSimple",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryWeakCurrentProjectsSimple(String SearchCondition,int page, int limit) {
		System.out.println("SearchCondition="+SearchCondition);
		if(SearchCondition==null){
			System.out.println("SearchCondition==null");
			SearchCondition="";
		}
		GsonUtil gsonUtil=new GsonUtil();
		WeakCurrentProjectsSimpleList=new ArrayList<WeakCurrentProjectsSimpleBean>();
		String arr[]=SearchCondition.split(",");
		if(SearchCondition!=null&&SearchCondition!=""){
			System.out.println("有"+arr.length+"个条件");
		}else {
			System.out.println(arr.length+"没有条件");
			return ResultMessage.ListtoLayuiTable(0, gsonUtil.List2Json(WeakCurrentProjectsSimpleList));
		}
		for(int a=0;a<arr.length;a++){
			//System.out.println("arr[a]="+arr[a].toString());
		}
		ArrayList<String> AuditStatus = new ArrayList<String>();
		ArrayList<String> ProjectCode = new ArrayList<String>();
		for(int a=0;a<arr.length;a++){
			if(arr[a].toString().indexOf("AuDiTStAtUs")!=-1){
				AuditStatus.add(arr[a].split("\\|")[1]);//这里之所以用"\\|"，是因为"|"是特殊字符
			}else if (arr[a].toString().indexOf("PrOjEcTcOde")!=-1) {
				ProjectCode.add(arr[a].split("\\|")[1]);
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
		String submitter = null;//提交人
		if(SecurityUtils.getSubject().hasRole("wck_per_user")){//用户是否为普通的立项用户
			submitter = (String) SecurityUtils.getSubject().getPrincipal();//提交人
		}
		int Counts= weakSimpleDao.queryAllInfoCountsBySearchCondition(AuditStatus, ProjectCode,submitter,project_area_department);
		WeakCurrentProjectsSimpleList = weakSimpleDao.queryAllInfoWithFirstBySearchCondition(AuditStatus, ProjectCode, page, limit,submitter,project_area_department);
		
		String str=gsonUtil.List2Json(WeakCurrentProjectsSimpleList);
		System.out.println("str="+str);
		return ResultMessage.ListtoLayuiTable(Counts, gsonUtil.List2Json(WeakCurrentProjectsSimpleList));
	}

	/**
	 * 根据project_code获取简单的完整项目数据
	 * @return 
	 */
	@RequestMapping(value = "/queryProjectInfoByProjectCode",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryProjectInfoByProjectCode(String project_code) {
		System.out.println("/queryProjectInfoByProjectCode");
		WeakCurrentProjectsSimpleList=weakSimpleDao.queryProjectInfoByProjectCode(project_code);
		if(WeakCurrentProjectsSimpleList.size()==0){
			return ResultMessage.MessageToJson(1, "无数据");
		}else{	
			return ResultMessage.ListToJson(0, WeakCurrentProjectsSimpleList);
		}
	}
	/**
	 * 根据project_code更新所有数据
	 * @param info
	 * @param project_code
	 * @return
	 * 通过project_code获取审批状态
	 * 一、如果审批状态为“1021立项审批通过”，则判断是否修改了钱相关的数据
	 * 	1.如果没有修改钱相关的数据，则不更改审批状态
	 * 	2.如果修改了钱相关的数据，则更改审核状态为1010
	 * 二、如果审批状态不为“1011立项审批通过”，则更改审核状态为1010
	 */
	@RequestMapping(value = "/updataWeakCurrentProjectsSimpleInfoByProjectCode",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updataWeakCurrentProjectsSimpleInfoByProjectCode(String info,String project_code) {
		System.out.println("/updataWeakCurrentProjectsSimpleInfoByProjectCode");
		WeakCurrentProjectsSimpleBean bean = GsonUtil.parseJsonWithGson(info, WeakCurrentProjectsSimpleBean.class);
		bean.setSubmitter((String) SecurityUtils.getSubject().getPrincipal());
		WeakCurrentProjectsSimpleList = weakSimpleDao.queryProjectInfoByProjectCode(project_code);
		int audit_status=WeakCurrentProjectsSimpleList.get(0).getAudit_status();
		System.out.println("audit_status="+audit_status+",project_code="+project_code);

		System.out.println("audit_status==1021="+(audit_status==1021));
		/**用户申请立项
		如果是1012 1020 
		*/
		/*if(audit_status==1021){
			bean.setAudit_info("N");
			bean.setAudit_status(1010);

			bean.setAudit_status(WeakCurrentProjectsSimpleList.get(0).getAudit_status());
			app_log.addWCPALog(project_code, "申请立项","1010", (String) SecurityUtils.getSubject().getPrincipal(), "更新内容");
			if(weakSimpleDao.updataWeakCurrentProjectsSimpleInfoByProjectCode(bean, project_code)){
				return ResultMessage.MessageToJson(0, "更新成功!");
			}else {
				return ResultMessage.MessageToJson(1, "更新失败!");
			}
		}else{*/
			//如果有修改金额
			System.out.println("项目报价"+(new BigDecimal(bean.getProject_quotation().toString()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString().equals(WeakCurrentProjectsSimpleList.get(0).getProject_quotation().toString()));
			System.out.println("材料"+(new BigDecimal(bean.getMaterial().toString()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString().equals(WeakCurrentProjectsSimpleList.get(0).getMaterial().toString()));
			System.out.println("人工（外请）"+(new BigDecimal(bean.getLabor().toString()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString().equals(WeakCurrentProjectsSimpleList.get(0).getLabor().toString()));
			System.out.println("固定资产分摊"+(new BigDecimal(bean.getAllocation_of_fixed_assets().toString()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString().equals(WeakCurrentProjectsSimpleList.get(0).getAllocation_of_fixed_assets().toString()));
			System.out.println("其他项"+(new BigDecimal(bean.getOther_items().toString()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString().equals(WeakCurrentProjectsSimpleList.get(0).getOther_items().toString()));
			System.out.println("费用小计"+(new BigDecimal(bean.getSubtotal_cost().toString()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString().equals(WeakCurrentProjectsSimpleList.get(0).getSubtotal_cost().toString()));
			System.out.println("税率"+(new BigDecimal(bean.getSimple_tax().toString()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString().equals(WeakCurrentProjectsSimpleList.get(0).getSimple_tax().toString()));
			System.out.println("可开票增专金额合计"+(new BigDecimal(bean.getSpecial_vat_invoice_amount().toString()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString().equals(WeakCurrentProjectsSimpleList.get(0).getSpecial_vat_invoice_amount().toString()));
			System.out.println("非专票发票金额合计"+(new BigDecimal(bean.getOrdinary_invoice_amount().toString()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString().equals(WeakCurrentProjectsSimpleList.get(0).getOrdinary_invoice_amount().toString()));
			System.out.println("供应商抵扣税率"+(new BigDecimal(bean.getSupplier_invoice_tax().toString()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString().equals(WeakCurrentProjectsSimpleList.get(0).getSupplier_invoice_tax().toString()));
			System.out.println("供应商开票抵税金额"+(new BigDecimal(bean.getSupplier_invoice().toString()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString().equals(WeakCurrentProjectsSimpleList.get(0).getSupplier_invoice().toString()));
			
			if(!(new BigDecimal(bean.getProject_quotation().toString()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString().equals(WeakCurrentProjectsSimpleList.get(0).getProject_quotation().toString())||//项目报价
				!(new BigDecimal(bean.getMaterial().toString()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString().equals(WeakCurrentProjectsSimpleList.get(0).getMaterial().toString())||//材料
				!(new BigDecimal(bean.getLabor().toString()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString().equals(WeakCurrentProjectsSimpleList.get(0).getLabor().toString())||//人工（外请）
				!(new BigDecimal(bean.getAllocation_of_fixed_assets().toString()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString().equals(WeakCurrentProjectsSimpleList.get(0).getAllocation_of_fixed_assets().toString())||//固定资产分摊
				!(new BigDecimal(bean.getOther_items().toString()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString().equals(WeakCurrentProjectsSimpleList.get(0).getOther_items().toString())||//其他项
				!(new BigDecimal(bean.getSubtotal_cost().toString()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString().equals(WeakCurrentProjectsSimpleList.get(0).getSubtotal_cost().toString())||//费用小计
				!(new BigDecimal(bean.getSimple_tax().toString()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString().equals(WeakCurrentProjectsSimpleList.get(0).getSimple_tax().toString())||//税率
				!(new BigDecimal(bean.getSpecial_vat_invoice_amount().toString()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString().equals(WeakCurrentProjectsSimpleList.get(0).getSpecial_vat_invoice_amount().toString())||//可开票增专金额合计
				!(new BigDecimal(bean.getOrdinary_invoice_amount().toString()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString().equals(WeakCurrentProjectsSimpleList.get(0).getOrdinary_invoice_amount().toString())||//非专票发票金额合计
				!(new BigDecimal(bean.getSupplier_invoice_tax().toString()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString().equals(WeakCurrentProjectsSimpleList.get(0).getSupplier_invoice_tax().toString())||//供应商抵扣税率
				!(new BigDecimal(bean.getSupplier_invoice().toString()).setScale(2, BigDecimal.ROUND_HALF_UP)).toString().equals(WeakCurrentProjectsSimpleList.get(0).getSupplier_invoice().toString())//供应商开票抵税金额
				
			){
				bean.setAudit_info("N");
				bean.setAudit_status(1010);
				app_log.addWCPALog(project_code, "申请立项","1010", (String) SecurityUtils.getSubject().getPrincipal(), "更新内容涉及到金额");
				if(weakSimpleDao.updataWeakCurrentProjectsSimpleInfoByProjectCode(bean, project_code)){
					return ResultMessage.MessageToJson(0, "更新成功!");
				}else {
					return ResultMessage.MessageToJson(1, "更新失败!");
				}
			}else{
				audit_status = WeakCurrentProjectsSimpleList.get(0).getAudit_status();
				bean.setAudit_info(WeakCurrentProjectsSimpleList.get(0).getAudit_info());
				bean.setAudit_status(audit_status);
				
				if(audit_status==1012||audit_status==1022){//用户更新数据，[更新的内容为非金额，审批的状态为驳回的状态]，故此处约定：更改其状态为待审核
					bean.setAudit_status(1010);
					app_log.addWCPALog(project_code, "申请立项",1010+"", (String) SecurityUtils.getSubject().getPrincipal(), "更新内容重新提交审批");
				}else {
					app_log.addWCPALog(project_code, "申请立项",audit_status+"", (String) SecurityUtils.getSubject().getPrincipal(), "更新内容，未涉及到金额，状态为当前项目节点");
				}
				if(weakSimpleDao.updataWeakCurrentProjectsSimpleInfoByProjectCode(bean, project_code)){
					return ResultMessage.MessageToJson(0, "更新成功!");
				}else {
					return ResultMessage.MessageToJson(1, "更新失败!");
				}
			}
		//}
	}
	/**
	 * 根据project_code更新审核信息
	 * @return 
	 */
	@RequestMapping(value = "/updataWeakCurrentProjectsSimpleAuditStatusByProjectCode",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updataWeakCurrentProjectsSimpleAuditStatusByProjectCode(String audit_status,String audit_info,String project_code,BigDecimal cost_share) {
		System.out.println("/updataWeakCurrentProjectsSimpleAuditStatusByProjectCode");
		System.out.println("audit_status="+audit_status);
		System.out.println("audit_info="+audit_info);
		System.out.println("project_code="+project_code);
		System.out.println("cost_share="+cost_share);

		Map<String, String> returnStringMap = new HashMap<String, String>();
		int mysql_approval_status= weakSimpleDao.WCPSAuditStatus(project_code);
		returnStringMap=WCPApprovalStatusUtil.approvalStatusCheck(mysql_approval_status,Integer.parseInt(audit_status) );
		if(returnStringMap.get("BOOLEAN").equals("false")){
			return ResultMessage.MessageToJson(1, "审批失败!当前项目的审批节点为："+returnStringMap.get("INFO"));
		}
		//如果是审核通过
		try {
			if(audit_status.equals("1021")){
				System.out.println("立项审核通过");
				WeakCurrentProjectsSimpleList=weakSimpleDao.queryProjectInfoByProjectCode(project_code);
				BigDecimal project_quotation = WeakCurrentProjectsSimpleList.get(0).getProject_quotation();
				BigDecimal subtotal_cost = WeakCurrentProjectsSimpleList.get(0).getSubtotal_cost();
				BigDecimal simple_tax = WeakCurrentProjectsSimpleList.get(0).getSimple_tax();
				weakDetailDao.insertWeakCurrentProjectsDetail(audit_status, audit_info,project_code, cost_share, project_quotation, subtotal_cost, simple_tax);
				app_log.addWCPALog(project_code, "申请立项",audit_status, (String) SecurityUtils.getSubject().getPrincipal(), audit_info);
				return ResultMessage.MessageToJson(0, "审核成功!");
				
			}else {
				System.out.println("非立项审核通过");
				weakSimpleDao.updataWeakCurrentProjectsSimpleAuditStatusByProjectCode(audit_status,audit_info, project_code);
				app_log.addWCPALog(project_code, "申请立项",audit_status, (String) SecurityUtils.getSubject().getPrincipal(), audit_info);
				return ResultMessage.MessageToJson(0, "审核成功!");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResultMessage.MessageToJson(1, "审核失败："+e.getMessage());
		}
	}
	

	/**
	 * 项目ID自动生成
	 * @param RDWB
	 * @return
	 */
	private String RDWB_Code(String RDWB) {
		String returnString="";
		String num=RDWB.split("-")[1];
		int code = Integer.valueOf(num)+1;
		for (int j = code-1; j < code ; j++,code++) {
			returnString=String.valueOf(code);
			for (int i = 0; i <num.length()- String.valueOf(code).length(); i++) {
				returnString="0"+returnString;
			}
			if(weakSimpleDao.queryInfoDescToCode("RDWB-"+returnString).size()==0){
				break;
			}
		}
		return "RDWB-"+returnString;
	}
	

	

	/**
	 * 搜索：根据条件获取数据
	 * @param project_code
	 * @return
	 */
	@RequestMapping(value = "/queryWeakCurrentProjectsSimpleToShow",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryWeakCurrentProjectsSimpleToShow(String project_code) {
		System.out.println("project_code="+project_code);
		List<WeakCurrentProjectsSimpleBean> historyList = new ArrayList<WeakCurrentProjectsSimpleBean>();
		historyList=weakSimpleDao.queryHistoryProjectInfoByProjectCode(project_code);
		WeakCurrentProjectsSimpleList = weakSimpleDao.queryProjectInfoByProjectCode(project_code);
		return ResultMessage.ListsToJson(0, WeakCurrentProjectsSimpleList, historyList);
	}
	

	/**
	 * 根据project_code撤项
	 * @param project_code
	 * @return
	 * 撤项需要将相关的数据都删除：
	 * 1.总表数据
	 * 2.成本报销数据
	 * 3.奖金分配数据
	 */
	@RequestMapping(value = "/revokeWeakCurrentProjectsSimpleByProjectCode",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String revokeWeakCurrentProjectsSimpleByProjectCode(String project_code){
		System.out.println("/revokeWeakCurrentProjectsSimpleByProjectCode");
		try {
			weakSimpleDao.withdrawal_item(project_code,1030, "N");
			return ResultMessage.MessageToJson(0, "撤项成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResultMessage.MessageToJson(1, "撤项异常:"+e.getMessage());
		}
		
		
	}
}
