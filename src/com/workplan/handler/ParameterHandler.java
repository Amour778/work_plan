package com.workplan.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.workplan.bean.ParameterBean;
import com.workplan.dao.ParameterDao;
import com.workplan.tools.ResultMessage;
/**
 * 弱电项目用的参数Handler
 * @author 01059101
 *
 */
@Controller
public class ParameterHandler {
	
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	ParameterBean parameterBean =new ParameterBean();
	ParameterDao parameterDao = (ParameterDao) context.getBean("ParameterDao");
	List<ParameterBean> list =new ArrayList<ParameterBean>();

	/**
	 * 获取所有参数信息，无条件
	 * @return
	 */
	@RequestMapping(value="/queryAllParameterNoparameter",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryAllParameterNoparameter(){
		list = parameterDao.queryAllParameterNoparameter();
		return ResultMessage.ListtoLayuiTable(list.size(), list);
	}
	/**
	 * 通过PID获取参数
	 * @return
	 */
	@RequestMapping(value="/queryAllParameterByPid",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryAllParameterByPid(String pid){
		list = parameterDao.queryAllParameterByPid(pid);
		return ResultMessage.ListtoLayuiTable(list.size(), list);
	}
	/**
	 * 通过PID获取参数
	 * @return
	 */
	@RequestMapping(value="/queryAllParameterByPidList",produces="text/html;charset=UTF-8")
	@ResponseBody
	public List<ParameterBean> queryAllParameterByPidList(String pid){
		list = parameterDao.queryAllParameterByPid(pid);
		return list;
	}

	/**
	 * 通过PID获取参数
	 * @return
	 */
	@RequestMapping(value="/queryAllParameterToStringByPidList",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryAllParameterToStringByPidList(String pid){
		try {
			list = parameterDao.queryAllParameterByPid(pid);
			return ResultMessage.ListToJson(0, list);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.MessageToJson(1, "获取参数数据失败："+e.getMessage());
		}
	}
	/**
	 * 通过TYPE获取参数
	 * @return
	 */
	@RequestMapping(value="/queryAllParameterByType",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryAllParameterByType(String type){
		list = parameterDao.queryAllParameterByType(type);
		return ResultMessage.ListtoLayuiTable(list.size(), list);
	}
	/**
	 * 通过PID和CID获取参数
	 * @return
	 */
	@RequestMapping(value="/queryAllParameterByPidAndCid",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryAllParameterByPidAndCid(String pid,String cid){
		list = parameterDao.queryAllParameterByPidAndCid(pid, cid);
		return ResultMessage.ListtoLayuiTable(list.size(), list);
	}
	/**
	 * 添加新的参数
	 * @return
	 */
	@RequestMapping(value="/addParameter",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addParameter(String pid,String type,String cid,String text,String value,String select,String disable){
		boolean returnBoolean=false;
		try {
			returnBoolean=parameterDao.add(pid, type, cid, text, value, select, disable);
			if(returnBoolean){
				return ResultMessage.MessageToJson(0, "添加成功");
			}else {
				return ResultMessage.MessageToJson(1, "添加失败");
			}
		} catch (Exception e) {
			return ResultMessage.MessageToJson(1, "添加失败："+e.getMessage());
		}
		
	}
	/**
	 * 通过PID和CID更新数据
	 * @return
	 */
	@RequestMapping(value="/updateParameter",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updateParameter(String Npid,String type,String Ncid,String text,String value,String select,String disable,String Opid,String Ocid){
		boolean returnBoolean=false;
		try {
			returnBoolean=parameterDao.update(Npid, type, Ncid, text, value, select, disable, Opid, Ocid);
			if(returnBoolean){
				return ResultMessage.MessageToJson(0, "更新成功");
			}else {
				return ResultMessage.MessageToJson(1, "更新失败");
			}
		} catch (Exception e) {
			return ResultMessage.MessageToJson(1, "更新失败："+e.getMessage());
		}
	}
	/**
	 * 通过PID和CID删除数据
	 * @return
	 */
	@RequestMapping(value="/delParameter",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String delParameter( String pid,String cid){
		boolean returnBoolean=false;
		try {
			returnBoolean=parameterDao.del(pid, cid);
			if(returnBoolean){
				return ResultMessage.MessageToJson(0, "删除成功");
			}else {
				return ResultMessage.MessageToJson(1, "删除失败");
			}
		} catch (Exception e) {
			return ResultMessage.MessageToJson(1, "删除失败："+e.getMessage());
		}
	}
	
	
}
