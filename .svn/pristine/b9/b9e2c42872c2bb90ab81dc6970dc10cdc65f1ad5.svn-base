package com.workplan.handler;


import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.workplan.bean.WorkItemFirstBean;
import com.workplan.dao.WorkItemFirstDao;
import com.workplan.dao.WorkItemSecondDao;
import com.workplan.tools.GetDateTimeNow;
import com.workplan.tools.GsonUtil;
import com.workplan.tools.ResultMessage;

@Controller
public class WorkItemFirstHandler {

	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

	/**
	 * 获取所有的一级事项
	 * @return
	 */
	@RequestMapping(value ="/getItemFirst",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getItemFirst() {
		System.out.println("getItemFirst");
		WorkItemFirstDao dao=(WorkItemFirstDao) context.getBean("WorkItemFirstDao");
		List<WorkItemFirstBean> listInfo =new ArrayList<WorkItemFirstBean>();
		listInfo=dao.queryAll();
		GsonUtil gsonUtil=new GsonUtil();
		String returnHtmlString=gsonUtil.List2Json(listInfo);
		System.out.println(returnHtmlString);
		return returnHtmlString;
	}


	/**
	 * 获取所有的一级事项ToTable
	 * @return
	 */
	@RequestMapping(value ="/getItemFirstToTable",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getItemFirstToTable(String item_name,int page,int limit) {
		System.out.println("getItemFirstToTable");
		System.out.println("item_name="+item_name);
		WorkItemFirstDao dao=(WorkItemFirstDao) context.getBean("WorkItemFirstDao");
		List<WorkItemFirstBean> listInfo =new ArrayList<WorkItemFirstBean>();
		listInfo=dao.queryAllToTable(item_name,page, limit);
		int counts=dao.queryAllToTableCounts(item_name).size();
		GsonUtil gsonUtil=new GsonUtil();
		ResultMessage mssage=new ResultMessage();
		String returnHtmlString=ResultMessage.ListtoLayuiTable(counts, gsonUtil.List2Json(listInfo));
		System.out.println(returnHtmlString);
		return returnHtmlString;
	}
	
	/**
	 * 添加新的一级事项
	 * @param item_name
	 * @return
	 */
	@RequestMapping(value ="/addItemFirst",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addItemFirst(String item_name) {
		System.out.println("addItemFirst");
		WorkItemFirstDao dao=(WorkItemFirstDao) context.getBean("WorkItemFirstDao");
		GetDateTimeNow item_id=new GetDateTimeNow();
		ResultMessage message=new ResultMessage();
		System.out.println("item_name"+item_name);
		if(dao.add(GetDateTimeNow.getDateTimeRandomToID(), item_name)){
			return ResultMessage.MessageToJson(0, "添加成功");
		}else {
			return ResultMessage.MessageToJson(1, "添加失败");
		}
	}
	
	
	@RequestMapping(value ="/updataItemFirst",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updataItemFirst(String item_name,String item_id) {
		System.out.println("updataItemFirst");
		WorkItemFirstDao dao=(WorkItemFirstDao) context.getBean("WorkItemFirstDao");
		ResultMessage message=new ResultMessage();
		if(dao.updata(item_id, item_name)){
			return ResultMessage.MessageToJson(0, "更新成功");
		}else {
			return ResultMessage.MessageToJson(1, "更新失败");
		}
	}

	/**
	 * 通过Item_id删除一级事项
	 * @param item_id
	 * @return
	 */
	@RequestMapping(value ="/deleteItemFirst",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String deleteItemFirst(String item_id) {
		System.out.println("deleteItemFirst");
		WorkItemFirstDao dao=(WorkItemFirstDao) context.getBean("WorkItemFirstDao");
		ResultMessage message=new ResultMessage();
		if(dao.deleteByItemId(item_id)){
			return ResultMessage.MessageToJson(0, "删除成功");
		}else {
			return ResultMessage.MessageToJson(1, "删除失败");
		}
	}
	
	/**
	 * 通过item_id批量删除事项，且会删除事项相关的二级事项
	 * @param item_id
	 * @return
	 */
	@RequestMapping(value ="/batchDeleteFirstByItemId",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String batchDeleteFirstByItemId(String item_id) {
		System.out.println("item_id="+item_id);
		List<WorkItemFirstBean> list= new ArrayList<WorkItemFirstBean>();
		String[] arrStrings=item_id.split(";");
		for(int a= 0;a<arrStrings.length;a++){
			WorkItemFirstBean bean=new WorkItemFirstBean();
			System.out.println("arrStrings["+a+"]="+arrStrings[a]);
			bean.setItemId(arrStrings[a]);
			list.add(bean);
		}
		WorkItemFirstDao first_dao=(WorkItemFirstDao) context.getBean("WorkItemFirstDao");
		ResultMessage message=new ResultMessage();
		for(int a= 0;a<list.size();a++){
			System.out.println("list.get("+a+").getItemId()="+list.get(a).getItemId());
		}
		System.out.println();
		if (first_dao.batchDeleteByItemId(list) == 0) {
			WorkItemSecondDao second_dao = (WorkItemSecondDao) context.getBean("WorkItemSecondDao");
			if (second_dao.batchDeleteBySuperiorItemId(list) == 0) {
				return ResultMessage.MessageToJson(0, "删除成功");
			} else {
				return ResultMessage.MessageToJson(1, "删除一级事项名称成功，<b>删除二级事项名称失败</b>");
			}
		} else {
			return ResultMessage.MessageToJson(1, "删除失败");
		}
	}
}
