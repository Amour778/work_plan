package com.workplan.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.workplan.bean.ParameterBean;
import com.workplan.bean.WeakCurrentProjectsActualExpenditureBean;
import com.workplan.bean.WeakCurrentProjectsSupplierBean;
import com.workplan.bean.WorkItemFirstBean;
import com.workplan.bean.WorkItemSecondSimpleBean;
import com.workplan.dao.WeakCurrentProjectsSupplierDao;
import com.workplan.dao.WorkItemFirstDao;
import com.workplan.dao.WorkItemSecondDao;
import com.workplan.tools.ArithUtil;
import com.workplan.tools.GetDateTimeNow;
import com.workplan.tools.GsonUtil;
import com.workplan.tools.ResultMessage;
@Controller
public class WeakCurrentProjectsSupplierHandler {
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

	WeakCurrentProjectsSupplierDao supplierDao = (WeakCurrentProjectsSupplierDao) context.getBean("WeakCurrentProjectsSupplierDao");

	List<WeakCurrentProjectsSupplierBean> supplierList = new ArrayList<WeakCurrentProjectsSupplierBean>();


	/**
	 *  添加供应商
	 * @param supplier_sid 客商编码
	 * @param supplier_name客商名称
	 * @param mnemonic_code助记码
	 * @param supplier_name_abbreviation客商简称
	 * @param supplier_english_name外文名称
	 * @return
	 */
	@RequestMapping(value = "/addWCPSupplierInfo",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addSupplierInfo(String supplier_sid,String supplier_name,String mnemonic_code,String supplier_name_abbreviation,String supplier_english_name) {
		try {
			if(supplierDao.insertSupplierInfo(supplier_sid,supplier_name,mnemonic_code,supplier_name_abbreviation,supplier_english_name)){
				return ResultMessage.MessageToJson(0, "添加供应商成功");
			}else {
				return ResultMessage.MessageToJson(1, "添加供应商失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			String errorCode = e.getCause().getMessage();
            if (errorCode.indexOf("Duplicate entry") >= 0) {
            	return ResultMessage.MessageToJson(1, "添加供应商失败：供应商编码已存在");
            }
			return ResultMessage.MessageToJson(1, "添加供应商失败："+e.getMessage());
		}
	}

	/**
	 * 搜索数据
	 * @param criteria
	 * @param page
	 * @param limit
	 * @return 符合用户搜索条件的事项结果
	 */
	@RequestMapping(value = "/querySupplierInfoBySearchCondition",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String querySupplierInfoBySearchCondition(String criteria,int page,int limit) {
		if(criteria.replaceAll("\\s*","").equals("")){
			criteria=null;
		}
		try {
			supplierList = supplierDao.queryAllSupplierList(criteria, page, limit);
			int counts = supplierDao.queryAllSupplierCounts(criteria, page, limit);
			return ResultMessage.ListtoLayuiTable(counts, GsonUtil.List2Json(supplierList));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultMessage.ListtoLayuiTableHaveError(1, "获取表格数据错误:"+e.getMessage());
		}
		
	}
	
	/**
	 * 付款申请单填写时所需要的单选下拉框
	 * @return 供应商名称/供应商代码
	 */
	@RequestMapping(value = "/querySupplierInfoToFormSelects",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String querySupplierInfoToFormSelects() {
		String reString="";
		try {
			supplierList = supplierDao.queryAllSupplierNoCriteria();
			for(int a= 0;a<supplierList.size();a++){
				reString+="{\"name\": \""+supplierList.get(a).getSupplier_name()+"\", \"value\":\""+supplierList.get(a).getSupplier_sid()+"\",\"selected\":\"\"},";
			}
			if(reString.length()!=0&&reString.substring(reString.length()-1, reString.length()).equals(",")){
				reString = reString.substring(0, reString.length()-1);
			}
			reString="{\"code\":0,\"msg\":\"success\",\"data\":["+reString+"]}";
		} catch (Exception e) {
			e.printStackTrace();
			reString="{\"code\":1,\"msg\":\"error"+e.getMessage()+"\",\"data\":[]}";
		}
		return reString;
	}
}
