package com.workplan.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.workplan.bean.ProgressBean;
import com.workplan.dao.ProgressDao;
import com.workplan.dao.WorkItemSecondDao;
import com.workplan.tools.GetDateTimeNow;
import com.workplan.tools.GsonUtil;
import com.workplan.tools.ResultMessage;

@Controller
public class ProgressHandler {
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	

	/**
	 * 符合用户搜索条件的事项结果
	 * @param SearchCondition 搜索条件
	 * @return Json数据
	 */
	@RequestMapping(value = "/ProgressInfoBySearchCondition",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String ProgressInfoBySearchCondition(String SearchCondition) {
		System.out.println("SearchCondition="+SearchCondition);
		
		ProgressDao progress_dao = (ProgressDao) context.getBean("ProgressDao");
		GsonUtil gsonUtil=new GsonUtil();
		return gsonUtil.List2Json(progress_dao.queryAllDESC(SearchCondition));
	}

	/**
	 * 添加进程并更新进程状态 : 未开始 -> 进行中
	 * @param info
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addProgress",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addProgress(String info,HttpServletRequest request) {
		String reString="";
		System.out.println("info="+info);
		ProgressBean bean=GsonUtil.parseJsonWithGson(info, ProgressBean.class);
		
		bean.setEdit_user((String) SecurityUtils.getSubject().getPrincipal());
		System.out.println(bean.getItem_id()+","+bean.getItem_progress()+","+bean.getItem_date()+","+bean.getEdit_user());
		ProgressDao progress_dao = (ProgressDao) context.getBean("ProgressDao");
		ResultMessage message=new ResultMessage();
		if(progress_dao.addByBean(bean)){
			int st=changeProgress(bean.getItem_id(), 0);
			if(st==-1||st==0){
				reString=ResultMessage.MessageToJson(0, "添加成功");
			}else {
				reString=ResultMessage.MessageToJson(1, "添加进程成功，<b>修改事项状态失败</b>");
			}
		}else {
			reString=ResultMessage.MessageToJson(1, "添加失败");
		}
		return reString;
	}

	/**
	 * 结束进程，更新进程状态 : 进行中 -> 完成
	 * @param item_id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/endProgress",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String endProgress(String item_id,HttpServletRequest request) {
		ProgressBean bean=new ProgressBean();
		GetDateTimeNow gettime=new GetDateTimeNow();
		HttpSession session = request.getSession();
		ResultMessage message=new ResultMessage();
		
		bean.setItem_id(item_id);
		bean.setItem_date(GetDateTimeNow.getDate());
		bean.setItem_progress("事项完成");
		bean.setEdit_user(session.getAttribute("SESSION_USERNAME").toString());
		System.out.println(bean.getItem_id()+","+bean.getItem_progress()+","+bean.getItem_date()+","+bean.getEdit_user());
		
		ProgressDao progress_dao = (ProgressDao) context.getBean("ProgressDao");
		if(progress_dao.addByBean(bean)){
			int st=changeProgress(bean.getItem_id(), 1);
			if(st==-1||st==0){
				return ResultMessage.MessageToJson(0, "结束事项成功");
			}else {
				return ResultMessage.MessageToJson(1, "结束事项失败");
			}
		}else{
			return ResultMessage.MessageToJson(1, "结束事项失败");
		}
	}
	
	/**
	 * 通过事项ID和状态，来修改事项的状态
	 * @param item_id
	 * @param item_state
	 * @return -1:当前状态与需要修改的状态一致  0 ： 修改状态成功  1：修改状态失败
	 */
	private int changeProgress(String item_id,int item_state) {
		System.out.println("updataItemSecond");
		WorkItemSecondDao dao=(WorkItemSecondDao) context.getBean("WorkItemSecondDao");
		//获取事项状态
		int state=-1;
		try {
			state=dao.queryInfoState(item_id).get(0).getState();
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
		if(item_state==state){
			return -1;
		}else if(dao.updataItemState(item_state,item_id)){
			return  0;
		}else {
			return  1;
		}
	
	}
	
	

}
