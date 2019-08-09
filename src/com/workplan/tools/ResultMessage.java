package com.workplan.tools;

import java.util.List;

import net.sf.json.JSONObject;

public class ResultMessage {
	
	public static void main(String[] args) {
	}
	

	static GsonUtil gsonUtil=new GsonUtil();
	/**
	 * 将List数据转换成Layui表格数据需要的格式
	 * 
	 * @param Counts
	 *            int 数据表格的总数
	 * @param listString
	 *            数据表格分页后的数据 json格式
	 * @return String
	 */
	public static String ListtoLayuiTable(int Counts, String listString) {
		String MESSAGE = "{\"code\": 0,\"msg\": \"\",\"count\":" + Counts
				+ ",\"data\":" + listString + "}";
		return MESSAGE;
	}
	/**
	 * 将List数据转换成Layui表格数据需要的格式
	 * @param code
	 * @param msg
	 * @return
	 */
	public static String ListtoLayuiTableHaveError(int code,String msg) {
		String MESSAGE = "{\"code\": "+code+",\"msg\": \""+msg+"\",\"count\":\"\",\"data\":[]}";
		return MESSAGE;
	}
	/**
	 * 将List数据转换成Layui表格数据需要的格式
	 * 
	 * @param Counts
	 *            int 数据表格的总数
	 * @param listString
	 *            数据表格分页后的数据 json格式
	 * @return String
	 */
	public static String ListtoLayuiTable(int Counts, List<?> list) {
		String MESSAGE = "{\"code\": 0,\"msg\": \"\",\"count\":" + Counts
				+ ",\"data\":" + gsonUtil.List2Json(list) + "}";
		return MESSAGE;
	}

	/**
	 * 将String数据转成(伪)Json数据所需要的格式
	 * 
	 * @param code
	 *            规定状态代码 ：0表示成功 ，1表示失败，更多则表示相应的内容
	 * @param info
	 *            相应的消息内容
	 * @return
	 */
	public static String errString(int code, String info) {
		String MESSAGE = "{\"code\":" + code + ",\"info\": \"" + info + "\"}";
		//System.out.println(ResultMessage.class+"-MessageToJson-MESSAGE="+MESSAGE);
		return MESSAGE;
	}

	/**
	 * 将String数据转成(伪)Json数据所需要的格式
	 * @param code
	 * @param info
	 * @return
	 */
	public static String MessageToJson(int code, String info) {
		String MESSAGE = "{\"code\":" + code + ",\"info\": \"" + info + "\"}";
		//System.out.println(ResultMessage.class+"-MessageToJson-MESSAGE="+MESSAGE);
		return MESSAGE;
	}

	/**
	 * 将Bean对象转换成需要的(伪)Json数据
	 * @param code
	 * @param object
	 * @return
	 */
	public static String MessageToJsonWithBean(int code, Object object) {
		String MESSAGE = "{\"code\":" + code + ",\"info\": " + JSONObject.fromObject(object).toString() + "}";
		//System.out.println(ResultMessage.class+"-MessageToJson-MESSAGE="+MESSAGE);
		return MESSAGE;
	}
	
	/**
	 * 将String数据转成(伪)Json数据所需要的格式
	 * 
	 * @param code
	 *            规定状态代码 ：0表示成功 ，1表示失败，更多则表示相应的内容
	 * @param info
	 *            相应的消息内容
	 * @return
	 */
	public static String MessageToJson(int code, String msg, String info) {
		String MESSAGE = "{\"code\":" + code + ",\"msg\": \"" + msg + "\",\"info\": \"" + info + "\"}";
		//System.out.println(ResultMessage.class+"-MessageToJson-MESSAGE="+MESSAGE);
		return MESSAGE;
	}
	/**
	 * 将String数据转成(伪)Json数据所需要的格式
	 * 
	 * @param code
	 *            规定状态代码 ：0表示成功 ，1表示失败，更多则表示相应的内容
	 * @param info
	 *            相应的消息内容
	 * @return
	 */
	public static String MessageToJson(int code, int info) {
		String MESSAGE = "{\"code\":" + code + ",\"info\": " + info + "}";
		//System.out.println(ResultMessage.class+"-MessageToJson-MESSAGE="+MESSAGE);
		return MESSAGE;
	}

