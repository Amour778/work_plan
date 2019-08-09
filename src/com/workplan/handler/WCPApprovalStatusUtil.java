package com.workplan.handler;

import java.util.HashMap;
import java.util.Map;


/**
 * 这个类为弱电项目审核状态，判定项目即将变更的审核状态是否符合流程，避免流程跨步
 * @author 01059101
 *
 */
public class WCPApprovalStatusUtil {
	
	/**
	 * 用于判断当前的审批状态码是否正确
	 * @param mysql_approval_status
	 * @param user_approval_status
	 * @return
	 */
	public static Map<String, String> approvalStatusCheck(int mysql_approval_status,int user_approval_status) {
		System.out.println("数据库审核状态代码为："+mysql_approval_status+"进入的用户审核状态代码为："+user_approval_status);
		boolean returnBoolean=false;
		String infoString=approvalStatusCode2Info(mysql_approval_status);
		//立项
		if (user_approval_status==1012||user_approval_status==1020) {
			if(mysql_approval_status==1010){
				returnBoolean= true;
			}
		}
		else if (user_approval_status==1021||user_approval_status==1022) {
			if(mysql_approval_status==1020){
				returnBoolean= true;
			}
		}
		//实际开支
		else if (user_approval_status==2010) {
			if(mysql_approval_status!=2041){
				returnBoolean= true;
			}
		}
		else if (user_approval_status==2012||user_approval_status==2020) {
			if(mysql_approval_status==2010){
				returnBoolean= true;
			}
		}
		else if (user_approval_status==2022||user_approval_status==2030) {
			if(mysql_approval_status==2020){
				returnBoolean= true;
			}
		}
		else if (user_approval_status==2032||user_approval_status==2040) {
			if(mysql_approval_status==2030){
				returnBoolean= true;
			}
		}
		else if (user_approval_status==2041||user_approval_status==2042) {
			if(mysql_approval_status==2040){
				returnBoolean= true;
			}
		}
		//付款申请单
		else if (user_approval_status==2110) {
			if(mysql_approval_status!=2141){
				returnBoolean= true;
			}
		}
		else if (user_approval_status==2112||user_approval_status==2120) {
			if(mysql_approval_status==2110){
				returnBoolean= true;
			}
		}
		else if (user_approval_status==2122||user_approval_status==2130) {
			if(mysql_approval_status==2120){
				returnBoolean= true;
			}
		}
		else if (user_approval_status==2132||user_approval_status==2140) {
			if(mysql_approval_status==2130){
				returnBoolean= true;
			}
		}
		else if (user_approval_status==2141||user_approval_status==2142) {
			if(mysql_approval_status==2140){
				returnBoolean= true;
			}
		}
		
		//申请开票
		else if (user_approval_status==2211||user_approval_status==2212) {
			if(mysql_approval_status==2210){
				returnBoolean= true;
			}
		}
		else if (user_approval_status==2210) {
			if(mysql_approval_status==2212){
				returnBoolean= true;
			}
		}
		
		
		//申请结项
		else if (user_approval_status==3010) {
			if(mysql_approval_status==1021){
				returnBoolean= true;
			}
		}
		else if (user_approval_status==3012||user_approval_status==3020) {
			if(mysql_approval_status==3010){
				returnBoolean= true;
			}
		}
		else if (user_approval_status==3022||user_approval_status==3030) {
			if(mysql_approval_status==3020){
				returnBoolean= true;
			}
		}
		else if (user_approval_status==3032||user_approval_status==3031) {
			if(mysql_approval_status==3030){
				returnBoolean= true;
			}
		}
		//个人奖金分配
		else if (user_approval_status==4010) {
			if(mysql_approval_status==3031){
				returnBoolean= true;
			}
		}
		else if (user_approval_status==4012||user_approval_status==4020) {
			if(mysql_approval_status==4010){
				returnBoolean= true;
			}
		}
		else if (user_approval_status==4022||user_approval_status==4030) {
			if(mysql_approval_status==4020){
				returnBoolean= true;
			}
		}
		else if (user_approval_status==4032||user_approval_status==4040) {
			if(mysql_approval_status==4030){
				returnBoolean= true;
			}
		}
		else if (user_approval_status==4042||user_approval_status==4041) {
			if(mysql_approval_status==4040){
				returnBoolean= true;
			}
		}
		else if (user_approval_status==9980) {
			if(mysql_approval_status==9979){
				returnBoolean= true;
			}
		}
		else if (user_approval_status==9989||user_approval_status==9999) {
			if(mysql_approval_status==9980){
				returnBoolean= true;
			}
		}
		Map<String, String> returnStringMap = new HashMap<String, String>();
		returnStringMap.put("BOOLEAN", returnBoolean+"");
		returnStringMap.put("INFO", infoString);
		return returnStringMap;
	}
	
