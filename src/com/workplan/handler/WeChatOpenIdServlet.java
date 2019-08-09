package com.workplan.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.shiro.SecurityUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.workplan.bean.Shnett_EdianzuBean;
import com.workplan.bean.UserInfoBean;
import com.workplan.dao.Shnett_EdianzuDao;
import com.workplan.dao.UserInfoDao;
import com.workplan.tools.GetDateTimeNow;
import com.workplan.tools.GsonUtil;
import com.workplan.tools.HttpRequestUtil;
import com.workplan.tools.ImageCompressUtil;
import com.workplan.tools.ResultMessage;
import com.workplan.tools.TransformUtil;
import com.workplan.tools.sqlHelper;
import com.workplan.upload.download.export.ExportExcelUtil;
import com.workplan.upload.download.export.ExportExcelWrapperMergeCellUtil;

/**
 * 微信小程序 易点租
 * 
 * @author 01059101
 * 
 */

@Controller
public class WeChatOpenIdServlet {
	sqlHelper sqlHelper=new sqlHelper();
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	UserInfoDao dao = (UserInfoDao) context.getBean("UserInfoDao");
	Shnett_EdianzuDao sedao = (Shnett_EdianzuDao) context.getBean("Shnett_EdianzuDao");
	List<Shnett_EdianzuBean> edianzuList = new ArrayList<Shnett_EdianzuBean>();
	
	

	@RequestMapping(value="/WeChat_MiNi/UserIsLogin",produces="text/html;charset=UTF-8")
	@ResponseBody
		public String UserIsLogin(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String sessionid = request.getParameter("sessionid");
		HttpSession session = request.getSession();
		if(session.getSessionContext().getSession(sessionid)!=null){
			//用户已登录
		}else {
			//用户未登录
		}
		return sessionid;
		
	}
	