	/**
	 * 将List数据转成(伪)Json数据所需要的格式
	 * 
	 * @param code
	 *            规定状态代码 ：0表示成功 ，1表示失败，更多则表示相应的内容
	 * @param info
	 *            相应的消息内容，此处的info为List转JSON后的数据
	 * @return
	 */
	public static String ListToJson(int code, List<?> list) {
		String MESSAGE = "{\"code\":" + code + ",\"info\": " + gsonUtil.List2Json(list) + "}";
		return MESSAGE;
	}


	/**
	 * 将List数据转成(伪)Json数据所需要的格式
	 * 
	 * @param code
	 *            规定状态代码 ：0表示成功 ，1表示失败，更多则表示相应的内容
	 * @param info
	 *            相应的消息内容，此处的info为List转JSON后的数据
	 * @return
	 */
	public static String ListsToJson(int code, List<?> listOne, List<?> listTwo) {
		String MESSAGE = "{\"code\":" + code + ",\"infoOne\": " + gsonUtil.List2Json(listOne) +",\"infoTwo\": " + gsonUtil.List2Json(listTwo) + "}";
		return MESSAGE;
	}

	/**
	 * 将List数据和info数据转成(伪)Json数据所需要的格式
	 * 
	 * @param code
	 *            规定状态代码 ：0表示成功 ，1表示失败，更多则表示相应的内容
	 * @param list
	 *            List<?>数据
	 * @param info
	 *            String数据[{}]
	 * @return
	 */
	public static String ListsAndInfoToJson(int code, List<?> list, String info, String other) {
		String MESSAGE = "{\"code\":" + code + ",\"list\": " + gsonUtil.List2Json(list) +",\"info\": " + info + ",\"other\": " + other + "}";
		return MESSAGE;
	}
	
	/**
	 * 将List数据转成(伪)Json数据所需要的格式
	 * 
	 * @param code
	 *            规定状态代码 ：0表示成功 ，1表示失败，更多则表示相应的内容
	 * @param info
	 *            相应的消息内容，此处的info为List转JSON后的数据
	 * @return
	 */
	public static String ListToFormSelects(String dataString) {
		String MESSAGE = "{\"code\":0,\"msg\":\"success\",\"data\":["+dataString+"]}";
		return MESSAGE;
	}
	
	/**
	 * 此处只是组合:将LIST数据转换为树形结构所需要的json形式，data数组中的内容由用户传递进来，
	 * "status":{"code":200,"message":"操作成功"},
		"data": [
		  {"id":"001","title": "湖南省","checkArr": "0","parentId": "0"},
		  {"id":"002","title": "湖北省","checkArr": "0","parentId": "0"},
		  {"id":"003","title": "广东省","checkArr": "0","parentId": "0"},
		  {"id":"004","title": "浙江省","checkArr": "0","parentId": "0"},
		  {"id":"005","title": "福建省","checkArr": "0","parentId": "0"},
		  {"id":"001001","title": "长沙市","checkArr": "0","parentId": "001"},
		  {"id":"001002","title": "株洲市","checkArr": "0","parentId": "001"},
		  {"id":"001003","title": "湘潭市","checkArr": "0","parentId": "001"},
		  {"id":"001004","title": "衡阳市","checkArr": "0","parentId": "001"},
		  {"id":"001005","title": "郴州市","checkArr": "0","iconClass": "dtree-icon-caidan_xunzhang","parentId": "001"}
		]
		}
	 * @return
	 */
	public static String ListDataToDTree(String dataString) {
		String MESSAGE="{\"status\":{\"code\":200,\"message\":\"操作成功\"},\"data\": ["+dataString+"]}";
		return MESSAGE;
		
	}
	
}
