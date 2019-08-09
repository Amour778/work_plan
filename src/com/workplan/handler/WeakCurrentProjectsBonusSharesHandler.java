package com.workplan.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.workplan.bean.WeakCurrentProjectsBonusSharesBean;
import com.workplan.dao.WeakCurrentProjectsBonusSharesDao;
import com.workplan.dao.WeakCurrentProjectsDetailDao;
import com.workplan.dao.WeakCurrentProjectsSimpleDao;
import com.workplan.tools.ArithUtil;
import com.workplan.tools.ResultMessage;
@Controller
public class WeakCurrentProjectsBonusSharesHandler {
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	ResultMessage resultMessage=new ResultMessage();
	WeakCurrentProjectsSimpleDao weakSimpleDao = (WeakCurrentProjectsSimpleDao) context.getBean("WeakCurrentProjectsSimpleDao");
	WeakCurrentProjectsDetailDao weakDetailDao = (WeakCurrentProjectsDetailDao) context.getBean("WeakCurrentProjectsDetailDao");
	WeakCurrentProjectsBonusSharesDao weakBonusSharesDao = (WeakCurrentProjectsBonusSharesDao) context.getBean("WeakCurrentProjectsBonusSharesDao");

	List<WeakCurrentProjectsBonusSharesBean> weakBonusSharesList = new ArrayList<WeakCurrentProjectsBonusSharesBean>();
	WeakCurrentProjectsBonusSharesBean weakBonusSharesBean = new WeakCurrentProjectsBonusSharesBean();
	WeakCurrentProjectsApprovaLogHandler app_log=new WeakCurrentProjectsApprovaLogHandler();
	
