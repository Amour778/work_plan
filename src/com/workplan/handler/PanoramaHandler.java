package com.workplan.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.workplan.bean.UserInfoBean;
import com.workplan.bean.WorkItemFirstBean;
import com.workplan.bean.WorkItemSecondSimpleBean;
import com.workplan.dao.UserInfoDao;
import com.workplan.dao.WorkItemFirstDao;
import com.workplan.dao.WorkItemSecondDao;
import com.workplan.tools.GetDateTimeNow;
import com.workplan.tools.GsonUtil;
import com.workplan.tools.ResultMessage;
import com.workplan.tools.SendShortMessage;

@Controller
public class PanoramaHandler {
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

	SendShortMessage sendShortMessage=new SendShortMessage();
	
	@RequestMapping("/getPanorama")
	public String getPanorama(){
		return "";
	}

	/**
	 * 用户选择添加一级事项并添加相关数据
	 * @param title
	 * @return
	 */
	@RequestMapping(value="/addLevel",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addLevel(String title){
		System.out.println("addLevel");
		//System.out.println("长度"+title.length());
		WorkItemFirstDao dao = (WorkItemFirstDao) context.getBean("WorkItemFirstDao");
		GetDateTimeNow getTim=new GetDateTimeNow();
		String item_id=GetDateTimeNow.getDateTimeRandomToID();
		if(dao.add(item_id, title)){
			return item_id;
		}else {
			System.out.println("生成新一级事项名称ID失败");
			return "";
		}
	}
	
	/**
	 * 用户选择使用已有一级事项并添加相关数据
	 * @param superior_item_id
	 * @param item_name
	 * @param organization
	 * @param work_content
	 * @param step
	 * @param completion_cycle
	 * @param person_liable 用户工号
	 * @param partner 用户工号, 用户工号
	 * @param start_time
	 * @param completion_time
	 * @return
	 */
	@RequestMapping(value="/addPanorama",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String choseLevel(String superior_item_id,String item_name,String organization,String work_content,String step,String completion_cycle,
			String person_liable,String partner,String start_time,String completion_time){

		ResultMessage message=new ResultMessage();
		/*System.out.println("type="+type);
		if(type.equals("addLevel")){
			superior_item_id=addLevel(title);
			System.out.println("superior_item_id="+superior_item_id);
			if(superior_item_id==""){
					return message.MessageToJson(1, "添加一级事项失败");
			}
		}*/
		System.out.println("superior_item_id="+superior_item_id);
		List<String> teList=new ArrayList<String>();
		if(person_liable.equals(partner)){
			teList.add(person_liable);
		}else {
			teList.add(person_liable);
			String[] nextPersonString=partner.split(",");
			for (int i = 0; i < nextPersonString.length; i++) {
				if(nextPersonString[i]!=null&&nextPersonString[i]!="")
					teList.add(nextPersonString[i]);
			}
		}
		
		WorkItemSecondDao dao = (WorkItemSecondDao) context.getBean("WorkItemSecondDao");
		GetDateTimeNow getTim=new GetDateTimeNow();
		String item_id=GetDateTimeNow.getDateTimeRandomToID();
		int state=-1;//默认状态为未开始：-1
		String creation_date=GetDateTimeNow.getDate();
		if(dao.add(superior_item_id,item_id, item_name, organization, work_content, step, completion_cycle, person_liable, partner, start_time, completion_time, state, creation_date)){
				String reString="";
				int a=0;
				UserInfoDao user_dao = (UserInfoDao) context.getBean("UserInfoDao");
				List<UserInfoBean> user_list = new ArrayList<UserInfoBean>();
				for (int i = 0; i < teList.size(); i++) {
					try {
						user_list=user_dao.querySimpleUserSimpleInfoByUserId(teList.get(i));
						System.out.println("item_name="+item_name);
						String sendString=sendShortMessage.sendShortMessage(user_list.get(0).getUser_tel(), item_name,"NewItemTemplateCode");
						if(!sendString.equals("0")){
							a++;
							reString+="姓名："+user_list.get(0).getUser_name()+"，失败码："+sendString+"<br/>";
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
						reString+="姓名："+user_list.get(0).getUser_name()+"，系统失败信息："+e.getMessage()+"<br/>";
					}
				}
				if(a==0){
					return ResultMessage.MessageToJson(0, "添加成功");
				}else {
					return ResultMessage.MessageToJson(1, "事项添加成功，以下人员的短信发送失败：<br/>"+reString);
				}
			
		}else {
			return ResultMessage.MessageToJson(1, "添加失败");
		}
	}

	/**
	 * 多选下拉框
	 * @return 状态(系统写死)、一级事项名称、//工作内容
	 */
	@RequestMapping(value = "/getPannramaMultiselect",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getAllUserInfo() {
		System.out.println("/getPannramaMultiselect");
		WorkItemFirstDao first_dao = (WorkItemFirstDao) context.getBean("WorkItemFirstDao");
		WorkItemSecondDao second_dao = (WorkItemSecondDao) context.getBean("WorkItemSecondDao");

		List<WorkItemFirstBean> first_list = new ArrayList<WorkItemFirstBean>();
		List<WorkItemSecondSimpleBean> second_list = new ArrayList<WorkItemSecondSimpleBean>();
		
		first_list=first_dao.queryAll();
		second_list=second_dao.queryWorkItemSecondMapperWithItemIdAndItemNameInfo();
		
		String reString="";
		//状态：默认选中未开始和进行中的状态
		reString+="{\"name\": \"状态\", \"type\": \"optgroup\"},";
		reString+="{\"name\": \"未开始\", \"value\": \"WoRk_StAtE|-1\",\"selected\":\"selected\"},{\"name\": \"进行中\", \"value\": \"WoRk_StAtE|0\",\"selected\":\"selected\"},{\"name\": \"完成\", \"value\": \"WoRk_StAtE|1\",\"selected\":\"\"},";
		//一级事项名称
		reString+="{\"name\": \"一级事项名称\", \"type\": \"optgroup\"},";
		for(int a= 0;a<first_list.size();a++){
			reString+="{\"name\": \""+first_list.get(a).getItemName()+"\", \"value\":\"FiRsT_iD|"+first_list.get(a).getItemId()+"\",\"selected\":\"\"},";
		}
		//二级事项名称
		reString+="{\"name\": \"二级事项名称\", \"type\": \"optgroup\"},";
		for(int a= 0;a<second_list.size();a++){
			reString+="{\"name\": \""+second_list.get(a).getItemName()+"\", \"value\":\"SeCoNd_iD|"+second_list.get(a).getItemId()+"\",\"selected\":\"\"},";
		}
		//工作内容
		//for(int a= 0;a<second_list.size();a++){
		//	reString+="{\"name\": \""+second_list.get(a).getItemName()+"-"+second_list.get(a).getWorkContent()+"\", \"value\":\""+second_list.get(a).getItemId()+"\",\"selected\":\"\"},";
		//}

		reString="{\"code\":0,\"msg\":\"success\",\"data\":["+reString.substring(0, reString.length()-1)+"]}";
		System.out.println(reString);
		return reString;
	}
	
	/**
	 * 搜索数据
	 * @param SearchCondition
	 * @param page
	 * @param limit
	 * @return 符合用户搜索条件的事项结果
	 */
	@RequestMapping(value = "/queryInfoBySearchCondition",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryInfoBySearchCondition(String SearchCondition,int page,int limit) {
		System.out.println("SearchCondition="+SearchCondition);
		System.out.println("page="+page);
		System.out.println("limit="+limit);
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
		ArrayList<String> Work_State = new ArrayList<String>();
		ArrayList<String> First_id = new ArrayList<String>();
		ArrayList<String> Second_id = new ArrayList<String>();
		
		for(int a=0;a<arr.length;a++){
			if(arr[a].toString().indexOf("WoRk_StAtE")!=-1){
				Work_State.add(arr[a].split("\\|")[1]);//这里之所以用"\\|"，是因为"|"是特殊字符
			}else if (arr[a].toString().indexOf("FiRsT_iD")!=-1) {
				First_id.add(arr[a].split("\\|")[1]);
				
			}else if (arr[a].toString().indexOf("SeCoNd_iD")!=-1) {
				Second_id.add(arr[a].split("\\|")[1]);
				
			}
		}
		WorkItemSecondDao second_dao = (WorkItemSecondDao) context.getBean("WorkItemSecondDao");
		List<WorkItemSecondSimpleBean> second_list = new ArrayList<WorkItemSecondSimpleBean>();
		int Counts=0;
		second_list=second_dao.queryAllInfoWithFirstBySearchCondition(Work_State,First_id,Second_id,page,limit);
		Counts=second_dao.queryAllInfoCountsBySearchCondition(Work_State,First_id,Second_id);
		/**
		 * 因为配合人无法使用 LEFT JOIN 匹配出来姓名，故只能循环获取
		 */
		UserInfoDao user_dao = (UserInfoDao) context.getBean("UserInfoDao");
		List<UserInfoBean> user_list = new ArrayList<UserInfoBean>();
		for (int i = 0; i < second_list.size(); i++) {
			//取出配合人工号，并分割
			String[] split_partner_arr=second_list.get(i).getPartner().split(",");
			//根据工号，循环获取姓名
			String nameString="";
			for (int j = 0; j < split_partner_arr.length; j++) {
				user_list=user_dao.querySimpleUserSimpleInfoByUserId(split_partner_arr[j]);
				try {
					nameString+=user_list.get(0).getUser_name()+",";
				} catch (Exception e) {
					System.out.println(PanoramaHandler.class+"获取用户姓名时出错:"+e.getMessage());
				}
				
			}
			//重新赋值
			try {
				if(nameString.substring(nameString.length()-1, nameString.length()).equals(","))
					nameString=nameString.substring(0, nameString.length()-1);
			} catch (Exception e) {
				nameString="";
			}
			second_list.get(i).setPartner(nameString);
		}
		GsonUtil gsonUtil=new GsonUtil();
		String str=gsonUtil.List2Json(second_list);
		System.out.println("str="+str);
		ResultMessage layuiTable=new ResultMessage();
		return ResultMessage.ListtoLayuiTable(Counts, gsonUtil.List2Json(second_list));
	}
	/**
	 * 获取 一级事项名称/二级事项名称和ID/工作内容/步骤
	 * @return 事项状态不为已完成的所有二级事项相关内容
	 */ 
	@RequestMapping(value = "/queryWorkContentWithFirstIdAndSecondId",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryWorkContentWithFirstIdAndSecondId() {
		System.out.println("/queryWorkContentWithFirstIdAndSecondId");
		//WorkItemFirstDao first_dao = (WorkItemFirstDao) context.getBean("WorkItemFirstDao");
		WorkItemSecondDao second_dao = (WorkItemSecondDao) context.getBean("WorkItemSecondDao");

		//List<WorkItemFirstBean> first_list = new ArrayList<WorkItemFirstBean>();
		List<WorkItemSecondSimpleBean> second_list = new ArrayList<WorkItemSecondSimpleBean>();
		
		//first_list=first_dao.queryAll();
		second_list=second_dao.queryWorkContentWithFirstIdAndSecondId();
		
		String reString="";
		GsonUtil message=new GsonUtil();
		reString=message.List2Json(second_list);
		System.out.println(reString);
		return reString;
	}
	

	/**
	 * 获取 首页展示内容
	 * @return 各个状态的数量
	 */ 
	@RequestMapping(value = "/queryWorkContentToMain",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryWorkContentToMain() {
		System.out.println("/queryWorkContentToMain");
		WorkItemSecondDao second_dao = (WorkItemSecondDao) context.getBean("WorkItemSecondDao");
		List<WorkItemSecondSimpleBean> second_list = new ArrayList<WorkItemSecondSimpleBean>();
		second_list=second_dao.queryWorkContentToMain();
		int SUM=0;
		String reString="";
		for(int a = 0 ; a < second_list.size();a++){
			SUM=SUM+Integer.parseInt(second_list.get(a).getItemId());
			reString+="{\"state\":"+second_list.get(a).getState()+",\"num\":"+second_list.get(a).getItemId()+"},";
		}
		reString+="{\"state\":99,\"num\":"+SUM+"}";
		reString="{\"info\": ["+reString+"]}";
		System.out.println(reString);
		return reString;
	}
	
	/**
	 * 通过二级itemId，获取责任人和者配合人
	 * @return "姓名1,手机号码1","姓名2,手机号码2"
	 */ 
	@RequestMapping(value = "/queryPersonLiableOrPartnerByItemId",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryPersonLiableOrPartnerByItemId(String item_id) {
		System.out.println("/queryPersonLiableOrPartnerByItemId");
		WorkItemSecondDao second_dao = (WorkItemSecondDao) context.getBean("WorkItemSecondDao");
		List<WorkItemSecondSimpleBean> second_list = new ArrayList<WorkItemSecondSimpleBean>();
		String reString="";
		second_list = second_dao.queryAllByItemId(item_id);
		if(second_list.size()>0){
			String partnerss=second_list.get(0).getPartner();
			String[] par_arr=partnerss.split(",");
			String partnersString="";
			for (int i = 0; i < par_arr.length; i++) {
				partnersString+="{\"value\": \""+par_arr[i]+"\"},";
			}
			if(partnersString.substring(partnersString.length()-1, partnersString.length()).equals(","))
				partnersString=partnersString.substring(0, partnersString.length()-1);
			reString="{\"code\":0,\"person_liable\":\""+second_list.get(0).getPersonLiable()+"\",\"partner\":["+partnersString+"]}";
		}else {
			reString="{\"code\":0,\"info\":\"获取事项数据失败，请检查此事项是否还存在\"}";
			
		}
		/*
		try {
			String PersonLiable=second_list.get(0).getPersonLiable();
			String Partner=second_list.get(0).getPartner();
			String[] PartnerArray=Partner.split(",");
			String JsonPersonLiable="";
			String JsonPartner="";
			user_list=user_dao.queryTelInfoWithUserName(PersonLiable);
			if(user_list.size()>0)
				JsonPersonLiable="\""+PersonLiable+","+user_list.get(0).getUser_tel()+"\"";
			for (int i = 0; i < PartnerArray.length; i++) {
				user_list=user_dao.queryTelInfoWithUserName(PartnerArray[i]);
				if(user_list.size()>0)
					JsonPartner+="["+PartnerArray[i]+","+user_list.get(0).getUser_tel()+"],";
			}
			if(JsonPartner.substring(JsonPartner.length()-1, JsonPartner.length()).equals(","))
				JsonPartner=JsonPartner.substring(0, JsonPartner.length()-1);
			System.out.println("JsonPersonLiable="+JsonPersonLiable);
			System.out.println("JsonPartner="+JsonPartner);
			reString="{\"code\":0,\"person_liable\":"+JsonPersonLiable+",\"partner\":\""+JsonPartner+"\"}";
		} catch (Exception e) {
			reString="{\"code\":1,\"info\":"+e.getMessage()+"}";
			System.out.println(e.getMessage());
		}*/
			
		System.out.println(reString);
		return reString;
	}
	
}
