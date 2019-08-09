package com.workplan.upload.download.export;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aliyuncs.exceptions.ClientException;
import com.workplan.bean.UserInfoBean;
import com.workplan.dao.UserInfoDao;
import com.workplan.tools.ExcelVersionUtil;
import com.workplan.tools.GeneratePasswordUtil;
import com.workplan.tools.SHA1Util;
import com.workplan.tools.SendShortMessage;

public class uploadUserInfo {

	SendShortMessage SendShortMessage = new SendShortMessage();//短信
	public String upload(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
	    String responseString = "";

	    InputStream is = null;
	    String savePath = request.getSession().getServletContext().getRealPath("WEB-INF/Excel_Files_Upload/"+Time()+"/");
	    System.out.println(savePath);
	    File saveFileDir = new File(savePath);
	    if (!(saveFileDir.exists()))
	    {
	      saveFileDir.mkdirs();
	    }
	    String tmpPath = request.getSession().getServletContext().getRealPath("WEB-INF/Excel_Files_Upload/temp");
	    File tmpFile = new File(tmpPath);
	    if (!(tmpFile.exists()))
	    {
	      tmpFile.mkdirs();
	    }
		int Errorrow_Num=0;//数据catch行
		String SheetName =null;//工作表名称
	      try {
	        DiskFileItemFactory factory = new DiskFileItemFactory();
	        factory.setSizeThreshold(10240);
	        factory.setRepository(tmpFile);
	        ServletFileUpload upload = new ServletFileUpload(factory);
	        upload.setHeaderEncoding("UTF-8");
	        if (!(ServletFileUpload.isMultipartContent(request)))
	        {
	        	responseString="{\"code\":1,\"info\":\"未知错误\"}";
	        }
	        List<FileItem> items = upload.parseRequest(request);
	        Iterator<FileItem> itr = items.iterator();

	        String saveFileName = null;

	        while (itr.hasNext()) {
	          FileItem item = itr.next();
	          String value;
	          if (item.isFormField()) {
	            String name = item.getFieldName();
	            value = item.getString("UTF-8");
	          }
	          else
	          {
	            String fileName = item.getName();
	            if ((fileName == null) && (fileName.trim().length() == 0))
	            {
	              continue;
	            }
	            fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
	            String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	            //String TIME = Com_id();
	            saveFileName =  Com_id() +fileName;// + TIME;
	            InputStream in = item.getInputStream();
	            FileOutputStream out = new FileOutputStream(savePath + "\\" + saveFileName);
	            byte[] buffer = new byte[1024];
	            int len = 0;
	            while ((len = in.read(buffer)) > 0) {
	              out.write(buffer, 0, len);
	            }
	            out.close();
	            in.close();
	            item.delete();
	          }
	        }
	        String filepath =  savePath + "\\"+ saveFileName;
	        System.out.println("表格是2003："+ExcelVersionUtil.isExcel2003(filepath));
	        System.out.println("表格是2007："+ExcelVersionUtil.isExcel2007(filepath));
	        FileInputStream fis = new FileInputStream(filepath);
	        Workbook workbook = null;
			if(ExcelVersionUtil.isExcel2007(filepath)){
			workbook = new XSSFWorkbook(fis);
			}else {
			workbook = new HSSFWorkbook(fis);
			}
			ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
       	  	UserInfoDao dao = (UserInfoDao) context.getBean("UserInfoDao");
       	  	List<UserInfoBean> userlist=new ArrayList<UserInfoBean>();
       	  	Map<String,String> TelAndPasswordListMap=new HashMap<String,String>();
			// 循环工作表Sheet
       	  	int add_Num=0;//添加到list中的数据总行数
			int row_Num=0;//数据总行数
			//FOR语句对number有干扰，暂时没时间改BUG，故先去掉
			//for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
				Sheet sheetAt = workbook.getSheetAt(0);
				SheetName = sheetAt.getSheetName();
				if (sheetAt == null) {
					responseString += "工作表【"+SheetName+"】数据为空<br/>";
				}
				if((sheetAt.getRow(0).getCell(0).equals("用户工号"))){
					System.out.println("工作表【"+SheetName+"】的第一个单元格不是'用户工号'，故判断为非用户信息导入表，不予导入:"+sheetAt.getRow(0).getCell(0));
					responseString += "工作表【"+SheetName+"】的第一个单元格名字不是'用户工号',不予导入<br/>";
				}
				// 循环行Row
				for (int rowNum = 1; rowNum <= sheetAt.getLastRowNum(); rowNum++) {
					System.out.println("rowNum="+rowNum);
					Errorrow_Num=rowNum;
					Row row = sheetAt.getRow(rowNum);
					if (row != null) {
						row_Num++;
						//定义变量
						String user_id =null;
						String user_name =null;
						String user_tel =null;
						String user_area_wechat_edianzu =null;
						String wcp_area =null;
						String wcp_head =null;
						
						Cell numCell = row.getCell(0);
						// 工号
						if (numCell != null) {
							numCell.setCellType(Cell.CELL_TYPE_STRING);
							user_id = numCell.getStringCellValue();
							user_id = ToEigthLength(user_id);
							System.out.println(user_id);
							if (!(dao.querySimpleUserSimpleInfoByUserId(user_id)
									.size() == 0)) {
								responseString += "工作表【"+SheetName+"】第" + rowNum + "行工号已存在<br/>";
								continue;
							}
						}else {
							responseString += "工作表【"+SheetName+"】第" + rowNum + "行工号为空<br/>";
							continue;
						}
						

						// 姓名
						numCell = row.getCell(1);
						if (numCell != null) {
							numCell.setCellType(Cell.CELL_TYPE_STRING);
							user_name = numCell.getStringCellValue();
							System.out.println(user_name);
						}else {
							responseString += "工作表【"+SheetName+"】第" + rowNum + "行姓名为空<br/>";
							continue;
						}
						// 手机号
						numCell = row.getCell(2);
						if (numCell != null) {
							numCell.setCellType(Cell.CELL_TYPE_STRING);
							user_tel = numCell.getStringCellValue();
							System.out.println(user_tel);
							if (!(user_tel.length() == 11)) {
								responseString += "工作表【"+SheetName+"】第" + rowNum + "行手机号码有误<br/>";
								continue;
							}
						}else {
							responseString += "工作表【"+SheetName+"】第" + rowNum + "行手机号码为空<br/>";
							continue;
						}

						// 易点租地区
						numCell = row.getCell(3);
						if (numCell != null) {
							numCell.setCellType(Cell.CELL_TYPE_STRING);
							user_area_wechat_edianzu= numCell.getStringCellValue();
							System.out.println(user_area_wechat_edianzu);
						}else {
							responseString += "工作表【"+SheetName+"】第" + rowNum + "行易点租地区为空<br/>";
							continue;
						}


						// 弱电项目-所属大区
						numCell = row.getCell(4);
						if (numCell != null) {
							numCell.setCellType(Cell.CELL_TYPE_STRING);
							wcp_area= numCell.getStringCellValue();
							System.out.println(wcp_area);
						}else {
							responseString += "工作表【"+SheetName+"】第" + rowNum + "行弱电项目-所属大区为空<br/>";
							continue;
						}
						numCell = row.getCell(5);
						if (numCell != null) {
							numCell.setCellType(Cell.CELL_TYPE_STRING);
							wcp_head= numCell.getStringCellValue();
							System.out.println(wcp_head);
						}else {
							responseString += "工作表【"+SheetName+"】第" + rowNum + "行弱电项目-是否大区负责人为空<br/>";
							continue;
						}
						

						//默认随机生成，共6位，通过短信发送给用户的手机号
						String user_password=GeneratePasswordUtil.generatePassword();
						//插入到map中
						TelAndPasswordListMap.put(user_tel, user_password);
						//默认角色：default_role，ID = > 1002
						String user_role="1002";
						UserInfoBean ad = new UserInfoBean();
						ad.setUser_id(user_id);
						ad.setUser_password(SHA1Util.hex_sha1(user_password));
						ad.setUser_name(user_name);
						ad.setUser_tel(user_tel);
						ad.setUser_role(user_role);
						ad.setUser_area_wechat_edianzu(user_area_wechat_edianzu);
						ad.setWcp_area(wcp_area);
						ad.setWcp_head(wcp_head);
						userlist.add(ad);
						add_Num++;
					}
				}
			//}
			File file2 = new File(filepath);
			file2.delete();
			if(add_Num==row_Num){
				if(dao.batchInsertUser(userlist)==0){
					int reCode=0;
					String reInfo="";
					for (String key : TelAndPasswordListMap.keySet()) {
						try {
							String tempString="";
							tempString=SendShortMessage.sendShortMessage(key, TelAndPasswordListMap.get(key),"GeneratePasswordTemplateCode");
							if(tempString=="0"){
							}else{
								reCode++;
								reInfo+="手机号为："+key+"的用户添加成功，但是发送密码时出错："+tempString;
							}
						} catch (ClientException e) {
							System.out.println("手机号为："+key+"的用户添加成功，但是发送密码时出错:"+e.getMessage());
							e.printStackTrace();
						}
						if(reCode==0){
							 responseString="{\"code\": 0,\"info\":\"上传成功，请查看短信获取密码\"}";
						}else{
							 responseString="{\"code\": 1,\"info\":\""+reInfo+"\"}";
						}
					}
				}else {
					 responseString="{\"code\": 1,\"info\":\"上传失败\"}";
				}
			}else {
				responseString="{\"code\": 1,\"info\":\"" + responseString + "\"}";
			}
			
	      } catch (FileUploadBase.FileSizeLimitExceededException e) {
				responseString="{\"code\": 1,\"info\":" + responseString + e.getMessage()+"\"}";
	      } catch (FileUploadException e1) {
				responseString="{\"code\": 1,\"info\":\"" + responseString +"工作表【"+SheetName+"】"+e1.getMessage()+ "\"}";
	      } catch (Exception e2) {
				responseString="{\"code\": 1,\"info\":\"" + responseString+"工作表【"+SheetName+"】第"+Errorrow_Num+"行未知错误，excel数据貌似有问题:"+e2.getMessage() + "\"}";
	      }
	    System.out.println("上传返回值：" + responseString);
		return responseString;
	}


	public String Com_id() {
		int max = 9999;
		int min = 1000;
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = df.format(new Date());
		return date + s;
	}

	public String Time() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String date = df.format(new Date());
		return date;
	}

	/**
	 * 工号长度补全：8位
	 * @param value
	 * @return
	 */
	public String ToEigthLength(String value) {
		int length = value.length();
		for (int i = 0; i < 8-length; i++) {
			value = "0" + value;
		}
		return value;
	}
}