	/**
	 * 根据数据库流程状态代码，获取对应状态中文描述
	 * @param statue_number
	 * @return
	 */
	public static String approvalStatusCode2Info(int statue_number) {
		System.out.println("获取对应状态中文描述，进入的用户审核状态代码为："+statue_number);
		String infoString;
		switch (statue_number) {
		case 1010:
			infoString = "立项-大区负责人待审核";
			break;
		case 1011:
			infoString = "立项-大区负责人已通过";
			break;
		case 1012:
			infoString = "立项-大区负责人已驳回";
			break;
		case 1020:
			infoString = "立项-管理员待审核";
			break;
		case 1021:
			infoString = "立项-已立项";
			break;
		case 1022:
			infoString = "立项-管理员已驳回";
			break;
		case 1030:
			infoString = "立项-已撤项";
			break;

		case 2010:
			infoString = "实际开支-大区负责人待审核";
			break;
		case 2011:
			infoString = "实际开支-大区负责人通过";
			break;
		case 2012:
			infoString = "实际开支-大区负责人驳回";
			break;
		case 2020:
			infoString = "实际开支-管理员待审核";
			break;
		case 2021:
			infoString = "实际开支-管理员已通过";
			break;
		case 2022:
			infoString = "实际开支-管理员已驳回";
			break;
		case 2030:
			infoString = "实际开支-财务待审核";
			break;
		case 2031:
			infoString = "实际开支-财务已通过";
			break;
		case 2032:
			infoString = "实际开支-财务已驳回";
			break;
		case 2040:
			infoString = "实际开支-BOSS待审核";
			break;
		case 2041:
			infoString = "实际开支-BOSS已通过";
			break;
		case 2042:
			infoString = "实际开支-BOSS已驳回";
			break;

		case 2110:
			infoString = "付款申请单-大区负责人待审核";
			break;
		case 2111:
			infoString = "付款申请单-大区负责人通过";
			break;
		case 2112:
			infoString = "付款申请单-大区负责人驳回";
			break;
		case 2120:
			infoString = "付款申请单-管理员待审核";
			break;
		case 2121:
			infoString = "付款申请单-管理员已通过";
			break;
		case 2122:
			infoString = "付款申请单-管理员已驳回";
			break;
		case 2130:
			infoString = "付款申请单-财务待审核";
			break;
		case 2131:
			infoString = "付款申请单-财务已通过";
			break;
		case 2132:
			infoString = "付款申请单-财务已驳回";
			break;
		case 2140:
			infoString = "付款申请单-BOSS待审核";
			break;
		case 2141:
			infoString = "付款申请单-BOSS已通过";
			break;
		case 2142:
			infoString = "付款申请单-BOSS已驳回";
			break;

		case 2210:
			infoString = "申请开票-财务待审核";
			break;
		case 2211:
			infoString = "申请开票-财务通过";
			break;
		case 2212:
			infoString = "申请开票-财务驳回";
			break;
		
			
		case 3010:
			infoString = "申请结项-大区负责人待审核";
			break;
		case 3011:
			infoString = "申请结项-大区负责人已通过";
			break;
		case 3012:
			infoString = "申请结项-大区负责人已驳回";
			break;
		case 3020:
			infoString = "申请结项-财务待审核";
			break;
		case 3021:
			infoString = "申请结项-财务已通过";
			break;
		case 3022:
			infoString = "申请结项-财务已驳回";
			break;
		case 3030:
			infoString = "申请结项-管理员待审核";
			break;
		case 3031:
			infoString = "申请结项-管理员已通过";
			break;
		case 3032:
			infoString = "申请结项-管理员已驳回";
			break;

		case 4010:
			infoString = "个人奖金-大区负责人待审核";
			break;
		case 4011:
			infoString = "个人奖金-大区负责人已通过";
			break;
		case 4012:
			infoString = "个人奖金-大区负责人已驳回";
			break;
		case 4020:
			infoString = "个人奖金-管理员待审核";
			break;
		case 4021:
			infoString = "个人奖金-管理员已通过";
			break;
		case 4022:
			infoString = "个人奖金-管理员已驳回";
			break;
		case 4030:
			infoString = "个人奖金-BOSS待审核";
			break;
		case 4031:
			infoString = "个人奖金-BOSS已通过";
			break;
		case 4032:
			infoString = "个人奖金-BOSS已驳回";
			break;
		case 4040:
			infoString = "个人奖金-人资待审核";
			break;
		case 4041:
			infoString = "个人奖金-人资已通过";
			break;
		case 4042:
			infoString = "个人奖金-人资已驳回";
			break;
			
		case 9970:
			infoString = "结项审批完成";
			break;
		case 9979:
			infoString = "质保金未收回";
			break;
		case 9980:
			infoString = "质保金收回-财务待审核";
			break;
		case 9982:
			infoString = "质保金收回-财务已驳回";
			break;
		case 9989:
			infoString = "工程已结项-质保金已收回";
			break;
		case 9999:
			infoString = "工程已结项-无质保金";
			break;
			
		default:
			infoString = "NULL";
			break;
		}
		return infoString;
	}

