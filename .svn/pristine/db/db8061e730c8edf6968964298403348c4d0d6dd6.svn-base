package com.workplan.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.workplan.bean.UserInfoBean;
import com.workplan.bean.WeakCurrentProjectsApprovaLogBean;
import com.workplan.bean.WeakCurrentProjectsSimpleBean;
import com.workplan.dao.MailDao;
import com.workplan.dao.UserInfoDao;
import com.workplan.dao.WeakCurrentProjectsApprovaLogDao;
import com.workplan.dao.WeakCurrentProjectsSimpleDao;
import com.workplan.tools.GetDateTimeNow;
import com.workplan.tools.ResultMessage;


/**
 * 审批历史记录
 * @author 01059101
 *
 */
@Controller
public class WeakCurrentProjectsApprovaLogHandler {

	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	WeakCurrentProjectsApprovaLogBean wcpaLogBean=new WeakCurrentProjectsApprovaLogBean();
	List<WeakCurrentProjectsApprovaLogBean>  wcpaLogList = new ArrayList<WeakCurrentProjectsApprovaLogBean>();

	MailDao mailDao = (MailDao) context.getBean("MailDao");
	UserInfoDao userDao = (UserInfoDao) context.getBean("UserInfoDao");
	WeakCurrentProjectsSimpleDao weakSimpleDao = (WeakCurrentProjectsSimpleDao) context.getBean("WeakCurrentProjectsSimpleDao");
	WeakCurrentProjectsApprovaLogDao wcpaLogDao = (WeakCurrentProjectsApprovaLogDao) context.getBean("WeakCurrentProjectsApprovaLogDao");

	List<UserInfoBean> userList = new ArrayList<UserInfoBean>();
	List<WeakCurrentProjectsSimpleBean> weakSimpleList = new ArrayList<WeakCurrentProjectsSimpleBean>();
	
	/**
	 * 添加审核记录，并发送邮件
	 * @param item_id 项目ID，一般为项目ID或者唯一ID
	 * @param item 审核记录的名称，例如：申请立项
	 * @param approval_sta 审核状态
	 * @param approval_user 添加这条记录的人
	 * @param remark 备注
	 */
	public void addWCPALog(String item_id,String item,String approval_sta,String approval_user,String remark){
		if(wcpaLogDao.addWCPALog(item_id, item, GetDateTimeNow.getDateTime(), approval_sta, approval_user, remark)){
			System.out.println("审核记录添加成功");
			Map<String, String> MESSAGE=new HashMap<String, String>();
			MailHandler mailHandler = new MailHandler();
			MESSAGE=mailHandler.sendWCPEMail(item_id, approval_sta);
			if(MESSAGE.get("code").equals("1")){
				mailDao.insertMailErrLog(item_id, approval_sta, MESSAGE.get("info"));
			}
			System.out.println("发送邮件的回执为："+MESSAGE);
		}else {
			System.err.println("审核记录添加失败");
		}	
	}	
	/**
	 * 添加审核记录，主要用于添加实际报销的时候，item_id为组ID ，但是项目发送邮件需要用到projectID，所以添加一个备用条件来存放projectID
	 * @param item_id 项目ID，一般为项目ID或者唯一ID
	 * @param item 审核记录的名称，例如：申请立项
	 * @param approval_sta 审核状态
	 * @param approval_user 添加这条记录的人
	 * @param remark 备注
	 * @param standby_condition 备用条件
	 */
	public void addWCPALog(String item_id,String item,String approval_sta,String approval_user,String remark,String standby_condition){
		System.out.println("备用条件standby_condition="+standby_condition);
		if(wcpaLogDao.addWCPALog(item_id, item, GetDateTimeNow.getDateTime(), approval_sta, approval_user, remark)){
			System.out.println("审核记录添加成功");
			Map<String, String> MESSAGE=new HashMap<String, String>();
			MailHandler mailHandler = new MailHandler();
			MESSAGE=mailHandler.sendWCPEMail(standby_condition, approval_sta);
			if(MESSAGE.get("code").equals("1")){
				mailDao.insertMailErrLog(standby_condition, approval_sta, MESSAGE.get("info"));
			}
			System.out.println("发送邮件的回执为："+MESSAGE);
		}else {
			System.err.println("审核记录添加失败");
		}	
	}
	
	@RequestMapping(value = "/queryWCPALogByItemId",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryWCPALogByItemId(String item_id) {
		wcpaLogList=wcpaLogDao.queryWCPALog(item_id);
		return ResultMessage.ListtoLayuiTable(wcpaLogList.size(), wcpaLogList);
	}
	
}