	/**
	 * 新增个人奖金分配
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "/addWeakCurrentProjectsBonusSharesBean",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addWeakCurrentProjectsBonusSharesBean(String info) {
		System.out.println("info="+info);
		JSONObject obj = JSONObject.fromObject(info);
		String project_code =obj.getString("project_code");
		String project_leader=obj.getString("project_leader");
		//BigDecimal sum_money =BigDecimal.valueOf(Double.valueOf(obj.getString("project_leader")));
		//BigDecimal personal =  BigDecimal.valueOf(Double.valueOf(obj.getString("personal")));
		//BigDecimal D_value;
		Double sum_money =Double.valueOf(obj.getString("sum_money"));//实际分配出去的奖金
		Double personal = Double.valueOf(obj.getString("personal"));//总奖金
		System.out.println("实际分配"+sum_money);
		System.out.println("总奖金"+personal);
		System.out.println("总奖金小于实际分配"+(sum_money>personal));
		System.out.println("总奖金大于实际分配"+(sum_money<personal));
		Double D_money;

		try {
			System.out.println("project_code="+project_code);
			weakBonusSharesDao = (WeakCurrentProjectsBonusSharesDao) context.getBean("WeakCurrentProjectsBonusSharesDao");
			weakBonusSharesList=weakBonusSharesDao.queryAllInfoWithProjectCode(project_code);
			weakBonusSharesDao.batchDeleteByID(weakBonusSharesList);
			JSONArray jsonArray = obj.getJSONArray("data");
			weakBonusSharesList= new ArrayList<WeakCurrentProjectsBonusSharesBean>();
			int isAddOrSub=0;
			String errString="";
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject elementobj = JSONObject.fromObject(jsonArray.get(i));
				weakBonusSharesBean = new WeakCurrentProjectsBonusSharesBean();
				weakBonusSharesBean.setUser_id(elementobj.getString("user_id"));
				weakBonusSharesBean.setUser_name(elementobj.getString("user_name"));
				weakBonusSharesBean.setProportion(elementobj.getString("proportion"));
				weakBonusSharesBean.setMoney(elementobj.getString("money"));
				weakBonusSharesBean.setProject_code(project_code);
				weakBonusSharesBean.setReturned_money(elementobj.getString("returned_money"));
				if(project_leader.equals(elementobj.getString("user_id"))&&isAddOrSub==0){
					D_money=Double.valueOf(elementobj.getString("money").toString());
					System.out.println("负责人project_leader="+project_leader);
					if(sum_money<personal){//实际分配出去的奖金 ＜  总奖金
						System.out.println("总奖金("+personal+")比实际分配金额("+sum_money+")多，已经自动加到负责人奖金中");
						errString="总奖金("+personal+")比实际分配金额("+sum_money+")多，已经自动加到负责人奖金中";
						D_money =ArithUtil.add(D_money,ArithUtil.sub(personal,sum_money));
					}else if(sum_money>personal){//实际分配出去的奖金 ＞  总奖金
						System.out.println("总奖金("+personal+")比实际分配金额("+sum_money+")少，已经自动从负责人奖金中扣除");
						errString="总奖金("+personal+")比实际分配金额("+sum_money+")少，已经自动从负责人奖金中扣除";
						D_money =ArithUtil.sub(D_money,ArithUtil.sub(sum_money,personal));
					}
					weakBonusSharesBean.setMoney(D_money.toString());
					isAddOrSub++;
					System.out.println("D_money="+D_money);
				}
				weakBonusSharesList.add(weakBonusSharesBean);
			}
			weakBonusSharesDao.batchInsertBonusShares(weakBonusSharesList);
			if(weakDetailDao.updataWeakCurrentProjectsDetailAuditStatusAndAuditInsoByProjectCode("4010", "N", project_code)){
				app_log.addWCPALog(project_code, "申请奖金分配", "4010", (String) SecurityUtils.getSubject().getPrincipal(), "申请奖金分配");
				if(isAddOrSub!=0){
					return ResultMessage.MessageToJson(0, "添加成功！"+errString);
				}else {
					return ResultMessage.MessageToJson(0, "添加成功");
				}
				
			}else {
				return ResultMessage.MessageToJson(1, "分配数据添加成功，更新项目的审核状态失败，项目ID："+project_code);
			}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResultMessage.MessageToJson(1,  "添加失败:"+e.getMessage());
		}
	}
	
	/**
	 * 个人奖金分配是否存在
	 * @param info
	 * @return
	 */
	@RequestMapping(value = "/isHaveWeakCurrentProjectsBonusSharesBean",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String isHaveWeakCurrentProjectsBonusSharesBean(String project_code) {
		try {
			int count=weakBonusSharesDao.queryAllInfoCountsByProjectCode(project_code);
			return ResultMessage.MessageToJson(0, count);
		} catch (Exception e) {
			return ResultMessage.MessageToJson(1, "获取人奖金分配方案失败："+e.getMessage());
		}
		
	}


	/**
	 * 通过项目编码和类型获取项目的奖金分配信息
	 * @param project_code
	 * @return
	 */
	@RequestMapping(value = "/queryWeakCurrentProjectsBonusSharesBeanInfoByProjectCodeAndType",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryWeakCurrentProjectsBonusSharesBeanInfoByProjectCodeAndType(String project_code, String type) {
			System.out.println("project_code="+project_code);
			System.out.println("type="+type);
			weakBonusSharesList=weakBonusSharesDao.queryAllInfoWithProjectCode(project_code);
			return ResultMessage.ListtoLayuiTable(weakBonusSharesList.size(), weakBonusSharesList);

	}

	/**
	 * 通过项目编码更新项目个人奖金审批状态
	 * @param project_code
	 * @return
	 */
	@RequestMapping(value = "/updataWeakCurrentProjectsBonusSharesAuditStatusByProjectCode",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updataWeakCurrentProjectsBonusSharesAuditStatusByProjectCode(String audit_status ,String audit_info ,String project_code) {
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
				String _audit_status="";
				if(audit_status.equals("4041")){//4041表示的含义为：项目的奖金分配的申请已经通过。然后将通过判断项目是否有质保金，来进行状态码的分配
					String returned_money_ishave = weakDetailDao.WCPDReturnedMoneyIshave(project_code);
					if(returned_money_ishave.equals("Y")){
						_audit_status="9979";
					}else {
						_audit_status="9999";
					}
				}else {
					_audit_status=audit_status;
				}
				if(weakDetailDao.updataWeakCurrentProjectsDetailAuditStatusAndAuditInsoByProjectCode(_audit_status, audit_info, project_code)){
					app_log.addWCPALog(project_code, "申请奖金分配", audit_status, (String) SecurityUtils.getSubject().getPrincipal(), audit_info);
					return ResultMessage.MessageToJson(0, "审批成功");
				}else {
					return ResultMessage.MessageToJson(1, "审批失败");
				}
			} catch (Exception e) {
				return ResultMessage.MessageToJson(1, "审批失败："+e.getMessage());
			}
	}
	


}
