package com.workplan.handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.workplan.bean.UserInfoBean;
import com.workplan.bean.WeakCurrentProjectsActualExpenditureBean;
import com.workplan.bean.WeakCurrentProjectsActualGroupBean;
import com.workplan.bean.WeakCurrentProjectsApprovaLogBean;
import com.workplan.bean.WeakCurrentProjectsSimpleBean;
import com.workplan.dao.UserInfoDao;
import com.workplan.dao.WeakCurrentProjectsActualExpenditureDao;
import com.workplan.dao.WeakCurrentProjectsActualGroupDao;
import com.workplan.dao.WeakCurrentProjectsApprovaLogDao;
import com.workplan.dao.WeakCurrentProjectsSimpleDao;
import com.workplan.tools.ArithUtil;
import com.workplan.tools.ArrayListMapUtil;
import com.workplan.tools.GetDateTimeNow;
import com.workplan.tools.ResultMessage;
@Controller
public class WeakCurrentProjectsActualGroupHandler {
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	WeakCurrentProjectsActualGroupDao weakGroupDao = (WeakCurrentProjectsActualGroupDao) context.getBean("WeakCurrentProjectsActualGroupDao");
	WeakCurrentProjectsActualExpenditureDao weakActaulDao = (WeakCurrentProjectsActualExpenditureDao) context.getBean("WeakCurrentProjectsActualExpenditureDao");
	WeakCurrentProjectsSimpleDao weakSimpleDao = (WeakCurrentProjectsSimpleDao) context.getBean("WeakCurrentProjectsSimpleDao");
	UserInfoDao userInfoDao = (UserInfoDao) context.getBean("UserInfoDao");
	WeakCurrentProjectsApprovaLogDao approvaLogDao = (WeakCurrentProjectsApprovaLogDao) context.getBean("WeakCurrentProjectsApprovaLogDao");
	
	List<WeakCurrentProjectsActualExpenditureBean> weakActaulList = new ArrayList<WeakCurrentProjectsActualExpenditureBean>();
	List<WeakCurrentProjectsActualGroupBean> weakGroupList = new ArrayList<WeakCurrentProjectsActualGroupBean>();
	List<WeakCurrentProjectsSimpleBean> weakSimpleList = new ArrayList<WeakCurrentProjectsSimpleBean>();
	List<WeakCurrentProjectsApprovaLogBean> approvaLogList = new ArrayList<WeakCurrentProjectsApprovaLogBean>();
	
	WeakCurrentProjectsActualExpenditureBean weakActaulBean = new WeakCurrentProjectsActualExpenditureBean();
	WeakCurrentProjectsActualGroupBean weakGroupBean = new WeakCurrentProjectsActualGroupBean();
	
	WeakCurrentProjectsApprovaLogHandler app_log=new WeakCurrentProjectsApprovaLogHandler();
	ArithUtil arithUtil = new ArithUtil();
	
