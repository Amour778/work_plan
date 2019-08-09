package com.workplan.handler;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.workplan.bean.ProgressBean;
import com.workplan.bean.UserInfoBean;
import com.workplan.bean.WorkItemFirstBean;
import com.workplan.bean.WorkItemSecondSimpleBean;
import com.workplan.dao.ProgressDao;
import com.workplan.dao.UserInfoDao;
import com.workplan.dao.WorkItemFirstDao;
import com.workplan.dao.WorkItemSecondDao;
import com.workplan.tools.GetDateTimeNow;
import com.workplan.tools.ResultMessage;
import com.workplan.tools.SendShortMessage;
import com.workplan.tools.sqlHelper;
import com.workplan.upload.download.export.ExportExcelUtil;
import com.workplan.upload.download.export.ExportExcelWrapperMergeCellUtil;

@Controller
public class WorkItemSecondHandler {

	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	/**
	 * 更新二级事项信息
	 * @param item_name
	 * @param item_id
	 * @return
	 */
	@RequestMapping(value ="/updataItemSecond",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updataItemFirst(String item_id,String superior_item_id,String item_name,String organization,String work_content,
			String completion_cycle,
			String person_liable,String partner,String start_time,String completion_time) {
		System.out.println("updataItemSecond");
		System.out.println("item_id="+item_id);
		System.out.println("superior_item_id="+superior_item_id);
		System.out.println("item_name="+item_name);
		System.out.println("organization="+organization);
		System.out.println("work_content="+work_content);
		System.out.println("completion_cycle="+completion_cycle);
		System.out.println("person_liable="+person_liable);
		System.out.println("partner="+partner);
		System.out.println("start_time="+start_time);
		System.out.println("completion_time="+completion_time);
		WorkItemSecondDao dao=(WorkItemSecondDao) context.getBean("WorkItemSecondDao");
		ResultMessage message=new ResultMessage();
		if(dao.updataItemInfoByItemId(item_id, superior_item_id, item_name, organization, work_content, completion_cycle, person_liable, partner, start_time, completion_time)){
			int a=0;
			String reString="";
			SendShortMessage sendShortMessage=new SendShortMessage();
			UserInfoDao user_dao = (UserInfoDao) context.getBean("UserInfoDao");
			List<UserInfoBean> user_list = new ArrayList<UserInfoBean>();
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
			for (int i = 0; i < teList.size(); i++) {
				try {
					user_list=user_dao.querySimpleUserSimpleInfoByUserId(teList.get(i));
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
				return ResultMessage.MessageToJson(0, "更新成功");
			}else {
				return ResultMessage.MessageToJson(1, "更新数据成功，短信发送失败<br/>"+reString);
			}
			
		}else {
			return ResultMessage.MessageToJson(1, "更新失败");
		}
	}
	
	/**
	 * 删除二级事项
	 * @param item_id
	 * @return
	 */
	@RequestMapping(value ="/deleteItemSecond",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String deleteItemFirst(String item_id) {
		System.out.println("deleteItemSecond");
		WorkItemSecondDao dao=(WorkItemSecondDao) context.getBean("WorkItemSecondDao");
		ResultMessage message=new ResultMessage();
		if(dao.deleteByItemId(item_id)){
			return ResultMessage.MessageToJson(0, "删除成功");
		}else {
			return ResultMessage.MessageToJson(1, "删除失败");
		}
	}
	

	/**
	 * 通过item_id批量删除二级事项
	 * @param item_id
	 * @return
	 */
	@RequestMapping(value ="/batchDeleteSecondByItemId",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String batchDeleteSecondByItemId(String item_id) {
		System.out.println("item_id="+item_id);
		List<WorkItemSecondSimpleBean> list= new ArrayList<WorkItemSecondSimpleBean>();
		String[] arrStrings=item_id.split(";");
		for(int a= 0;a<arrStrings.length;a++){
			WorkItemSecondSimpleBean bean=new WorkItemSecondSimpleBean();
			System.out.println("arrStrings["+a+"]="+arrStrings[a]);
			bean.setItemId(arrStrings[a]);
			list.add(bean);
		}
		WorkItemSecondDao second_dao=(WorkItemSecondDao) context.getBean("WorkItemSecondDao");
		ResultMessage message=new ResultMessage();
		for(int a= 0;a<list.size();a++){
			System.out.println("list.get("+a+").getItemId()="+list.get(a).getItemId());
		}
		System.out.println();
		if (second_dao.batchDeleteByItemId(list) == 0) {
			return ResultMessage.MessageToJson(0, "删除成功");
		} else {
			return ResultMessage.MessageToJson(1, "删除失败");
		}
	}
	
	/**
	 * 通过事项ID搜索步骤信息
	 * @param item_id
	 * @return
	 */
	@RequestMapping(value ="/queryStepByItemId",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryStepByItemId(String item_id) {
		System.out.println("queryStepByItemId");
		WorkItemSecondDao dao=(WorkItemSecondDao) context.getBean("WorkItemSecondDao");
		ResultMessage message=new ResultMessage();
		List<WorkItemSecondSimpleBean> list= new ArrayList<WorkItemSecondSimpleBean>();
		list=dao.queryStepByItemId(item_id);
		if(list.size()!=0){
			//if((list.get(0).getStep().equals("N"))){
				return message.MessageToJson(0, list.get(0).getStep());
			/*}else {
				return message.MessageToJson(2, "无数据");
			}*/
		}else {
			return message.MessageToJson(1, "获取数据失败");
		}
	}
	
	/**
	 * 通过事项ID添加步骤
	 * @param item_id
	 * @param step
	 * @return
	 */
	@RequestMapping(value ="/addStepByItemId",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addStepByItemId(String item_id,String step) {
		System.out.println("addStepByItemId");
		WorkItemSecondDao dao=(WorkItemSecondDao) context.getBean("WorkItemSecondDao");
		ResultMessage message=new ResultMessage();
		if(dao.updataItemStep(step,item_id)){
				return message.MessageToJson(0, "提交成功");
		}else {
			return message.MessageToJson(1, "提交失败");
		}
	}
	
	
	/**
	 * 导出工作全景表格
	 * @return
	 */
	@RequestMapping(value ="/ExportExcelPanorama",produces="text/html;charset=UTF-8")
	@ResponseBody
	public void ExportExcelPanorama(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ExportExcelPanorama");
		WorkItemSecondDao dao=(WorkItemSecondDao) context.getBean("WorkItemSecondDao");
		List<WorkItemSecondSimpleBean> list= new ArrayList<WorkItemSecondSimpleBean>();
		String[] headers={"事项名称（一级）","事项名称（二级）","组织","工作内容","步骤","完成周期","负责人","配合人","开始时间","完成时间","状态 ","进度"};
		GetDateTimeNow getTimeNow=new GetDateTimeNow();
		String fileName="盛海工作全景图"+getTimeNow.getDateTimeRandomToID();
		
		WorkItemFirstDao WorkItemFirstDao=(WorkItemFirstDao) context.getBean("WorkItemFirstDao");//一级
		WorkItemSecondDao WorkItemSecondDao=(WorkItemSecondDao) context.getBean("WorkItemSecondDao");//二级
		ProgressDao ProgressDao=(ProgressDao) context.getBean("ProgressDao");//步骤
		List<WorkItemFirstBean> FirstdBeanList= new ArrayList<WorkItemFirstBean>();//一级
		List<WorkItemSecondSimpleBean> SecondBeanList= new ArrayList<WorkItemSecondSimpleBean>();//二级
		List<ProgressBean> Progress= new ArrayList<ProgressBean>();//步骤
		
		//获取一级事项总条数firstCount
		FirstdBeanList=WorkItemFirstDao.queryAllToTableCounts(null);
		int firstCount=FirstdBeanList.size();
		
		System.out.println("firstCount="+firstCount);
		//拥有二级事项的个数数组countNum
		//int[] countNum=new int[firstCount];
		//String[] firsts=new String[firstCount];

		//for (int i = 0; i < FirstdBeanList.size(); i++) {
		//	firsts[i]=FirstdBeanList.get(i).getItemId();
		//}
		//填充二级事项个数数组
		for (int i = 0; i < FirstdBeanList.size(); i++) {
			System.out.println("FirstdBeanList.get(i).getItemId()="+FirstdBeanList.get(i).getItemId());
			SecondBeanList=WorkItemSecondDao.queryInfoStateWithSuperiorItemId(FirstdBeanList.get(i).getItemId());
			System.out.println("SecondBeanList.size()="+SecondBeanList.size());
			//countNum[i]=SecondBeanList.size();
			//System.out.println("countNum["+i+"]"+countNum[i]);
		}
		//获取一级事项和二级事项整合的内容
		SecondBeanList= new ArrayList<WorkItemSecondSimpleBean>();
		SecondBeanList=WorkItemSecondDao.queryFirstAndSeconedAllInfoNotProgress();
		for (int i = 0; i < SecondBeanList.size(); i++) {
			//根据二级事项获取对应步骤
			Progress=new ArrayList<ProgressBean>();
			Progress=ProgressDao.queryAllDESC(SecondBeanList.get(i).getItemId());
			String progress="";
			for (int j = 0; j < Progress.size(); j++) {
				progress+="【日期："+Progress.get(j).getItem_date()+"编写人："+Progress.get(j).getEdit_user()+"】\r\n"+Progress.get(j).getItem_progress()+"\r\n";
			}
			System.out.println(SecondBeanList.get(i).getItemId()+"<->"+progress);
			SecondBeanList.get(i).setProgress(progress);
		}
		System.out.println("导出的前期准备工作完成");
		ExportExcelWrapperMergeCellUtil<WorkItemSecondSimpleBean> util = new ExportExcelWrapperMergeCellUtil<WorkItemSecondSimpleBean>();
		System.out.println("【导出开始】");
		util.exportExcel(fileName, "盛海工作全景图", headers, SecondBeanList, response,ExportExcelUtil.EXCEl_FILE_2007);
		System.out.println("【导出完成】");
	}

	/**
	 * 根据用户工号匹配用户事项，如果事项在24小时之后过期则提醒
	 */
	@RequestMapping(value ="/getTimeOutItemByUserName",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getTimeOutItemByUserName() {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		System.out.println("username="+username);
		List<WorkItemSecondSimpleBean> listAdd= new ArrayList<WorkItemSecondSimpleBean>();
		List<WorkItemSecondSimpleBean> listDel= new ArrayList<WorkItemSecondSimpleBean>();
		WorkItemSecondDao second_dao=(WorkItemSecondDao) context.getBean("WorkItemSecondDao");
		ResultMessage message=new ResultMessage();
		sqlHelper sqlHelper=new sqlHelper();
		GetDateTimeNow getDateTimeNow=new GetDateTimeNow();
		try {
			//获取配置的即将过期时间
			int addDate = Integer.parseInt(sqlHelper.getCompletion_time());
			System.out.println("addDate="+addDate);
			System.out.println("username="+username);
			listAdd=second_dao.queryTimeOutItem(addDate,username);
			//获取配置的过期时间
			//int deleteDate = Integer.parseInt(sqlHelper.getCompletion_time_timeout());
			listDel=second_dao.queryTimeOutItem(username);
			
			String info="";
			int a = 0,b=0;
			String soon_expire="";
			String has_expired="";
			if(listAdd.size()>0){
				for (int i = 0; i < listAdd.size(); i++) {
					a++;
					soon_expire+=listAdd.get(i).getItemName()+"<br/>";
				}
				info+="{\"soon_expire\":\""+soon_expire+"\",";
			}else {
				info+="{\"soon_expire\":0,";
			}
			if(listDel.size()>0){
				for (int i = 0; i < listDel.size(); i++) {
					b++;
					has_expired+=listDel.get(i).getItemName()+"<br/>";
				}
				info+="\"has_expired\":\""+has_expired+"\"}";
			}else {
				info+="\"has_expired\":0}";
			}
			System.out.println(WorkItemSecondHandler.class+info);
			if((a==0)&&(b==0)){
					return message.MessageToJson(0, "无过期消息");
			}else {
				return "{\"code\":1,\"info\": " + info + "}";
			}
					
		} catch (Exception e) {
			return message.MessageToJson(1, "获取过期事项数据失败");
		}
		
	}
	
}
