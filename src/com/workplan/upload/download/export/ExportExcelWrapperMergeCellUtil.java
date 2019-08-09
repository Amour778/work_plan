package com.workplan.upload.download.export;

import java.net.URLEncoder;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;

/**
 * 包装类
 * 
 * @author liuyazhuang
 * 
 * @param <T>
 */
public class ExportExcelWrapperMergeCellUtil<T> extends ExportExcelMergeCellUtil<T> {
	/**
	 * <p>
	 * 导出带有头部标题行的Excel <br>
	 * 时间格式默认：yyyy-MM-dd hh:mm:ss <br>
	 * </p>
	 * 
	 * @param title
	 *            表格标题
	 * @param headers
	 *            头部标题集合
	 * @param dataset
	 *            数据集合
	 * @param out
	 *            输出流
	 * @param firstCountNum
	 *            一级事项个数
	 * @param firstsValue
	 *            一级事项名称
	 */
	public void exportExcel(String fileName, String title, String[] headers,Collection<T> dataset, HttpServletResponse response, String version) {
		try {
			if (StringUtils.isBlank(version)|| EXCEL_FILE_2003.equals(version.trim())) {
				response.setContentType("application/vnd.ms-excel");
				response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName, "UTF-8")+ ".xls");
				exportExcel2003MergeCell(title, headers, response.getOutputStream(), dataset);
			} else {
				response.setContentType("application/vnd.ms-excel");
				response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName, "UTF-8")+ ".xlsx");
				exportExcel2007MergeCell(title, headers, response.getOutputStream(), dataset);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exportExcel2007MergeCellShnett_EdianzuBean(String fileName, String title, String[] headers,Collection<T> dataset, HttpServletResponse response, String version) {
		try {
				response.setContentType("application/vnd.ms-excel");
				response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName, "UTF-8")+ ".xlsx");
				exportExcel2007MergeCellShnett_EdianzuBean(title, headers, response.getOutputStream(), dataset);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
