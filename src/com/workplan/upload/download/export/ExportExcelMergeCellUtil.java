package com.workplan.upload.download.export;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.workplan.bean.Shnett_EdianzuBean;
import com.workplan.bean.UserInfoBean;
import com.workplan.bean.WorkItemSecondSimpleBean;
import com.workplan.dao.UserInfoDao;

public class ExportExcelMergeCellUtil<T> {
	
	// 2007 版本以上 最大支持1048576行
	public  final static String  EXCEl_FILE_2007 = "2007";
	// 2003 版本 最大支持65536 行
	public  final static String  EXCEL_FILE_2003 = "2003";
	
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	/**
	 * <p>
	 * 导出无头部标题行Excel <br>
	 * 时间格式默认：yyyy-MM-dd hh:mm:ss <br>
	 * </p>
	 * 
	 * @param title 表格标题
	 * @param dataset 数据集合
	 * @param out 输出流
	 * @param version 2003 或者 2007，不传时默认生成2003版本
	
	public void exportExcel(String title, Collection<T> dataset, OutputStream out, String version) {
		if(StringUtils.isEmpty(version) || EXCEL_FILE_2003.equals(version.trim())){
			exportExcel2003MergeCell(title, null, out, dataset, "yyyy-MM-dd hh:mm:ss");
		}else{
			exportExcel2007MergeCell(title, null, out, dataset, "yyyy-MM-dd hh:mm:ss");
		}
	}
  */
	/**
	 * <p>
	 * 导出带有头部标题行的Excel <br>
	 * 时间格式默认：yyyy-MM-dd hh:mm:ss <br>
	 * </p>
	 * 
	 * @param title 表格标题
	 * @param headers 头部标题集合
	 * @param dataset 数据集合
	 * @param out 输出流
	 * @param version 2003 或者 2007，不传时默认生成2003版本
	 
	public void exportExcel(String title,String[] headers, Collection<T> dataset, OutputStream out,String version) {
		if(StringUtils.isBlank(version) || EXCEL_FILE_2003.equals(version.trim())){
			exportExcel2003MergeCell(title, headers, out, dataset, "yyyy-MM-dd hh:mm:ss");
		}else{
			exportExcel2007MergeCell(title, headers, out, dataset, "yyyy-MM-dd hh:mm:ss");
		}
	}
 
*/
	/**
	 * 合并单元格导出
	 * @param args
	 */
	
	/*
	 * 1.获取一级事项
	 * 2.获取一级事项对应的二级事项：二级事项数量决定一级事项单元格向下合并的行数
	 * //创建workbook
	 * HSSFWorkbook workbook = new HSSFWorkbook(); 
	 * //创建sheet页
	 * HSSFSheet sheet = workbook.createSheet("学生表"); 
	 * Region region1 = new Region(0, (short)0, 1, (short)0);
	 * sheet.addMergedRegion(region1);
	 * Region region1 = new Region(rowFrom, colFrom, rowTo, colTo)
	 * 参数			说明
	 * rowFrom		合并单元格的起始行（POI中row的标号）
	 * colFrom		合并单元格的起始列（POI中row的标号）
	 * rowTo		合并单元格的结束行（POI中column的标号）
	 * colTo		合并单元格的结束列（POI中column的标号）
	 * 
	 * 3.获取二级事项对应的进度
	 * 
	 */

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void exportExcel2003MergeCell(String title, String[] headers, OutputStream out,Collection<T> dataset) {
		// 声明一个工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 生成一个表格
		XSSFSheet sheet = workbook.createSheet(title);
		
		// 产生表格标题行
		XSSFRow row = sheet.createRow(0);
		XSSFCell cellHeader;
		for (int i = 0; i < headers.length; i++) {
			cellHeader = row.createCell(i);
			
			cellHeader.setCellValue(new XSSFRichTextString(headers[i]));
		}
		try {
            // 生成第二行之后的内容
            int i = 1;
            for (Object object : dataset) {
            	WorkItemSecondSimpleBean orderDto = (WorkItemSecondSimpleBean) object;
                // 创建一行
                Row row2 = sheet.createRow(i++);
                row2.createCell(0).setCellValue(orderDto.getSuperior_item_name());
                row2.createCell(1).setCellValue(orderDto.getItemName());
                row2.createCell(2).setCellValue(orderDto.getOrganization());
                row2.createCell(3).setCellValue(orderDto.getWorkContent());
            }
            ByteArrayOutputStream outPut = new ByteArrayOutputStream();
            workbook.write(outPut);
            byte[] b = outPut.toByteArray();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(b);
            int bytesRead = 0;
            // 用输出流去写，缓冲输入输出流
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            
        }
	}

	