	/**
	 * 新增成本报销申请
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "/addWeakCurrentProjectsActualExpenditureGroup",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addWeakCurrentProjectsActualExpenditureGroup(String info) {
		System.out.println("info="+info);
		JSONObject obj = null;
		try {
			obj = JSONObject.fromObject(info);
		} catch (Exception e) {
			return ResultMessage.MessageToJson(1,  "添加失败:"+e.getMessage());
		}
		/* audit_status审核状态是由数据库的默认值定义的 */
		String project_code = obj.getString("project_code");
		String cost_department = obj.getString("cost_department");
		String name_of_applicant = obj.getString("name_of_applicant");
		String reimbursement_number = obj.getString("reimbursement_number");
		String applicant_department = obj.getString("applicant_department");
		String the_last_time = obj.getString("the_last_time");
		String date_of_application = GetDateTimeNow.getDate();
		try {
			weakActaulDao = (WeakCurrentProjectsActualExpenditureDao) context.getBean("WeakCurrentProjectsActualExpenditureDao");
			JSONArray jsonArray = obj.getJSONArray("data");
			weakActaulList= new ArrayList<WeakCurrentProjectsActualExpenditureBean>();
			String group_id=GetDateTimeNow.getDateTimeRandomToID();
			double v1=0;;
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject elementobj = JSONObject.fromObject(jsonArray.get(i));
				weakActaulBean = new WeakCurrentProjectsActualExpenditureBean();
				String actual_id=GetDateTimeNow.getDateTimeRandomToID();
				System.out.println("actual_id="+actual_id);
				weakActaulBean.setActual_id(actual_id);
				weakActaulBean.setProject_code(project_code);
				weakActaulBean.setGroup_id(group_id);
				//weakActaulBean.setCost_department(cost_department);
				weakActaulBean.setCost_matters(elementobj.getString("cost_matters"));
				weakActaulBean.setAmount_in_original_currency(new BigDecimal(elementobj.getString("amount_in_original_currency")));
				weakActaulBean.setIdle_stock(new BigDecimal(elementobj.getString("idle_stock")));
				weakActaulBean.setTax_rate(new BigDecimal(elementobj.getString("tax_rate")));
				weakActaulBean.setAmount_of_tax(new BigDecimal(elementobj.getString("amount_of_tax")));
				weakActaulBean.setPrincipal(new BigDecimal(elementobj.getString("principal")));
				weakActaulBean.setBill_type(elementobj.getString("bill_type"));
				weakActaulBean.setInvoice_number(elementobj.getString("invoice_number"));
				weakActaulBean.setDate_of_occurrence(elementobj.getString("date_of_occurrence"));
				weakActaulBean.setPurpose_of_occurrence(elementobj.getString("purpose_of_occurrence"));
				weakActaulBean.setReimbursement_number(reimbursement_number);
				weakActaulBean.setName_of_applicant(name_of_applicant);
				weakActaulBean.setApplicant_department(applicant_department);
				weakActaulBean.setDate_of_application(date_of_application);
				//weakActaulBean.setThe_last_time(the_last_time);
				weakActaulList.add(weakActaulBean);
				
				app_log.addWCPALog(actual_id, "新增成本报销","2010", (String) SecurityUtils.getSubject().getPrincipal(), "N",project_code);
				
				v1=ArithUtil.add(v1, new Double(elementobj.getString("amount_in_original_currency")));
				
			}
			String a=weakActaulDao.batchInsertActualExpenditure(weakActaulList);
			System.out.println("a="+a);
			if(a.equals("0")){
				if(weakGroupDao.insertActualFroup(group_id,project_code, cost_department, v1+"", (String) SecurityUtils.getSubject().getPrincipal(), the_last_time)){
					return ResultMessage.MessageToJson(0, "添加成功");
				}else {
					return ResultMessage.MessageToJson(1, "添加明细成功但是添加组信息失败，请尝试重新提交");
				}
			}else {
				return ResultMessage.MessageToJson(1,  "添加失败:"+a);
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return ResultMessage.MessageToJson(1,  "添加失败:"+e.getMessage());
		}
	}
	/**
	 * 通过ID获取是否存在审核通过并为最后一次报销的数据
	 * @param project_code
	 * @return
	 */
	@RequestMapping(value = "/queryIsOrNotTheLastTimeByProjectCode",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryIsOrNotTheLastTimeByProjectCode(String project_code) {
		System.out.println("project_code="+project_code);
		int isHave=weakGroupDao.queryIsOrNotTheLastTimeByProjectCode(project_code);
		if(isHave==0){
			return ResultMessage.MessageToJson(0, "没有最后一次报销的数据");
		}else{
			return ResultMessage.MessageToJson(1, "存在'最后一次报销'数据，不可再提交报销申请");
		}	
	}
	

	/**
	 * 多选下拉框
	 * @return 
	 */
	@RequestMapping(value = "/getWCPAGFormSelectsInfo",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getWCPAGFormSelectsInfo() {
		weakSimpleList=weakSimpleDao.queryAllProjectName();
		String reString="";
		//状态：默认不选中
		reString+="{\"name\": \"审核状态\", \"type\": \"optgroup\"},";
		reString+="{\"name\": \"待审核\", \"value\": \"AuDiTsTaTuS|2010,AuDiTsTaTuS|2020,AuDiTsTaTuS|2030,AuDiTsTaTuS|2040\",\"selected\":\"selected\"}," +
				"{\"name\": \"已通过\", \"value\": \"AuDiTsTaTuS|2041\",\"selected\":\"\"}," +
				"{\"name\": \"已驳回\", \"value\": \"AuDiTsTaTuS|2012,AuDiTsTaTuS|2022,AuDiTsTaTuS|2032,AuDiTsTaTuS|2042\",\"selected\":\"selected\"},";
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
	/**
	 * 搜索：根据条件获取组数据
	 * @param SearchCondition
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/queryWCPAGBySearchCondition",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryWCPAGBySearchCondition(String SearchCondition) {
		System.out.println("SearchCondition="+SearchCondition);
		if(SearchCondition==null){
			System.out.println("SearchCondition==null");
			SearchCondition="";
		}
		String arr[]=SearchCondition.split(",");
		if(SearchCondition!=null&&SearchCondition!=""){
			System.out.println("有"+arr.length+"个条件");
		}else {
			System.out.println(arr.length+"没有条件");
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
		weakActaulList = weakActaulDao.queryAllInfoWithFirstBySearchCondition(AuditStatus, ProjectCode,submitter,project_area_department);
		if(weakActaulList.size()==0){
			return ResultMessage.ListDataToDTree("");
		}
		String returnString="";
		int counts=weakActaulList.size();
		String[] groupArray=new String[counts];
		String[] projectArray=new String[counts];
		for (int i = 0; i < weakActaulList.size(); i++) {
			//具体的申请事项
			weakGroupList = new ArrayList<WeakCurrentProjectsActualGroupBean>();
			weakGroupList=weakGroupDao.querySimpleInfoWithProjectCode(weakActaulList.get(i).getProject_code());
			/*String Audit_status=weakActaulList.get(i).getAudit_status()+"";
			if(Audit_status.equals("2010")||Audit_status.equals("2020")||Audit_status.equals("2030")||Audit_status.equals("2040")){
				Audit_status="待审核";
			}else if(Audit_status.equals("2041")){
				Audit_status="已通过";
			}else if(Audit_status.equals("2012")||Audit_status.equals("2022")||Audit_status.equals("2032")||Audit_status.equals("2042")){
				Audit_status="已驳回";
			}
			returnString+="{\"id\":\""+weakActaulList.get(i).getId()+"\",\"title\": \""+Audit_status+"\",\"checkArr\": \"0\",\"parentId\": \""+weakActaulList.get(i).getActual_id()+"\"},";*/
			groupArray[i]=weakActaulList.get(i).getGroup_id();
			projectArray[i]=weakActaulList.get(i).getProject_code();
		}
		/*if(returnString.substring(returnString.length()-1, returnString.length()).equals(",")){
			returnString=returnString.substring(0,returnString.length()-1);
		}*/
		System.out.println(returnString);
		/*for (int i = 0; i < groupArray.length; i++) {
			System.out.println("groupArray["+i+"]="+groupArray[i]);
			System.out.println("projectArray["+i+"]="+projectArray[i]);
		}*/
		String[] _groupArray=ArrayListMapUtil.deduplicationArray(groupArray);
		String[] _projectArray=ArrayListMapUtil.deduplicationArray(projectArray);
		for (int i = 0; i < _groupArray.length; i++) {
			System.out.println("_groupArray["+i+"]="+_groupArray[i]);
			//通过组ID获取组的信息
			weakGroupList=weakGroupDao.queryJustGroupInfoByGroupId(_groupArray[i]);
			if(weakGroupList.size()==0){
				continue;
			}
			String id=weakGroupList.get(0).getGroup_id();
			String title=id;
			String parentId=weakGroupList.get(0).getProject_code();
			if(weakGroupList.get(0).getApproval_finsh().equals("Y")){
				returnString+="{\"id\":\""+id+"\",\"title\": \""+title+"<span class='layui-badge-dot layui-bg-green'></span>\",\"checkArr\": \"0\",\"parentId\": \""+parentId+"\"},";
			}else {
				returnString+="{\"id\":\""+id+"\",\"title\": \""+title+"<span class='layui-badge-dot layui-bg-orange'></span>\",\"checkArr\": \"0\",\"parentId\": \""+parentId+"\"},";
			}
		}

		/*if(returnString.substring(returnString.length()-1, returnString.length()).equals(",")){
			returnString=returnString.substring(0,returnString.length()-1);
		}*/
		System.out.println(returnString);
		for (int i = 0; i < _projectArray.length; i++) {
			System.out.println("_projectArray["+i+"]="+_projectArray[i]);
			//通过项目ID获取项目的信息
			weakSimpleList=weakSimpleDao.queryInfoDescToCode(_projectArray[i]);
			if(weakSimpleList.size()==0){
				continue;
			}
			String id=weakSimpleList.get(0).getProject_code();
			String title=id+"-"+weakSimpleList.get(0).getProject_name();
			String parentId="0";
			returnString+="{\"id\":\""+id+"\",\"title\": \""+title+"\",\"checkArr\": \"0\",\"parentId\": \""+parentId+"\"},";
		}

		if(returnString.substring(returnString.length()-1, returnString.length()).equals(",")){
			returnString=returnString.substring(0,returnString.length()-1);
		}
		System.out.println(returnString);

		return ResultMessage.ListDataToDTree(returnString);
	}
	
	/**
	 * 通过组ID获取提交的申请报销数据
	 * @param actual_id
	 * @return
	 */
	@RequestMapping(value = "/queryWCPAGByGroupId",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryWCPAGByGroupId(String group_id) {
		System.out.println("group_id="+group_id);
		if(group_id.equals("")){
			return ResultMessage.ListtoLayuiTableHaveError(1, "请点击左侧组名");
		}
		weakActaulList=weakActaulDao.queryAllInfoWithFirstByGroupId(group_id);
		return ResultMessage.ListtoLayuiTable(weakActaulList.size(), weakActaulList);
	}


	/**
	 * 通过ID获取对应报销组的是否审核完毕状态
	 * @param id
	 * @return approval_finsh
	 */
	@RequestMapping(value = "/checkGroupIsApprovalFinsh",produces="text/html;charset=UTF-8")
	@ResponseBody
	public void checkGroupIsApprovalFinsh(String id) {
		System.out.println("id="+id);
		weakActaulList =weakActaulDao.queryAuditStatusByActualId(id);
		System.out.println("ID"+id+"对应的组未审核完毕的数据有"+weakActaulList.size()+"条");
		if(weakActaulList.size()==0) {
			//更新状态
			weakActaulList =weakActaulDao.queryActualIdById(id);
			if(weakGroupDao.updataWCPAGApprovalFinshByActualId("Y", weakActaulList.get(0).getGroup_id())){
				System.out.println("更新组的审批完成状态成功");
			}else {
				System.out.println("更新组的审批完成状态失败");
			}
		}else {
			System.out.println("组还存在未通过的申请");
		}
		
	}

	/**
	 * 通过组ID，获取组内报销信息、报销人员信息、项目信息，用于自动填充报销单
	 * @param group_id
	 * @return
	 */
	@RequestMapping(value = "/queryWeakActualReimbursementByGroupId",produces="text/html;charset=UTF-8")
	@ResponseBody
	@RequiresRoles("wck_per_user")
	public String queryWeakActualReimbursementByProjectCodeAndUserId(String group_id) {
		//通过组编码获取需要的信息
		weakGroupList = weakGroupDao.queryJustGroupInfoByGroupId(group_id);
		if(weakGroupList.size()==0){
			return ResultMessage.MessageToJson(1, "报销组ID无报销数据");
		}
		String project_code = weakGroupList.get(0).getProject_code();
		//通过上面获取到的项目编码，获取项目信息
		weakSimpleList = weakSimpleDao.queryProjectInfoByProjectCode(project_code);
		if(weakSimpleList.size()==0){
			return ResultMessage.MessageToJson(1, "报销组ID对应的项目无责任人信息");
		}
		//通过项目信息获取责任人信息
		String user_id = weakSimpleList.get(0).getProject_leader();
		String item_classification = weakSimpleList.get(0).getItem_classification();
		//通过项目编码获取报销信息
		weakActaulList=weakActaulDao.queryWeakActualReimbursementByGroupId(group_id);
		if(weakActaulList.size()==0){
			return ResultMessage.MessageToJson(1, "项目无报销数据");
		}
		//通过用户工获取用户信息
		UserInfoHandler userInfoHandler = new UserInfoHandler();
		String userinfoString=userInfoHandler.getUserInfoToReimbursementByUserId(user_id);
		//通过项目编码获取审批信息
		//String appLog
		//费用所属部门负责人d	2020	财务负责人f2040	人资负责人h		盛海负责人b
		//成本报销审核记录approvaLogList，审核记录已经进行时间排序，时间越早越靠后
		approvaLogList = approvaLogDao.getLogByWCPActualGroupId(group_id);
		//故可以直接获取第一个报销状态符合的数据
		String dString="", aString="", hString="", bString="";
		for (int i = 0; i < approvaLogList.size(); i++) {
			String nowApproval_staString = approvaLogList.get(i).getApproval_sta();
			if(dString.equals("")&&nowApproval_staString.equals("2020")){
				dString=approvaLogList.get(i).getApproval_user()+"/同意/"+approvaLogList.get(i).getApproval_time();
			}
			if(aString.equals("")&&nowApproval_staString.equals("2030")){
				aString=approvaLogList.get(i).getApproval_user()+"/同意/"+approvaLogList.get(i).getApproval_time();
			}
			if(hString.equals("")&&nowApproval_staString.equals("2040")){
				hString=approvaLogList.get(i).getApproval_user()+"/同意/"+approvaLogList.get(i).getApproval_time();
			}
			if(bString.equals("")&&nowApproval_staString.equals("2041")){
				bString=approvaLogList.get(i).getApproval_user()+"/同意/"+approvaLogList.get(i).getApproval_time();
			}
			if(!dString.equals("")&&!aString.equals("")&&!hString.equals("")&&!bString.equals("")){
				break;
			}
		}
		String otherString="[{"+
		"\"item_classification\":\""+item_classification+"\"," +
		"\"dString\":\""+dString+"\"," +
		"\"aString\":\""+aString+"\"," +
		"\"hString\":\""+hString+"\"," +
		"\"bString\":\""+bString+"\"" +
				"}]";;
		return ResultMessage.ListsAndInfoToJson(0, weakActaulList, userinfoString,otherString);
	}
}
