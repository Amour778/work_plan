package com.workplan.handler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyuncs.exceptions.ClientException;
import com.workplan.bean.PermissionBean;
import com.workplan.bean.RoleBean;
import com.workplan.bean.UserInfoBean;
import com.workplan.dao.PermissionDao;
import com.workplan.dao.RoleDao;
import com.workplan.dao.UserInfoDao;
import com.workplan.tools.GeneratePasswordUtil;
import com.workplan.tools.GetDateTimeNow;
import com.workplan.tools.ResultMessage;
import com.workplan.tools.SHA1Util;
import com.workplan.tools.SendShortMessage;
import com.workplan.upload.download.export.uploadUserInfo;



@Controller
public class UserInfoHandler {
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	SendShortMessage SendShortMessage = new SendShortMessage();//短信
	UserInfoDao dao = (UserInfoDao) context.getBean("UserInfoDao");
	List<UserInfoBean> userlist = new ArrayList<UserInfoBean>();

	
	/**
	 * 登陆
	 * @return
	 */
	@RequestMapping(value = "/tologin",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String toLogin(@RequestParam("username") String username,
			@RequestParam("password") String password, 
			@RequestParam("validateCode") String validateCode,
			@RequestParam("ip") String ip_address,
			HttpServletRequest request) {
		String returnmessageString="";
		HttpSession session = request.getSession();

		String validateCodeInSession = (String) session.getAttribute("VALIDATE_CODE");
		if (!(validateCode.equalsIgnoreCase(validateCodeInSession))) {
			//System.out.println("验证码错误");
			returnmessageString=ResultMessage.MessageToJson(1, "验证码错误");
		}else {
			//清除验证码
			//session.invalidate();
			// 1.创建Subject
			Subject subject = SecurityUtils.getSubject();
			// 2.创建用户登录凭证
			//System.out.println("username="+username);
			//System.out.println("password="+password);
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			/*System.out.println("token.getHost()="+token.getHost());
			System.out.println("token.getUsername()="+token.getUsername());
			System.out.println("token.getCredentials()="+token.getCredentials().hashCode());
			System.out.println("token.getPassword()="+token.getPassword().toString());
			System.out.println("token.getPrincipal()="+token.getPrincipal());
			System.out.println("token.isRememberMe()="+token.isRememberMe());*/
			token.setRememberMe(true);
			// 3.登录，如果登录失败会抛出不同的异常，根据异常输出失败原因
			try {
				subject.login(token);
				// 6.判断是否成功登录
				//assertEquals(true, subject.isAuthenticated());
				//System.out.println("登录成功！！");
				returnmessageString=ResultMessage.MessageToJson(0, "登录成功");
				if (null != subject) {
					Session ses = subject.getSession();
					ses.setTimeout(1000 * 60 * 60 * 2);
					//System.out.println("修改Session超时时间为[" + ses.getTimeout() + "]毫秒");
					ses.setAttribute("SESSION_USERNAME", subject.getPrincipals());
					//System.out.println("password_oldString="+password_oldString);
					LoginLogHandler loginLog=new LoginLogHandler();
					String user_id=username;
					loginLog.addLoginLog(user_id, ip_address);
				}
				//验证是否登录成功
				if(subject.isAuthenticated()){
					// 判断用户是否拥有某个角色
					//assertEquals(true, subject.hasRole("admin"));
					// 使用Shiro自带的断言判断用户是否有被授权
					//subject.checkRole("manager");
					//subject.checkPermission("create_user1");
					System.out.println("subject.hasRole(ruoa)="+subject.hasRole("ruoa"));
					System.out.println("subject.hasRole(ruod)="+subject.hasRole("ruod"));
					System.out.println("subject.hasRole(ruoh)="+subject.hasRole("ruoh"));
					System.out.println("subject.hasRole(ruof)="+subject.hasRole("ruof"));
					System.out.println("subject.hasRole(ruob)="+subject.hasRole("ruob"));
					System.out.println("subject.hasRole(super_admin)="+subject.hasRole("super_admin"));
					System.out.println("subject.hasRole(wck_per_user)="+subject.hasRole("wck_per_user"));
					//System.out.println("subject.hasRole(admin)="+subject.hasRole("admin"));
					//System.out.println("subject.isPermitted(/deleteuser)="+subject.isPermitted("/del_user"));
					//System.out.println("subject.isPermitted(/main)="+subject.isPermitted("/main"));
				}else{
					token.clear();
				}
				
			} catch (IncorrectCredentialsException e) {
				System.out.println("登录密码错误. Password for account " + token.getPrincipal() + " was incorrect.");
				returnmessageString=ResultMessage.MessageToJson(1, "登录密码错误");
			} catch (ExcessiveAttemptsException e) {
				System.out.println("登录失败次数过多");
				returnmessageString=ResultMessage.MessageToJson(1, "登录失败次数过多");
			} catch (LockedAccountException e) {
				System.out.println("帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.");
				returnmessageString=ResultMessage.MessageToJson(1, "帐号已被锁定");
			} catch (DisabledAccountException e) {
				System.out.println("帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.");
				returnmessageString=ResultMessage.MessageToJson(1, "帐号已被禁用");
			} catch (ExpiredCredentialsException e) {
				System.out.println("帐号已过期. the account for username " + token.getPrincipal() + "  was expired.");
				returnmessageString=ResultMessage.MessageToJson(1, "帐号已过期");
			} catch (UnknownAccountException e) {
				System.out.println("帐号不存在. There is no user with username of " + token.getPrincipal());
				returnmessageString=ResultMessage.MessageToJson(1, "帐号不存在");
			} catch (UnauthorizedException e) {
				System.out.println("您没有得到相应的授权！" + e.getMessage());
				returnmessageString=ResultMessage.MessageToJson(1, "您没有得到相应的授权");
			}
		}
		
		return returnmessageString;
	}
	/**
	 * 获取所有用户信息
	 * @return
	 
	@RequestMapping(value = "/getUserAllInfo",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getUserAllInfo() {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		UserInfoDao userDao = (UserInfoDao) context.getBean("UserInfoDao");
		List<UserInfoBean> userBean = userDao.querySimpleUserSimpleInfoByUserId(username);
		return ResultMessage.ListToJson(0, userBean);
	}
*/

	/**
	 * 获取用户拥有的角色名称：管理员，普通用户
	
	@RequestMapping(value = "/getUserRoleNickName",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getUserRoleNickName() {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		UserInfoDao userDao = (UserInfoDao) context.getBean("UserInfoDao");
		List<UserInfoBean> userBean = userDao.querySimpleUserSimpleInfoByUserId(username);
		if(!(userBean.size()==0)&&!(userBean.get(0).getUser_role().equals(""))){
			RoleDao roleDao = (RoleDao) context.getBean("RoleDao");
			List<RoleBean> roleBean = new ArrayList<RoleBean>();
			try {
				roleBean = roleDao.queryForFatherInfoListByRoleIds(userBean.get(0).getUser_role());
			} catch (Exception e) {
				e.printStackTrace();
				return ResultMessage.MessageToJson(1, "获取数据失败");
			}
			return ResultMessage.ListToJson(0, roleBean);
		}else {
			return ResultMessage.MessageToJson(1, "用户不存在");
		}
	}
	 */

	/**
	 * 批量删除用户,通过用户ID
	 * @param user_id
	 * @return
	 */
	@RequestMapping(value = "/batch_del_user",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String batchDeleteUserByUserId(String user_id) {
		String[] aa = user_id.split(";");
		UserInfoDao dao = (UserInfoDao) context.getBean("UserInfoDao");
		List<UserInfoBean> list = new ArrayList<UserInfoBean>();
		UserInfoBean bean = new UserInfoBean();
		for (int i = 0; i < aa.length; i++) {
			bean = new UserInfoBean();
			System.out.println("aa["+i+"]"+aa[i]);
			bean.setUser_id(aa[i]);
			list.add(bean);
		}
		int a=dao.batchDeleteByUserId(list);
		if(a==0){
			return ResultMessage.MessageToJson(0, "删除成功");
		}else {
			return ResultMessage.MessageToJson(1, "删除失败");
		}
	}
	/**
	 * 删除用户
	 * @param user_id
	 */
	@RequestMapping(value = "/del_user",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String deleteUserInfo(String userid) {
		UserInfoDao dao = (UserInfoDao) context.getBean("UserInfoDao");
		if(dao.deleteUserInfo(userid)){
			return ResultMessage.MessageToJson(0, "删除成功");
		}else {
			return ResultMessage.MessageToJson(1, "删除失败");
		}
		
	}



	/**
	 * 搜索数据
	 * @param SearchCondition
	 * @param page
	 * @param limit
	 * @return 符合用户搜索条件的事项结果
	 */
	@RequestMapping(value = "/get_user",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryUserInfoBySearchCondition(String user_id,int page,int limit) {
			UserInfoDao dao = (UserInfoDao) context.getBean("UserInfoDao");
			List<UserInfoBean> bean_list = new ArrayList<UserInfoBean>();
			int Counts=0;
			bean_list=dao.queryAllInfoWithSearchConditionToLayui(user_id, page, limit);
			Counts=dao.queryAllInfoCountsBySearchCondition(user_id);
			return ResultMessage.ListtoLayuiTable(Counts,bean_list);
	}

	/**
	 * 通过用户选择的大区，获取所有用户信息
	 * @param wcp_area
	 * @return
	 */
	@RequestMapping(value = "/queryUserListByWCPArea",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String queryUserListByWCPArea(String wcp_area) {
			UserInfoDao dao = (UserInfoDao) context.getBean("UserInfoDao");
			List<UserInfoBean> bean_list = new ArrayList<UserInfoBean>();
			bean_list=dao.queryUserByWCPArea(wcp_area);
			return ResultMessage.ListToJson(bean_list.size(), bean_list);
	}
	
	/**
	 * 添加用户
	 * @param user_id
	 * @param user_name
	 * @param user_tel
	 * @param user_password
	 * @param user_role
	 * @return
	 */
	@RequestMapping(value = "/add_user",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addUserInfo(@RequestParam("user_id") String user_id,
			@RequestParam("user_name") String user_name,
			@RequestParam("user_tel") String user_tel,
			String user_area_wechat_edianzu,
			String wcp_area,
			String wcp_head,
			@RequestParam("user_role") String user_role) {
		UserInfoDao dao = (UserInfoDao) context.getBean("UserInfoDao");
		if(dao.queryAllInfoCountsBySearchCondition(user_id)>0)
			return ResultMessage.MessageToJson(1, "用户工号已存在");
		else {
			String user_password=GeneratePasswordUtil.generatePassword();
			try {
				if(dao.add(user_id, user_name, user_tel,SHA1Util.hex_sha1(user_password),user_role,user_area_wechat_edianzu,wcp_area,wcp_head)){
					String sendString=SendShortMessage.sendShortMessage(user_tel, user_password,"GeneratePasswordTemplateCode");
					if(sendString.equals("0")){
						return ResultMessage.MessageToJson(0, "添加用户成功，请查看短信获取密码");
					}else {
						return ResultMessage.MessageToJson(0, "添加用户成功，短信发送失败，返回码:"+sendString);
					}
				}else {
					return ResultMessage.MessageToJson(1, "添加用户失败");
				}
			} catch (ClientException e) {
				e.printStackTrace();
				return ResultMessage.MessageToJson(1, "添加用户成功，用户密码发送失败。请使用'重置密码'功能重新生成密码");
			}
			
		}
		
	}
	/**
	 * 修改密码
	 * @param user_id
	 */
	@RequestMapping(value = "/reset_password",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String changeUserPassword(String user_password,String user_id) {
		UserInfoDao dao = (UserInfoDao) context.getBean("UserInfoDao");
		if(dao.changeUserPassword(user_password, user_id)){
			return ResultMessage.MessageToJson(0, "修改成功");
		}else {
			return ResultMessage.MessageToJson(1, "修改失败");
		}
		
	}
	
	
	/**
	 * 生成复杂密码
	 * @param user_id
	 */
	@RequestMapping(value = "/generatePassword",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String generatePassword(String user_id) {
		String user_password=GeneratePasswordUtil.generatePassword();
		System.out.println("用户密码生成："+user_password);
		String userpassword =SHA1Util.hex_sha1(user_password);
		UserInfoDao dao = (UserInfoDao) context.getBean("UserInfoDao");
		if(dao.changeUserPassword(userpassword, user_id)){
			
			List<UserInfoBean> userlist = new ArrayList<UserInfoBean>();
			userlist=dao.queryAll(user_id);
			try {
				SendShortMessage.sendShortMessage(userlist.get(0).getUser_tel(), user_password,"GeneratePasswordTemplateCode");
				return ResultMessage.MessageToJson(0, "密码重置成功，请查看短信获取新密码");
			} catch (ClientException e) {
				e.printStackTrace();
				return ResultMessage.MessageToJson(1, "密码重置成功，短信发送失败：<br/>CODE："+e.getErrCode()+"<br/>MSG："+e.getErrMsg());
			}
		}else {
			return ResultMessage.MessageToJson(1, "密码重置失败");
		}
		
	}
	
	/**
	 * 修改用户手机号
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/edit_phone",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updataUserTel(String user_id,String user_tel){
		UserInfoDao dao = (UserInfoDao) context.getBean("UserInfoDao");
		if(dao.changeUserTel(user_tel, user_id)){
			return ResultMessage.MessageToJson(0, "修改成功");
		}else{
			return ResultMessage.MessageToJson(1, "修改失败");
		}
		
	}
	/**
	 * 上传用户列表信息来添加用户
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/doUploadUserinfo",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String doUploadUserinfo(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		uploadUserInfo upload = new uploadUserInfo();
		return upload.upload(request, response);
	}
	


	/**
	 * 通过ID获取用户信息
	 * @return 用户姓名
	 */
	@RequestMapping(value = "/getUserInfoWithUserId",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getUserInfoWithUserId() {
		UserInfoDao dao = (UserInfoDao) context.getBean("UserInfoDao");
		List<UserInfoBean> userlist = new ArrayList<UserInfoBean>();
		try {
			String user_id = (String) SecurityUtils.getSubject().getPrincipal();
			System.out.println("------------------------------user_id="+user_id);
			userlist=dao.queryAll(user_id);
			if(userlist.size()==1){
				userlist.get(0).setUser_password(null);
				String reString=ResultMessage.ListToJson(0, userlist);
				return reString;
			}else {
				return ResultMessage.MessageToJson(1, "获取数据失败");
			}
		} catch (Exception e) {
			return ResultMessage.MessageToJson(1, "获取数据失败,请重新登陆");
		}
	}


	/**
	 * 通过用户ID修改用户角色
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/updataUserRoleByUserId",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updataUserRoleByUserId(String user_name,String user_role_string){
		UserInfoDao dao = (UserInfoDao) context.getBean("UserInfoDao");
		try {
			if(dao.changeUserRole(user_role_string,  user_name)){
				return ResultMessage.MessageToJson(0, "修改成功");
			}else {
				return ResultMessage.MessageToJson(1, "修改失败");
			}
		} catch (Exception e) {
			return ResultMessage.MessageToJson(1, "获取数据失败,请重新登陆");
		}
	}
	/**
	 * 通过用户ID修改用户手机号和姓名
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/updataUserNameAndTelWithUserId",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updataUserNameAndTelWithUserId(String user_name,String user_tel){
		UserInfoDao dao = (UserInfoDao) context.getBean("UserInfoDao");
		try {

			String user_id = (String) SecurityUtils.getSubject().getPrincipal();
			System.out.println("------------------------------user_id="+user_id);
			if(dao.changeUserNameAndTel(user_name, user_tel, user_id)){
				return ResultMessage.MessageToJson(0, "修改成功");
			}else {
				return ResultMessage.MessageToJson(1, "修改失败");
			}
		} catch (Exception e) {
			return ResultMessage.MessageToJson(1, "获取数据失败,请重新登陆");
		}
	}
	/**
	 * 通过用户ID修改用户密码
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/updataUserPwdWithUserId",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updataUserPwdWithUserId(String pwd_old,String pwd_new){
		UserInfoDao dao = (UserInfoDao) context.getBean("UserInfoDao");
		String user_id = (String) SecurityUtils.getSubject().getPrincipal();
		int userInfoCount = dao.queryInfoByUserIdAndPassword(user_id, pwd_old);
		System.out.println("符合用户名为："+user_id+"旧密码为："+pwd_old+"的用户数为："+userInfoCount);
		try {
			if(userInfoCount==1)
			{
				if (dao.changeUserPassword(pwd_new, user_id)) {
					return ResultMessage.MessageToJson(0, "修改成功");
				} else {
					return ResultMessage.MessageToJson(1, "修改失败");
				}
			} else {
				return ResultMessage.MessageToJson(1, "旧密码错误");
			}
		} catch (Exception e) {
			return ResultMessage.MessageToJson(1, "获取数据失败,请重新登陆");
		}
	}
	
	
	/**
	 * 通过用户工号获取用户角色名称/ID列表:List<String>
	 * @param role_type
	 * @param user_id
	 * @return
	 */
	public List<String> getRoleListByUserId(String role_type,String user_id) {
		List<String> roles_List = new ArrayList<String>();
		UserInfoDao userinfoDao = (UserInfoDao) context.getBean("UserInfoDao");
		UserInfoBean userinfoBean = null;
		try {
			userinfoBean = userinfoDao.queryForInfoBean(user_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (userinfoBean != null) {
			RoleDao roleDao = (RoleDao) context.getBean("RoleDao");
			List<RoleBean> roleBean = null;
			try {
				roleBean = roleDao.queryForFatherInfoListByRoleIds(userinfoBean.getUser_role());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(role_type.equals("name")){
			for (RoleBean role : roleBean) {
				roles_List.add(role.getRole_nickname());
			}}else if(role_type.equals("id")){
				for (RoleBean role : roleBean) {
					roles_List.add(role.getRole_id());
				}
			}
		}
			return roles_List;
	}
	
	// 通过用户工号获取用户角色列表:List<String>
	public List<String> getPermissionListByUserId(String user_id) {
		List<String> permissions_List = new ArrayList<String>();
		List<String> roleBean = getRoleListByUserId("id", user_id);
		String perString = "";
		for (String role : roleBean) {
			perString += role + ",";
		}
		permissions_List = new ArrayList<String>();
		if (perString.substring(perString.length() - 1, perString.length()).equals(","))
			perString = perString.substring(0, perString.length() - 1);
		PermissionDao permissionDao = (PermissionDao) context.getBean("PermissionDao");
		List<PermissionBean> permissionBean = null;
		try {
			permissionBean = permissionDao.queryInfoListByMenuIds(perString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (PermissionBean permission : permissionBean) {
			permissions_List.add(permission.getUrl());
		}
		return permissions_List;
	}

	/**
	 * 多选下拉框：获取用户信息1:用于责任人
	 * @return 用户姓名，联系方式
	 */
	@RequestMapping(value = "/getAllUserInfoToPerson",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getAllUserInfoToPerson() {
		UserInfoDao dao = (UserInfoDao) context.getBean("UserInfoDao");
		List<UserInfoBean> userlist = new ArrayList<UserInfoBean>();
		userlist=dao.queryAll();
		String reString="";
		for(int a= 0;a<userlist.size();a++){
			reString+="{\"name\":\""+userlist.get(a).getUser_name()+"\",\"value\":\""+userlist.get(a).getUser_name()+","+userlist.get(a).getUser_tel()+"\",\"selected\":\"\",\"disabled\":\"\"},";
			}
		reString="{\"code\":0,\"msg\":\"success\",\"data\":["+reString.substring(0, reString.length()-1)+"]}";
		//GsonUtil gsonUtil =new GsonUtil();
		//return gsonUtil.List2Json(userlist);
		System.out.println(reString);
		return reString;
	}
	/**
	 * 配合人也需要联系方式，故取消此方法
	 * 多选下拉框：获取用户信息2:用于配合人
	 * @return 用户姓名
	 */
	@RequestMapping(value = "/getAllUserInfo",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getAllUserInfo() {
		UserInfoDao dao = (UserInfoDao) context.getBean("UserInfoDao");
		List<UserInfoBean> userlist = new ArrayList<UserInfoBean>();
		userlist=dao.queryAll();
		String reString="";
		for(int a= 0;a<userlist.size();a++){
			reString+="{\"name\":\""+userlist.get(a).getUser_name()+"\",\"value\":\""+userlist.get(a).getUser_name()+"\",\"selected\":\"\",\"disabled\":\"\"},";
		}
		reString="{\"code\":0,\"msg\":\"success\",\"data\":["+reString.substring(0, reString.length()-1)+"]}";
		//GsonUtil gsonUtil =new GsonUtil();
		//return gsonUtil.List2Json(userlist);
		System.out.println(reString);
		return reString;
	}

	/**
	 * FormSelects所需数据
	 * 
	 * @return name:用户姓名 value:用户工号
	 */
	@RequestMapping(value = "/getAllUserInfoToFormSelects",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getAllUserInfoToFormSelects() {
		UserInfoDao dao = (UserInfoDao) context.getBean("UserInfoDao");
		List<UserInfoBean> userlist = new ArrayList<UserInfoBean>();
		userlist=dao.queryAll();
		String reString="";
		for(int a= 0;a<userlist.size();a++){
			reString+="{\"name\":\""+userlist.get(a).getUser_name()+"\",\"value\":\""+userlist.get(a).getUser_id()+"\",\"selected\":\"\",\"disabled\":\"\"},";
		}
		reString="{\"code\":0,\"msg\":\"success\",\"data\":["+reString.substring(0, reString.length()-1)+"]}";
		//GsonUtil gsonUtil =new GsonUtil();
		//return gsonUtil.List2Json(userlist);
		System.out.println(reString);
		return reString;
	}

	/**
	 * 通过用户工号，获取用户的银行卡信息，用于自动填充报销单
	 * @param user_id
	 * @return
	 */
	public String getUserInfoToReimbursementByUserId(String user_id) {
		//通过项目
		userlist=dao.queryUserInfoToReimbursementByUserId(user_id);
		if(userlist.size()==0){
			return "[{\"code\":1,\"msg\":\"未获取到用户信息\"}]";
		}else {
			String user_area=userlist.get(0).getWcp_area();
			List<UserInfoBean> headlist = new ArrayList<UserInfoBean>();
			headlist = dao.queryWCPHeadByWCPArea(user_area);
			if(headlist.size()==0){
				return "[{\"code\":1,\"msg\":\"未获取到用户所处大区的大区负责人信息\"}]";
			}
			String head_id = headlist.get(0).getUser_id();
			String head_name =  headlist.get(0).getUser_name();
			String returnString = "[{\"code\":0," +
					"\"msg\":[{\"head_id\":\""+head_id+"\"," +
					"\"head_name\":\""+head_name+"\"," +
					"\"user_id\":\""+userlist.get(0).getUser_id()+"\"," +
					"\"user_name\":\""+userlist.get(0).getUser_name()+"\"," +
					"\"wcp_area\":\""+user_area+"\"," +
					"\"bank\":\""+userlist.get(0).getBank()+"\"," +
					"\"bank_card_account\":\""+userlist.get(0).getBank_card_account()+"\"," +
					"\"bank_card_place\":\""+userlist.get(0).getBank_card_place()+"\"," +
					"\"bank_card_type\":\""+userlist.get(0).getBank_card_type()+"\"," +
					"\"date_time\":\""+GetDateTimeNow.getDate()+"\"," +
					"\"head_id\":\""+head_id+"\"}]}]";
			return returnString;
		}
	}
	/**
	 * 通过用户输入的工号，获取工号信息
	 * @param user_id
	 * @return
	 */
	@RequestMapping(value = "/getUserNameAndWCPAreaByUserId",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getUserNameAndWCPAreaByUserId(String user_id) {
		//通过项目
		userlist=dao.queryUserInfoToReimbursementByUserId(user_id);
		if(userlist.size()==0||userlist==null){
			return ResultMessage.MessageToJson(1, "未获取到用户信息");
		}else {
			return ResultMessage.ListToJson(0, userlist);
		}
	}
	
	

}