	public void exportExcel2007MergeCell(String title, String[] headers, OutputStream out,Collection<T> dataset) {
		// 声明一个工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		System.out.println("  /*/  声明一个工作薄");
		// 生成一个表格
		XSSFSheet sheet = workbook.createSheet(title);
		System.out.println("  /*/  生成一个表格");

		//文字颜色-纯白
		XSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.WHITE.index);
		
		// “未开始”单元格样式
		XSSFCellStyle style_1 = workbook.createCellStyle();
		style_1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style_1.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style_1.setFont(font);
		System.out.println("  /*/  “未开始”单元格样式");
		
		// “进行中”单元格样式
		XSSFCellStyle style0 = workbook.createCellStyle();
		style0.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		style0.setFillPattern(CellStyle.SOLID_FOREGROUND);
		System.out.println("  /*/  “进行中”单元格样式");
		
		// “已完成”单元格样式
		XSSFCellStyle style1 = workbook.createCellStyle();
		style1.setFillForegroundColor(IndexedColors.TEAL.getIndex());
		style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style1.setFont(font);
		System.out.println("  /*/  “已完成”单元格样式");
		
		// 产生表格标题行
		XSSFRow row = sheet.createRow(0);
		System.out.println("  /*/  产生表格标题行");
		XSSFCell cellHeader;
		for (int i = 0; i < headers.length; i++) {
			cellHeader = row.createCell(i);
			cellHeader.setCellValue(new XSSFRichTextString(headers[i]));
		}
		System.out.println("  /*/  标题行产生完成");
		try {
			System.out.println("  /*/  生成第二行之后的内容");
            // 生成第二行之后的内容
            int i = 1;
           
            for (Object object : dataset) {
            	WorkItemSecondSimpleBean orderDto = (WorkItemSecondSimpleBean) object;
                // 创建一行
            	
                Row row2 = sheet.createRow(i++);
                row2.createCell(0).setCellValue(orderDto.getSuperior_item_name());//一级事项名称
                row2.createCell(1).setCellValue(orderDto.getItemName());//二级事项名称
                row2.createCell(2).setCellValue(orderDto.getOrganization());//组织
                row2.createCell(3).setCellValue(orderDto.getWorkContent());//工作内容
                if(orderDto.getStep().replaceAll(" ", "").equals("N")) {//步骤
                	row2.createCell(4).setCellValue("");
                }else {
                	row2.createCell(4).setCellValue(orderDto.getStep());
				}
                row2.createCell(5).setCellValue(orderDto.getCompletionCycle());//完成周期
                row2.createCell(6).setCellValue(orderDto.getPersonLiable());//责任人
                row2.createCell(7).setCellValue(orderDto.getPartner());//配合人
                row2.createCell(8).setCellValue(orderDto.getStartTime());//开始时间
                row2.createCell(9).setCellValue(orderDto.getCompletionTime());//完成时间

				Cell cell = row2.createCell(10);//状态
                switch (orderDto.getState()) {
				case -1:
					 cell.setCellValue("未开始");
					 cell.setCellStyle(style_1);
					break;

				case 0:
					 cell.setCellValue("进行中");
					 cell.setCellStyle(style0);
					break;

				case 1:
					 cell.setCellValue("已完成");
					 cell.setCellStyle(style1);
					break;
				default:
					break;
				}
                row2.createCell(11).setCellValue(orderDto.getProgress());//进度
                //row2.createCell(12).setCellValue(orderDto.getItemId());
            }
            ByteArrayOutputStream outPut = new ByteArrayOutputStream();
            workbook.write(outPut);
            byte[] b = outPut.toByteArray();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(b);
            int bytesRead = 0;
            // 用输出流去写，缓冲输入输出流
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
	}
	public void exportExcel2007MergeCellShnett_EdianzuBean(String title, String[] headers, OutputStream out,Collection<T> dataset) {
		// 声明一个工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		System.out.println("  /*/  声明一个工作薄");
		// 生成一个表格
		XSSFSheet sheet = workbook.createSheet(title);
		System.out.println("  /*/  生成一个表格");

		// 产生表格标题行
		XSSFRow row = sheet.createRow(0);
		System.out.println("  /*/  产生表格标题行");
		XSSFCell cellHeader;
		for (int i = 0; i < headers.length; i++) {
			cellHeader = row.createCell(i);
			cellHeader.setCellValue(new XSSFRichTextString(headers[i]));
		}
		System.out.println("  /*/  标题行产生完成");
		try {
			System.out.println("  /*/  生成第二行之后的内容");
            // 生成第二行之后的内容
            int i = 1;

            UserInfoDao userDao = (UserInfoDao) context.getBean("UserInfoDao");

    		List<UserInfoBean> userList = new ArrayList<UserInfoBean>();
    		
            for (Object object : dataset) {
            	Shnett_EdianzuBean orderDto = (Shnett_EdianzuBean) object;
                // 创建一行
                Row row2 = sheet.createRow(i++);
                row2.createCell(0).setCellValue(orderDto.getEvent_type());//上门类型
                row2.createCell(1).setCellValue(orderDto.getStartDate());//到场日期
                row2.createCell(2).setCellValue(orderDto.getStartTime());//到场时间
                row2.createCell(3).setCellValue(orderDto.getEndDate());//完成日期
                row2.createCell(4).setCellValue(orderDto.getEndTime());//完成时间
                row2.createCell(5).setCellValue(orderDto.getCompany_name());//公司名称
                row2.createCell(6).setCellValue(orderDto.getRegion_province()+orderDto.getRegion_city()+orderDto.getRegion_area());//省市区
                row2.createCell(7).setCellValue(orderDto.getCompany_address());//公司地址
                row2.createCell(8).setCellValue(orderDto.getCompany_contacts());//公司联系人
                row2.createCell(9).setCellValue(orderDto.getCompany_contact_phone());//公司联系人联系电话
                row2.createCell(10).setCellValue(orderDto.getOrders_number());//单子数量
                row2.createCell(11).setCellValue(orderDto.getCourier_number());//租返单号
                row2.createCell(12).setCellValue(orderDto.getNumber_of_inspection());//巡检设备数量
                row2.createCell(13).setCellValue(orderDto.getCar_fare().toString());//交通费
                row2.createCell(14).setCellValue(orderDto.getInsert_time());//录入时间
                row2.createCell(15).setCellValue(orderDto.getInsert_userid());//录入人工号
                try {
                    userList=userDao.querySimpleUserSimpleInfoByUserId(orderDto.getInsert_userid());
                	row2.createCell(16).setCellValue(userList.get(0).getUser_name());//录入人姓名
                    row2.createCell(17).setCellValue(userList.get(0).getUser_area_wechat_edianzu());//录入人地区
				} catch (Exception e) {
					// TODO: handle exception
					row2.createCell(16).setCellValue("");//录入人姓名
	                row2.createCell(17).setCellValue("");//录入人地区
				}
                
            }
            ByteArrayOutputStream outPut = new ByteArrayOutputStream();
            workbook.write(outPut);
            byte[] b = outPut.toByteArray();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(b);
            int bytesRead = 0;
            // 用输出流去写，缓冲输入输出流
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
	}
}