	/**
	 * 获取用户OPENID并验证是否已经绑定工号
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/WeChat_MiNi/OpenIdServlet",produces="text/html;charset=UTF-8")
	@ResponseBody
		public String OpenIdServlet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		System.out.println("OpenIdServlet");
		String code = request.getParameter("code");
		String APP_ID = "wx8aa07791fab4ceaa";
		String APP_SECRET = "ec55e44d68e34647261c41e02b41841e";
		String APP_GRANT_TYPE = "authorization_code";

		String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
		/*Map<String, String> requestUrlParam = new HashMap<String, String>();
		requestUrlParam.put("appid", APP_ID);
		requestUrlParam.put("secret", APP_SECRET);
		requestUrlParam.put("js_code", code);
		requestUrlParam.put("grant_type", APP_GRANT_TYPE);*/
		Gson gson = new Gson();
		String json = HttpRequestUtil.sendGet(requestUrl, "appid=" + APP_ID
				+ "&secret=" + APP_SECRET + "&js_code=" + code + "&grant_type="
				+ APP_GRANT_TYPE);
		System.out.println("json=" + json);
		//HttpSession session = request.getSession();
		GsonUtil.MINIProngramOPENID result = gson.fromJson(json, GsonUtil.MINIProngramOPENID.class);
		//String openId=result.getOpenid();//openId
		//String session_key=result.getSession_key();//session_key
		ResultMessage ResultMessage = new ResultMessage();
		if (result.getErrcode() == null) {
			String OPENID = result.getOpenid();
			System.out.println("OPENID=" + OPENID);
			List<UserInfoBean> list = dao.querySimpleUserSimpleInfoByOPENID(OPENID);
			//System.out.println("list.size()=" + list.size());
			if (list.size() == 0) {
				System.out.println("MESSAGE=" + "BIND");
				return ResultMessage.MessageToJson(0, OPENID);
			} else if (list.size() ==1) {
				System.out.println("MESSAGE=" + "正常获取用户信息");
				return ResultMessage.ListToJson(1, list);
			} else{
				System.out.println("MESSAGE=" + "用户OPENID存在多个绑定工号");
				return ResultMessage.MessageToJson(2, "用户OPENID存在多个绑定工号");
			}
		} else {
			System.out.println("MESSAGE=" + "获取用户OPENID失败");
			return ResultMessage.MessageToJson(2, "获取用户OPENID失败");
		}
	}
	
	/**
	 * 绑定用户OPENID和工号
	 * @param user_id
	 * @param openId
	 * @return
	 */
	@RequestMapping(value="/WeChat_MiNi/BindUserId",produces="text/html;charset=UTF-8")
	@ResponseBody
		public String BindUserId(String user_id,String openId){
		System.out.println("/BindUserId");
		if(dao.changeUserOpenId(openId, user_id)){
			List<UserInfoBean> list = dao.querySimpleUserSimpleInfoByOPENID(openId);
			if (list.size() ==1) {
				System.out.println("MESSAGE=" + "正常获取用户信息");
				return ResultMessage.ListToJson(0, list);
			} else{
				System.out.println("MESSAGE=" + "用户OPENID存在多个绑定工号");
				return ResultMessage.MessageToJson(1, "绑定异常");
			}
		}else {
			return ResultMessage.MessageToJson(1, "绑定失败,工号不存在");
		}
	}
	/**
	 * 上传图片
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/WeChat_MiNi/doUploadPic",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String doUploadWCPPic(HttpServletRequest request,HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8"); // 设置编码
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		String path = request.getSession().getServletContext().getRealPath("WEB-INF/WeChat_Files_Upload");
		Map<String, Object> uploadFile = uploadFile(request, path);
		if ((Boolean) uploadFile.get("state")) {
			ImageCompressUtil imageCompressUtil = new ImageCompressUtil();
			try {
				imageCompressUtil.compressPic(path+"\\"+uploadFile.get("filename"), path+"\\"+uploadFile.get("filename"));
			} catch (Exception e) {
				System.out.println("压缩图片失败："+path+"\\"+uploadFile.get("filename"));
				e.printStackTrace();
			}
			System.out.println("上传的文件名为：" + uploadFile.get("filename"));
			return uploadFile.get("filename").toString();
		} else {
			return "FALSE";
		}
	}

    /**
     * 删除图片文件
     * @param request
     * @param system
     * @param project_code
     * @param project_progress
     * @param suffix
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/WeChat_MiNi/doDelPic",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String deletePicByPicName(HttpServletRequest request,
			String pic_name,
            HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8"); // 设置编码
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
    	String fileName = request.getSession().getServletContext().getRealPath("WEB-INF/WeChat_Files_Upload") + "/" + pic_name;
    	System.out.println("fileName="+fileName);
    	File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("删除文件失败:" + fileName + "不存在！");
     		return ResultMessage.MessageToJson(2, "删除文件失败:" + pic_name + "不存在！");
        } else {
        	WeakCurrentProjectsDetailHandler wcpdHandler = new WeakCurrentProjectsDetailHandler();
            if(deleteFile(fileName)){
            	return ResultMessage.MessageToJson(0, "删除成功！");
            }else {
       			return ResultMessage.MessageToJson(1, "删除失败！未知错误");
			} 
         }
    }

	/**
	 * 添加信息
	 * @param info
	 * @param insert_userid
	 * @return
	 */
	@RequestMapping(value="/WeChat_MiNi/insertInfo",produces="text/html;charset=UTF-8")
	@ResponseBody
		public String insertInfo(String insert_userid,HttpServletRequest request,HttpServletResponse response) throws IOException {
		String info=request.getParameter("info");
		try {
			if(sqlHelper.getWechat_edianzu_transcoding().equals("true")){
				info=new String(request.getParameter("info").getBytes("ISO8859-1"), "UTF-8");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(WeChatOpenIdServlet.class+"进行中文转码时异常："+e.getMessage());
		}
		System.out.println(info);
		System.out.println("insert_userid="+insert_userid);
		try {
			JSONObject obj = JSONObject.fromObject(info);
			String images= obj.getString("images").replace("wxfile://", "").replace("http://tmp/","");
			String event_type=null;
			if(obj.getString("event_type").equals("0")){
				event_type="风控类";
			}else if(obj.getString("event_type").equals("1")){
				event_type="技术类";
			}else if(obj.getString("event_type").equals("2")){
				event_type="租返类";
			}else if(obj.getString("event_type").equals("3")){
				event_type="巡检类";
			}else if(obj.getString("event_type").equals("4")){
				event_type="库房上门类";
			}else if(obj.getString("event_type").equals("5")){
				event_type="驻场类";
			}else {
				event_type=obj.getString("event_type");
			}
			String startDate= obj.getString("startDate");
			String startTime= obj.getString("startTime");
			String endDate= obj.getString("endDate");
			String endTime= obj.getString("endTime");
			String company_name= obj.getString("company_name");
			String region_address = obj.getString("region_address");//省市区
			String company_address= obj.getString("company_address");//具体地址
			String region_province=region_address.split(",")[0].replace("[\"", "").replace("\"", "");
			String region_city=region_address.split(",")[1].replace("\"", "");
			String region_area=region_address.split(",")[2].replace("\"]", "").replace("\"", "");
			System.out.println(region_province+region_city+region_area);
			String company_contacts= obj.getString("company_contacts");
			String company_contact_phone= obj.getString("company_contact_phone");
			String orders_number= obj.getString("orders_number");
			String number_of_inspection= obj.getString("number_of_inspection");
			String courier_number = obj.getString("courier_number");
			String car_fare= obj.getString("car_fare");
			if(sedao.insert(images, event_type, startDate, startTime, endDate, endTime, company_name, region_province,region_city,region_area,company_address, company_contacts, 
					company_contact_phone, orders_number, number_of_inspection, insert_userid, courier_number, car_fare)){
					return ResultMessage.MessageToJson(0, "数据提交成功");
			}else{
				return ResultMessage.MessageToJson(1,  "数据提交失败");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResultMessage.MessageToJson(1,  "数据提交失败:"+e.getMessage());
		}
		
	}
	
	/**
	 * 多选下拉框
	 * @return 
	 */
	@RequestMapping(value = "/getWeChatEdianzuFormSelectsInfo",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getWeChatEdianzuFormSelectsInfo() {
		UserInfoDao userDao = (UserInfoDao) context.getBean("UserInfoDao");
		List<UserInfoBean> userList = userDao.querySimpleUserInfoByUserAreaIsNotNull();
		String USER_ID = (String) SecurityUtils.getSubject().getPrincipal();
		String USER_AREA = null;
		String USER_NAME = null;
		String areaArray="";//用户拥有的角色ID
		for(UserInfoBean role:userList){
			areaArray+=role.getUser_area_wechat_edianzu()+",";
			if(USER_ID.equals(role.getUser_id())){
				USER_AREA=role.getUser_area_wechat_edianzu();
				USER_NAME=role.getUser_name();
			}
		}
		if (areaArray.length()==0) {
			return "[]";
		}
		String[] areaArrayList = areaArray.split(",");
		areaArrayList=TransformUtil.array_unique(areaArrayList);
		//地区
		String reString="";
		if(USER_AREA!=null){
			reString+="{\"name\": \"地区\", \"type\": \"optgroup\"},";
			reString+="{\"name\": \""+USER_AREA+"\", \"value\":\"USER_AREA|"+USER_AREA+"\",\"selected\":\"selected\",\"disabled\":\"disabled\"},";
		}else {
			reString+="{\"name\": \"地区\", \"type\": \"optgroup\"},";
			for (int i = 0; i < areaArrayList.length; i++) {
				reString+="{\"name\": \""+areaArrayList[i]+"\", \"value\":\"USER_AREA|"+areaArrayList[i]+"\",\"selected\":\"\"},";
			}
		}
		//工单类型
		reString+="{\"name\": \"工单类型\", \"type\": \"optgroup\"},";
		reString+="{\"name\": \"风控类\", \"value\":\"EVENT_TYPE|风控类\",\"selected\":\"\"},";
		reString+="{\"name\": \"技术类\", \"value\":\"EVENT_TYPE|技术类\",\"selected\":\"\"},";
		reString+="{\"name\": \"租返类\", \"value\":\"EVENT_TYPE|租返类\",\"selected\":\"\"},";
		reString+="{\"name\": \"巡检类\", \"value\":\"EVENT_TYPE|巡检类\",\"selected\":\"\"},";
		reString+="{\"name\": \"库房上门类\", \"value\":\"EVENT_TYPE|库房上门类\",\"selected\":\"\"},";
		reString+="{\"name\": \"驻场类\", \"value\":\"EVENT_TYPE|驻场类\",\"selected\":\"\"},";

		//用户
		if(USER_AREA!=null){
			reString+="{\"name\": \"用户\", \"type\": \"optgroup\"},";
			reString+="{\"name\": \""+USER_ID+"-"+USER_NAME+"\", \"value\":\"USER_ID|"+USER_ID+"\",\"selected\":\"selected\",\"disabled\":\"disabled\"},";
		}else{
			reString+="{\"name\": \"用户\", \"type\": \"optgroup\"},";
			for(int a= 0;a<userList.size();a++){
				reString+="{\"name\": \""+userList.get(a).getUser_id()+"-"+userList.get(a).getUser_name()+"\", \"value\":\"USER_ID|"+userList.get(a).getUser_id()+"\",\"selected\":\"\"},";
			}
		}

		
		reString="{\"code\":0,\"msg\":\"success\",\"data\":["+reString.substring(0, reString.length()-1)+"]}";
		System.out.println(reString);
		return reString;
	}
	/**
	 * 搜索：根据条件获取数据
	 * @param SearchCondition
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/queryWeChatEdianzuToLayuiTable",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryWeChatEdianzuToLayuiTable(String SearchCondition,String date,int page, int limit) {
		System.out.println("SearchCondition="+SearchCondition);
		System.out.println("date="+date);
		if(SearchCondition==null){
			System.out.println("SearchCondition==null");
			SearchCondition="";
		}
		if(date==null){
			System.out.println("date==null");
			date="";
		}
		String arr[]=SearchCondition.split(",");

		for(int a=0;a<arr.length;a++){
			System.out.println("arr[a]="+arr[a].toString());
		}
		ArrayList<String> USER_AREA = new ArrayList<String>();
		ArrayList<String> EVENT_TYPE = new ArrayList<String>();
		ArrayList<String> USER_ID = new ArrayList<String>();
		for(int a=0;a<arr.length;a++){
			if(arr[a].toString().indexOf("USER_AREA")!=-1){
				USER_AREA.add(arr[a].split("\\|")[1]);//这里之所以用"\\|"，是因为"|"是特殊字符
			}else if (arr[a].toString().indexOf("EVENT_TYPE")!=-1) {
				EVENT_TYPE.add(arr[a].split("\\|")[1]);	
			}else if (arr[a].toString().indexOf("USER_ID")!=-1) {
				USER_ID.add(arr[a].split("\\|")[1]);	
			}
		}
		String StartDate=null,EndDate=null;
		if(date!=null&&date!=""){
			StartDate=date.split("~")[0].trim();
			EndDate=date.split("~")[1].trim();
			System.out.println("StartDate="+StartDate.trim());
			System.out.println("EndDate="+EndDate.trim());
		}
		int Counts= sedao.queryAllInfoCountsBySearchCondition(USER_AREA, EVENT_TYPE, USER_ID, StartDate, EndDate);
		edianzuList = sedao.queryAllInfoBySearchCondition(USER_AREA, EVENT_TYPE, USER_ID, StartDate, EndDate, page, limit);
		GsonUtil gsonUtil=new GsonUtil();
		return ResultMessage.ListtoLayuiTable(Counts, gsonUtil.List2Json(edianzuList));
	}
	
	
	 /**
     * 处理图片显示请求
     * @param fileName
     */
    @RequestMapping(value="/WeChat_MiNi/showWeChatPic/{img_name}.{suffix}")
    public void showPicture(HttpServletRequest request,
    						@PathVariable("img_name") String img_name,
                            @PathVariable("suffix") String suffix,
                            HttpServletResponse response){
    	response.setContentType("image/jpeg/jpg/png/gif/bmp/tiff/svg");
    	System.out.println("img_name="+img_name);
    	System.out.println("suffix="+suffix);
    	String pic_urlString=request.getSession().getServletContext().getRealPath("WEB-INF/WeChat_Files_Upload") + "/" + img_name + "." + suffix;
    	FileInputStream fis = null;
		try {
			OutputStream out = response.getOutputStream();
			File file = new File(pic_urlString);
			fis = new FileInputStream(file);
			byte[] b = new byte[fis.available()];
			fis.read(b);
			out.write(b);
			out.flush();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    }
    /**
     * 响应输出图片文件
     * @param response
     * @param imgFile
     */
    private void responseFile(HttpServletResponse response, File imgFile) {
    	
        try{
        	InputStream is = new FileInputStream(imgFile);
            OutputStream os = response.getOutputStream();
            byte [] buffer = new byte[1024];
            while(is.read(buffer) != -1){
                os.write(buffer);
            }
            os.flush();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * 微信小程序-最近几条数据
     * @param USER_ID
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/WeChat_MiNi/queryAllInfoByUserIDWithLimit",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryAllInfoByUserIDWithLimit(String USER_ID,int page,int limit) {
    	System.out.println("/queryAllInfoByUserIDWithLimit");
    	System.out.println("USER_ID="+USER_ID);
    	System.out.println("page="+page);
    	System.out.println("limit="+limit);
		edianzuList = sedao.queryAllInfoByUserIDWithLimit(USER_ID, page, limit);
		return ResultMessage.ListToJson(0, edianzuList);
	}

    /**
     * 微信小程序--特定某条数据
     * @param USER_ID
     * @param ID
     * @return
     */
    @RequestMapping(value = "/WeChat_MiNi/queryOneInfoByID",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryOneInfoByID(String USER_ID,String ID) {
		edianzuList = sedao.queryOneInfoByID(USER_ID, ID);
		if(edianzuList.size()==0){
			return ResultMessage.MessageToJson(1, "获取数据失败");
		}else {
			return ResultMessage.ListToJson(0, edianzuList);
		}
		
	}
	


    
    /**
     * 微信小程序--搜索数据
     * @param USER_ID
     * @param StartDate
     * @param EndDate
     * @return
     */
    @RequestMapping(value = "/WeChat_MiNi/queryOneInfoByIDupdataOneInfoByUserIdAndID",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryOneInfoByIDupdataOneInfoByUserIdAndID(String USER_ID,String StartDate,String EndDate) {
		edianzuList = sedao.queryAllInfoByStartDateAndEndDate(USER_ID, StartDate, EndDate);
		return ResultMessage.ListToJson(0, edianzuList);
	}    
    
    /**
     * 微信小程序--删除数据
     * @param USER_ID
     * @param ID
     * @return
     */
    @RequestMapping(value = "/WeChat_MiNi/deleteOneInfoByUSER_IDAndID",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String deleteOneInfoByUSER_IDAndID(String USER_ID,String ID) {
		if(sedao.deleteOneInfoByUSER_IDAndID(USER_ID, ID)){
			return ResultMessage.MessageToJson(0, "删除成功");
		}else{
			return ResultMessage.MessageToJson(1, "删除失败");
		}
	}
    


    /**
     * 微信小程序--修改数据：通过insert_userid和ID限制
     * @param images
     * @param event_type
     * @param startDate
     * @param startTime
     * @param endDate
     * @param endTime
     * @param company_name
     * @param company_address
     * @param company_contacts
     * @param company_contact_phone
     * @param orders_number
     * @param number_of_inspection
     * @param courier_number
     * @param car_fare
     * @param insert_userid
     * @param ID
     * @return
     */
    @RequestMapping(value = "/WeChat_MiNi/updataOneInfoByUserIdAndID",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updataOneInfoByUserIdAndID(String insert_userid, String ID,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String info = request.getParameter("info");
		try {
			if(sqlHelper.getWechat_edianzu_transcoding().equals("true")){
				info=new String(request.getParameter("info").getBytes("ISO8859-1"), "UTF-8");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(WeChatOpenIdServlet.class+"进行中文转码时异常："+e.getMessage());
		}
		System.out.println(info);
		System.out.println("insert_userid=" + insert_userid);
		try {
			JSONObject obj = JSONObject.fromObject(info);
			String images="";
			String image = obj.getString("images").replace("wxfile://", "")
					.replace("http://tmp/", "");
			String image_url[]=image.split(",");
			for (int i = 0; i < image_url.length; i++) {
				String imageurl[]=image_url[i].split("/");
				images+=imageurl[imageurl.length-1]+",";
			}
			if(images.substring(images.length()-1,images.length()).equals(",")){
				images=images.substring(0,images.length()-1);
			}
			System.out.println(images);
			String event_type = null;
			if (obj.getString("event_type").equals("0")) {
				event_type = "风控类";
			} else if (obj.getString("event_type").equals("1")) {
				event_type = "技术类";
			} else if (obj.getString("event_type").equals("2")) {
				event_type = "租返类";
			} else if (obj.getString("event_type").equals("3")) {
				event_type = "巡检类";
			} else if (obj.getString("event_type").equals("4")) {
				event_type = "库房上门类";
			} else if (obj.getString("event_type").equals("5")) {
				event_type = "驻场类";
			} else {
				event_type = obj.getString("event_type");
			}
			String startDate = obj.getString("startDate");
			String startTime = obj.getString("startTime");
			String endDate = obj.getString("endDate");
			String endTime = obj.getString("endTime");
			String company_name = obj.getString("company_name");
			String region_address = obj.getString("region_address");//省市区
			String company_address= obj.getString("company_address");//具体地址
			String region_province=region_address.split(",")[0].replace("[\"", "").replace("\"", "");
			String region_city=region_address.split(",")[1].replace("\"", "");
			String region_area=region_address.split(",")[2].replace("\"]", "").replace("\"", "");
			System.out.println(region_province+region_city+region_area);
			String company_contacts = obj.getString("company_contacts");
			String company_contact_phone = obj.getString("company_contact_phone");
			String orders_number = obj.getString("orders_number");
			String number_of_inspection = obj.getString("number_of_inspection");
			String courier_number = obj.getString("courier_number");
			String car_fare = obj.getString("car_fare");
			if (sedao.updataOneInfoByUserIdAndID(images, event_type, startDate,
					startTime, endDate, endTime, company_name, region_province,region_city,region_area,company_address,
					company_contacts, company_contact_phone, orders_number,
					number_of_inspection, courier_number, car_fare,
					insert_userid, ID)) {
				return ResultMessage.MessageToJson(0, "修改成功");
			} else {
				return ResultMessage.MessageToJson(1, "修改失败");
			}
		} catch (Exception e) {
			return ResultMessage.MessageToJson(1, "修改失败:" + e.getMessage());
		}
	}
	
	
    /**
	 * 导出小程序上传数据(不包括图片)
	 * @return
	 */
	@RequestMapping(value ="/ExportExcelWeChatEdianzu",produces="text/html;charset=UTF-8")
	@ResponseBody
	public void ExportExcelWeChatEdianzu(HttpServletRequest request, HttpServletResponse response,String SearchCondition,String date) throws Exception {
		System.out.println("/ExportExcelWeChatEdianzu");
		System.out.println("SearchCondition="+SearchCondition);
		System.out.println("date="+date);
		if(SearchCondition==null){
			System.out.println("SearchCondition==null");
			SearchCondition="";
		}
		String arr[]=SearchCondition.split(",");

		for(int a=0;a<arr.length;a++){
			System.out.println("arr[a]="+arr[a].toString());
		}
		ArrayList<String> USER_AREA = new ArrayList<String>();
		ArrayList<String> EVENT_TYPE = new ArrayList<String>();
		ArrayList<String> USER_ID = new ArrayList<String>();
		for(int a=0;a<arr.length;a++){
			if(arr[a].toString().indexOf("USER_AREA")!=-1){
				USER_AREA.add(arr[a].split("\\|")[1]);//这里之所以用"\\|"，是因为"|"是特殊字符
			}else if (arr[a].toString().indexOf("EVENT_TYPE")!=-1) {
				EVENT_TYPE.add(arr[a].split("\\|")[1]);	
			}else if (arr[a].toString().indexOf("USER_ID")!=-1) {
				USER_ID.add(arr[a].split("\\|")[1]);	
			}
		}
		String StartDate=null,EndDate=null;
		if(date!=null&&date!=""&&!date.equals("null")){
			StartDate=date.split("~")[0].trim();
			EndDate=date.split("~")[1].trim();
			System.out.println("StartDate="+StartDate.trim());
			System.out.println("EndDate="+EndDate.trim());
		}
		String[] headers={"上门类型","到场日期","到场时间","完成日期","完成时间","公司名称","省市区","公司地址","公司联系人","公司联系人联系电话","单子数量","租返单号 ","巡检设备数量","交通费","录入时间","录入人工号","录入人姓名","录入人地区"};
		GetDateTimeNow getTimeNow=new GetDateTimeNow();
		String fileName="小程序易点租信息收集"+getTimeNow.getDateTimeRandomToID();
		edianzuList=sedao.queryAllInfoBySearchConditionToExcelExport(USER_AREA, EVENT_TYPE, USER_ID, StartDate, EndDate);
		System.out.println("导出的前期准备工作完成");
		ExportExcelWrapperMergeCellUtil<Shnett_EdianzuBean> util = new ExportExcelWrapperMergeCellUtil<Shnett_EdianzuBean>();
		System.out.println("【导出开始】");
		util.exportExcel2007MergeCellShnett_EdianzuBean(fileName, "小程序易点租信息收集", headers, edianzuList, response,ExportExcelUtil.EXCEl_FILE_2007);
		System.out.println("【导出完成】");
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    
    /**
	 * 
	* @Title: uploadFile 
	* @Description: TODO(上传文件) 
	* @param @param request
	* @param @param path
	* @param @return    
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	public static Map<String, Object> uploadFile(HttpServletRequest request,
			String path) {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		creatFile(path);
		factory.setRepository(new File(path));
		factory.setSizeThreshold(1024 * 1024);
		ServletFileUpload upload = new ServletFileUpload(factory);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			List<FileItem> list = upload.parseRequest(request);
			FileItem item = getUploadFileItem(list);
			String filename = getUploadFileName(item);
			Map<String, String> splitFile = splitFile(filename);
			//System.out.println("存放目录:" + path);
			//System.out.println("文件名:" + splitFile.get("name"));
			// 真正写到磁盘上
			item.write(new File(path, filename)); // 第三方提供的
			returnMap.put("state", true);
			returnMap.put("filename", filename);
			returnMap.put("splitFile", splitFile.get("format"));
		} catch (FileUploadException e) {
			returnMap.put("state", false);
		} catch (Exception e) {
			returnMap.put("state", false);
		}
		return returnMap;
	}
	
	/**
	 * 
	* @Description: TODO(拆分文件名) 
	* @param @param str
	* @param @return    name：文件名称 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	public static Map<String, String> splitFile(String str) {
		String name=str.substring(0,str.lastIndexOf("."));
		String format=str.substring(str.lastIndexOf("."),str.length());
		Map<String, String> returnMap =new HashMap<String, String>();
		returnMap.put("name", name);
		returnMap.put("format", format);
		return returnMap;
	}

	/**
	 * 获取路径名
	 * @Title: getUploadFileName
	 * @Description: TODO( 获取路径名)
	 * @param @param item
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	private static String getUploadFileName(FileItem item) {
		// 获取路径名
		String value = item.getName();
		// 索引到最后一个反斜杠
		int start = value.lastIndexOf("/");
		// 截取 上传文件的 字符串名字，加1是 去掉反斜杠，
		String filename = value.substring(start + 1);

		return filename;
	}

	/**
	 * @Title: getUploadFileItem
	 * @Description: TODO(获取上传文件)
	 * @param @param list
	 * @param @return
	 * @return FileItem 返回类型
	 * @throws
	 */
	private static FileItem getUploadFileItem(List<FileItem> list) {
		for (FileItem fileItem : list) {
			if (!fileItem.isFormField()) {
				return fileItem;
			}
		}
		return null;
	}

	/**
	 * @Title: creatFile
	 * @Description: TODO(创建文件夹)
	 * @param @param path
	 * @return void 返回类型
	 * @throws
	 */
	public static void creatFile(String path) {
		File file = new File(path);
		// 如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {
			//System.out.println("//目录"+path+"不存在");
			file.mkdir();
		} else {
			//System.out.println("//目录"+path+"存在");
		}
	}

	/**
	 * 
	* @Title: judeFileExists 
	* @Description: TODO(判断文件是否存在) 
	* @param @param path
	* @param @return    
	* @return boolean    返回类型 
	* @throws
	 */
	public static boolean judeFileExists(String path) {
		System.out.println("judeFileExists："+path);
		File file = new File(path);
		if (file.exists()) {
			System.out.println("文件存在");
			//file.delete();
			//System.out.println("删除完成...");
			return true;
		} else {
			System.out.println("文件不存在");
			return false;
		}

	}
	
	/**
	* @Title: deleteFile 
	* @Description: TODO(删除单个文件) 
	* @param @param sPath
	* @param @return    
	* @return boolean    返回类型 
	* @throws
	 */
	public static boolean deleteFile(String path) {  
	    File file = new File(path);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        return true;  
	    }  
	    return false;  
	}  
    
}