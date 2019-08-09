package com.workplan.handler;

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

import com.workplan.bean.WeakCurrentProjectsApplicationOfAnInvoiceBean;
import com.workplan.dao.WeakCurrentProjectsApplicationOfAnInvoiceDao;
import com.workplan.tools.GetDateTimeNow;
import com.workplan.tools.GsonUtil;
import com.workplan.tools.ResultMessage;
@Controller
public class WeakCurrentProjectsApplicationOfAnInvoiceHandler {
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	WeakCurrentProjectsApplicationOfAnInvoiceDao aoaiDao = (WeakCurrentProjectsApplicationOfAnInvoiceDao) context.getBean("WeakCurrentProjectsApplicationOfAnInvoiceDao");
	WeakCurrentProjectsApplicationOfAnInvoiceBean aoaiBean =  new WeakCurrentProjectsApplicationOfAnInvoiceBean();
	List<WeakCurrentProjectsApplicationOfAnInvoiceBean> aoaiList = new ArrayList<WeakCurrentProjectsApplicationOfAnInvoiceBean>();

	WeakCurrentProjectsApprovaLogHandler app_log=new WeakCurrentProjectsApprovaLogHandler();

	/**
	 * 添加申请开票
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "/addWCP_AOAI_info",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addWCP_AOAI_info(String info,String project_code) {
		System.out.println("info="+info);
		if(info==null||info.equals("")){
			return ResultMessage.errString(1, "后台未接收到需要提交的数据");
		}
		try {
			WeakCurrentProjectsApplicationOfAnInvoiceBean bean = GsonUtil.parseJsonWithGson(info, WeakCurrentProjectsApplicationOfAnInvoiceBean.class);
			String aid = bean.getAid();
			if(aid==null||aid.equals("null")||aid.equals("")){
				System.out.println("生成AID");
				aid = "A"+GetDateTimeNow.getDateTimeRandomToID();
				bean.setAid(aid);
			}
			System.out.println("aid="+aid);
			bean.setAudit_status(2210);
			bean.setAudit_info("N");
			bean.setProject_code(project_code);
			aoaiDao.addWCP_AOAI_info(bean);
			app_log.addWCPALog(project_code, "开票申请",2210+"", (String) SecurityUtils.getSubject().getPrincipal(),"申请开票");
			return ResultMessage.MessageToJson(0, "提交数据成功");
		} catch (Exception e) {
			e.printStackTrace();
			String errorCode = e.getCause().getMessage();
            if (errorCode.indexOf("Duplicate entry") >= 0) {
            	return ResultMessage.MessageToJson(1, "提交数据失败：项目已经提交过开票申请");
            }
			return ResultMessage.MessageToJson(1, "提交数据失败："+e.getMessage());
		}
	}

	/**
	 * 修改申请开票的内容
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "/updateWCP_AOAI_info",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updateWCP_AOAI_info(String info) {
		System.out.println("info="+info);
		if(info==null||info.equals("")){
			return ResultMessage.errString(1, "后台未接收到需要提交的数据");
		}
		try {
			WeakCurrentProjectsApplicationOfAnInvoiceBean bean = GsonUtil.parseJsonWithGson(info, WeakCurrentProjectsApplicationOfAnInvoiceBean.class);
			bean.setAudit_status(2210);
			bean.setAudit_info("开票申请");
			aoaiDao.updateWCP_AOAI_info(bean);
			app_log.addWCPALog(bean.getProject_code(), "开票申请",2210+"", (String) SecurityUtils.getSubject().getPrincipal(),"编辑后重新提交");
			return ResultMessage.MessageToJson(0, "提交数据成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.MessageToJson(1, "提交数据失败："+e.getMessage());
		}
	}


	/**
	 * 审核时用的申请开票的内容
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "/pendingWCP_AOAI_info",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String pendingWCP_AOAI_info(String project_code) {
		try {
			aoaiBean = aoaiDao.queryOneWCP_AOAI_AllInfo_ByProjectCode(project_code);
			if(aoaiBean==null){
				return ResultMessage.MessageToJson(1, "无开票请求");
			}else {
				if(aoaiBean.getAudit_status()==2210){
					return ResultMessage.MessageToJsonWithBean(0,aoaiBean);
				}else if(aoaiBean.getAudit_status()==2211){
					return ResultMessage.MessageToJson(1, "开票申请已经通过");
				}else if(aoaiBean.getAudit_status()==2212){
					return ResultMessage.MessageToJson(1, "开票申请已经驳回");
				}else {
					return ResultMessage.MessageToJsonWithBean(1,"未知的审批状态");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.MessageToJson(1, "获取数据异常:"+e.getMessage());
		}
	}

	/**
	 * 通过项目编码获取提交的开票申请信息
	 */
	@RequestMapping(value = "/queryOneWCP_AOAI_AllInfo_ByProjectCode",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryOneWCP_AOAI_AllInfo_ByProjectCode(String project_code) {
		try {
			aoaiBean = aoaiDao.queryOneWCP_AOAI_AllInfo_ByProjectCode(project_code);
			if(aoaiBean==null){
				return ResultMessage.MessageToJson(0, "N");
			}else {
				if(aoaiBean.getAudit_status()==2210){
					return ResultMessage.MessageToJson(1, "已经提交过开票申请，请等待审批结束");
				}else if(aoaiBean.getAudit_status()==2211){
					return ResultMessage.MessageToJson(1, "开票申请已经通过，不可再提");
				}else if(aoaiBean.getAudit_status()==2212){
					return ResultMessage.MessageToJsonWithBean(0,aoaiBean);
				}else {
					return ResultMessage.MessageToJsonWithBean(1,"未知的审批状态");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.MessageToJson(1, "获取数据异常:"+e.getMessage());
		}
	}
	
	/**
	 * 更新申请开票审批状态
	 * @param aid
	 * @param audit_status
	 * @param audit_info
	 * @return
	 */
	@RequestMapping(value = "/updateWCP_AOAI_AuditStatusAndAuditInfoByAid",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updateWCP_AOAI_AuditStatusAndAuditInfoByAid(String aid,String project_code,int audit_status,String audit_info) {
		try {
			Map<String, String> returnStringMap = new HashMap<String, String>();
			int mysql_approval_status = aoaiDao.queryWCP_AOAI_AuditStatus_ByAid(aid);
			System.out.println("mysql_approval_status="+mysql_approval_status+",audit_status="+audit_status);
			returnStringMap=WCPApprovalStatusUtil.approvalStatusCheck(mysql_approval_status,audit_status);
			if(returnStringMap.get("BOOLEAN").equals("false")){
				return ResultMessage.MessageToJson(1, "审批失败!当前的审批节点为："+returnStringMap.get("INFO"));
			}
			if(aoaiDao.updateWCP_AOAI_AuditStatusAndAuditInfoByAid(aid, audit_status, audit_info)){
				app_log.addWCPALog(project_code, "开票申请",audit_status+"", (String) SecurityUtils.getSubject().getPrincipal(),audit_info);
				return ResultMessage.MessageToJson(0, "审批成功");
			}else {
				return ResultMessage.MessageToJson(1, "审批失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.MessageToJson(1, "审批失败："+e.getMessage());
		}
	}

}