	/**
	 * 用于审批后，发送邮件时应该通知拥有哪些角色的人的判断
	 * @param statue_number 审核状态代码
	 * @return 角色代码,邮箱模板ID
	 * <br/>
	 *1004	弱电-普通立项用户<br/>
	 *1005	弱电-管理员<br/>
	 *1006	弱电-财务<br/>
	 *1007	弱电-人资<br/>
	 *1010	弱电-大区负责人<br/>
	 *1011	弱电-BOSS
	 */
	public static String approvalStatusCodeDetermineWhoNeedsToBeNotified(int statue_number) {
		System.out.println("进入的审核状态代码为："+statue_number);
		String infoString;
		switch (statue_number) {
		case 1010:
			infoString = "1010,1";//"立项-大区负责人待审核";
			break;
		case 1012:
			infoString = "1004,11";//"立项-大区负责人已驳回";
			break;
		case 1020:
			infoString = "1005,1";//"立项-管理员待审核";
			break;
		case 1021:
			infoString = "1004,6";//"立项-已立项";
			break;
		case 1022:
			infoString = "1004,11";//"立项-管理员已驳回";
			break;
			
		case 2010:
			infoString = "1010,2";//"实际开支-大区负责人待审核";
			break;
		case 2012:
			infoString = "1004,12";//"实际开支-大区负责人驳回";
			break;
		case 2020:
			infoString = "1005,2";//"实际开支-管理员待审核";
			break;
		case 2022:
			infoString = "1004,12";//"实际开支-管理员已驳回";
			break;
		case 2030:
			infoString = "1006,2";//"实际开支-财务待审核";
			break;
		case 2032:
			infoString = "1004,12";//"实际开支-财务已驳回";
			break;
		case 2040:
			infoString = "1011,2";//"实际开支-BOSS待审核";
			break;
		case 2041:
			infoString = "1004,7";//"实际开支-BOSS已通过";
			break;
		case 2042:
			infoString = "1004,12";//"实际开支-BOSS已驳回";
			break;

		case 2110:
			infoString = "1010,17";//"付款申请单-大区负责人待审核";
			break;
		case 2112:
			infoString = "1004,19";//"付款申请单-大区负责人驳回";
			break;
		case 2120:
			infoString = "1005,17";//"付款申请单-管理员待审核";
			break;
		case 2122:
			infoString = "1004,19";//"付款申请单-管理员已驳回";
			break;
		case 2130:
			infoString = "1006,17";//"付款申请单-财务待审核";
			break;
		case 2132:
			infoString = "1004,19";//"付款申请单-财务已驳回";
			break;
		case 2140:
			infoString = "1011,17";//"付款申请单-BOSS待审核";
			break;
		case 2141:
			infoString = "1004,18";//"付款申请单-BOSS已通过";
			break;
		case 2142:
			infoString = "1004,19";//"付款申请单-BOSS已驳回";
			break;

		case 2210:
			infoString = "1006,20";//"开票申请单-财务待审核";
			break;
		case 2211:
			infoString = "1004,21";//"开票申请单-财务已通过";
			break;
		case 2212:
			infoString = "1004,22";//"开票申请单-财务已驳回";
			break;

		case 3010:
			infoString = "1010,3";//"申请结项-大区负责人待审核";
			break;
		case 3012:
			infoString = "1004,13";//"申请结项-大区负责人已驳回";
			break;
		case 3020:
			infoString = "1006,3";//"申请结项-财务待审核";
			break;
		case 3022:
			infoString = "1004,13";//"申请结项-财务已驳回";
			break;
		case 3030:
			infoString = "1005,3";//"申请结项-管理员待审核";
			break;
		case 3031:
			infoString = "1004,8";//"申请结项-管理员已通过";
			break;
		case 3032:
			infoString = "1004,13";//"申请结项-管理员已驳回";
			break;

		case 4010:
			infoString = "1010,4";//"个人奖金-大区负责人待审核";
			break;
		case 4012:
			infoString = "1004,14";//"个人奖金-大区负责人已驳回";
			break;
		case 4020:
			infoString = "1005,4";//"个人奖金-管理员待审核";
			break;
		case 4022:
			infoString = "1004,14";//"个人奖金-管理员已驳回";
			break;
		case 4030:
			infoString = "1011,4";//"个人奖金-BOSS待审核";
			break;
		case 4032:
			infoString = "1004,14";//"个人奖金-BOSS已驳回";
			break;
		case 4040:
			infoString = "1007,4";//"个人奖金-人资待审核";
			break;
		case 4041:
			infoString = "1004,9";//"个人奖金-人资已通过";
			break;
		case 4042:
			infoString = "1004,14";//"个人奖金-人资已驳回";
			break;

		case 9979:
			infoString = "1004,16";//"质保金未收回";
			break;
		case 9980:
			infoString = "1006,5";//"质保金收回-财务待审核";
			break;
		case 9982:
			infoString = "1004,15";//"质保金收回-财务已驳回";
			break;
		case 9989:
			infoString = "1004,10";//"工程已结项-质保金已收回";
			break;
		case 9999:
			infoString = "1004,8";//"工程已结项-无质保金";
			break;
			
		default:
			infoString = "NULL,NULL";
			break;
		}
		System.out.println("需要通知的角色代码,邮箱模板ID："+infoString);
		return infoString;
	}
	

}
