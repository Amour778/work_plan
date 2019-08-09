package com.workplan.handler;

import java.util.ArrayList;
import java.util.List;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.workplan.bean.PermissionBean;
import com.workplan.bean.RoleBean;
import com.workplan.bean.UserInfoBean;
import com.workplan.dao.PermissionDao;
import com.workplan.dao.RoleDao;
import com.workplan.dao.UserInfoDao;

/**
 * 自定义的指定Shiro验证用户登录的类
 */
public class MyShiroRealm extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		// TODO Auto-generated method stub
		System.out.println(MyShiroRealm.class+":Z");
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		UserInfoDao userinfoDao = (UserInfoDao) context.getBean("UserInfoDao");
		UserInfoBean userinfoBean=null;
		if(arg0==null){
			
		}
		//MemoryConstrainedCacheManager cache=new MemoryConstrainedCacheManager();
		try {
			userinfoBean = userinfoDao.queryForInfoBean(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(userinfoBean!=null){
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			List<String> roles_permissions_List = new ArrayList<String>();
			RoleDao roleDao = (RoleDao) context.getBean("RoleDao");
			//搜索用户角色
			List<RoleBean> roleBean=null;
			try {
				System.out.println("搜索用户角色");
				roleBean = roleDao.queryForFatherInfoListByRoleIds(userinfoBean.getUser_role());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//System.out.println("roleBean.size()="+roleBean.size());
			String perString="";
			//添加角色
			for (RoleBean role : roleBean) {
				roles_permissions_List.add(role.getRole_name());
				perString+=role.getRole_per()+",";
			}
			info.addRoles(roles_permissions_List);
			roles_permissions_List = new ArrayList<String>();
			//搜索用户角色对应权限
			PermissionDao permissionDao = (PermissionDao) context.getBean("PermissionDao");
			if(perString.substring(perString.length()-1, perString.length()).equals(","))
				perString=perString.substring(0, perString.length()-1);
			List<PermissionBean> permissionBean=null;
			try {
				permissionBean = permissionDao.queryInfoListByMenuIds(perString);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//添加角色对应的权限
			for (PermissionBean permission : permissionBean) {
				roles_permissions_List.add(permission.getUrl());
			}
			info.addStringPermissions(roles_permissions_List);
			return info;
		}
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		System.out.println(MyShiroRealm.class + ":C");
		System.out.println(MyShiroRealm.class + ":token==>"+token.toString());
		// token是用户输入的用户名和密码
		// 第一步从token中取出用户名

		// 1.把AuthenticationToken转换为UsernamePasswordToken
		UsernamePasswordToken userToken = (UsernamePasswordToken) token;
		
		// 2.从UsernamePasswordToken中获取username
		String username = userToken.getUsername();
		// 第二步：根据用户输入的userCode从数据库查询用户信息
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserInfoBean userDBean = null;
		UserInfoDao userDao = (UserInfoDao) context.getBean("UserInfoDao");
		SimpleAuthenticationInfo info = null;
		try {
			// 3.调用数据库的方法，从数据库中查询Username对应的用户记录
			userDBean = userDao.queryForInfoBean(username);

			// 4.根据用户的情况，来构建AuthenticationInfo对象,通常使用的实现类为SimpleAuthenticationInfo
			// 以下信息是从数据库中获取的
			// 1)principal：认证的实体信息，可以是username，也可以是数据库表对应的用户的实体对象
			Object principal = userDBean.getUser_id();
			// 2)credentials：密码
			Object credentials = userDBean.getUser_password();
			// 3)realmName：当前realm对象的name，调用父类的getName()方法即可
			String realmName = getName();
			// 4)credentialsSalt盐值
			ByteSource credentialsSalt = ByteSource.Util.bytes(principal);// 使用账号作为盐值
			info = new SimpleAuthenticationInfo(principal, credentials,
					credentialsSalt, realmName);
		} catch (Exception e1) {
			System.out.println("UserName为[ " + username + " ]用户不存在");
		}
		return info;

	}
	
}