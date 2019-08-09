package com.workplan.handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.shiro.SecurityUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.workplan.bean.WeakCurrentProjectsRequestForPaymentBean;
import com.workplan.dao.WeakCurrentProjectsRequestForPaymentDao;
import com.workplan.tools.GetDateTimeNow;
import com.workplan.tools.GsonUtil;
import com.workplan.tools.ResultMessage;
@Controller
public class WeakCurrentProjectsRequestForPaymentHandler {
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	WeakCurrentProjectsRequestForPaymentDao rfpDao = (WeakCurrentProjectsRequestForPaymentDao) context.getBean("WeakCurrentProjectsRequestForPaymentDao");
	WeakCurrentProjectsRequestForPaymentBean rfpBean =  new WeakCurrentProjectsRequestForPaymentBean();
	List<WeakCurrentProjectsRequestForPaymentBean> rfpList = new ArrayList<WeakCurrentProjectsRequestForPaymentBean>();

	WeakCurrentProjectsApprovaLogHandler app_log=new WeakCurrentProjectsApprovaLogHandler();

	/**
	 * 添加付款申请单
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "/addOrUpdateWCP_RFP_info",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addOrUpdateWCP_RFP_info(String info) {
		System.out.println("info="+info);
		if(info==null||info.equals("")){
			return ResultMessage.errString(1, "后台未接收到需要提交的数据");
		}
		try {
			WeakCurrentProjectsRequestForPaymentBean bean = GsonUtil.parseJsonWithGson(info, WeakCurrentProjectsRequestForPaymentBean.class);
			String rid = bean.getRid();
			if(bean.getRid()==null||bean.getRid().equals("null")){
				System.out.println("生成RID");
				rid = "R"+GetDateTimeNow.getDateTimeRandomToID();
				bean.setRid(rid);
			}
			System.out.println("RID="+bean.getRid());
			bean.setAudit_sta(2110);
			bean.setAudit_info("N");
			System.out.println("拆解数据完成");
			rfpDao.addOrUpdateWCP_RFP_info(bean);
			//提交审批历史及邮件提醒
			app_log.addWCPALog(rid, "付款申请单","2110", (String) SecurityUtils.getSubject().getPrincipal(), bean.getAudit_info());
			return ResultMessage.MessageToJson(0, "提交数据成功");
		} catch (Exception e) {
			e.printStackTrace();
			/*String errorCode = e.getCause().getMessage();
			if (errorCode.indexOf("Duplicate entry") >= 0) {
            	System.out.println("数据已存在");
            }*/
			return ResultMessage.MessageToJson(1, "提交数据失败："+e.getMessage());
		}
	}

	/**
	 * 通过项目ID获取所有的付款申请单
	 * @param project_code
	 * @return
	 */
	@RequestMapping(value = "/queryAllWCP_RFP_ByProjectCode",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryAllWCP_RFP_ByProjectCode(String project_code) {
		rfpList = rfpDao.queryAllWCP_RFP_ByProjectCode(project_code);
		return ResultMessage.ListtoLayuiTable(rfpList.size(), rfpList);
	}

	/**
	 * 通过项目ID获取项目的申请部门/费用所属部门project_area_department、已付款金额(不含本次)amountPaid、合同或应付款总额totalAmountPayable
	 * @param project_code
	 * @return
	 */
	@RequestMapping(value = "/queryWCP_info_ToAdd_RFP_ByProjectCode",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryWCP_info_ToAdd_RFP_ByProjectCode(String project_code) {
		try {
			rfpList = rfpDao.queryWCP_info_ToAdd_RFP_ByProjectCode(project_code);
			return ResultMessage.ListToJson(0, rfpList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.MessageToJson(1, "通过项目唯一ID获取项目的信息失败："+e.getMessage());
		}
	}

	/**
	 * 通过RID获取所有的付款申请单
	 * @param project_code
	 * @return
	 */
	@RequestMapping(value = "/queryOneWCP_RFP_ByRid",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryOneWCP_RFP_ByRid(String rid) {
		try {
			rfpBean = rfpDao.queryOneWCP_RFP_ByRid(rid);
			return ResultMessage.MessageToJsonWithBean(0,rfpBean);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.MessageToJson(1, "获取数据异常:"+e.getMessage());
		}
	}
	
	/**
	 * 通过RID更新项目的审批状态
	 * @param rid
	 * @param audit_sta
	 * @param audit_info
	 * 如果更新的审核状态为2141(BOSS通过)，则将此次付款数据添加到总表，具体分支在DAO中
	 * @return
	 */
	@RequestMapping(value = "/updateWCP_RFP_AuditStaAndAuditInfoByRid",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updateWCP_RFP_AuditStaAndAuditInfoByRid(String rid,int audit_sta,String audit_info,String project_code,BigDecimal amountOfThisPayment) {
		try {
			Map<String, String> returnStringMap = new HashMap<String, String>();
			int mysql_approval_status = rfpDao.WCPRFPAuditSta(rid);
			returnStringMap=WCPApprovalStatusUtil.approvalStatusCheck(mysql_approval_status,audit_sta);
			if(returnStringMap.get("BOOLEAN").equals("false")){
				return ResultMessage.MessageToJson(1, "审批失败!当前的审批节点为："+returnStringMap.get("INFO"));
			}
			if(rfpDao.updateWCP_RFP_AuditStaAndAuditInfoByRid(rid, audit_sta, audit_info,project_code,amountOfThisPayment)){
				app_log.addWCPALog(rid, "付款申请单",audit_sta+"", (String) SecurityUtils.getSubject().getPrincipal(),audit_info);
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
