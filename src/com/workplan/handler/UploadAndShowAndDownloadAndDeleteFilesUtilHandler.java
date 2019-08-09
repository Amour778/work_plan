package com.workplan.handler;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.workplan.tools.FileUtils;
import com.workplan.tools.GetDateTimeNow;
import com.workplan.tools.ResultMessage;
import com.workplan.tools.UploadPicUtil;

@Controller
public class UploadAndShowAndDownloadAndDeleteFilesUtilHandler {

	private static String FilesLocalSRC = "WEB-INF/Files_Upload/";
	private static String FilesZipLocalSRC = "ZipTemp";

	/**
	 * 上传文件：弱电项目专用
	 * 
	 * @param request
	 * @param system
	 *            对应系统编码，例如弱电为:wcp
	 * @param project_code
	 *            对应弱电项目唯一的编码，前端传过来的值为“项目编码_进度”
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/doUploadFiles/{system}/{project_code}", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String doUploadFiles(HttpServletRequest request,
			@PathVariable("system") String system,
			@PathVariable("project_code") String project_code,
			HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		String path = project_code.split("_")[0];
		Map<String, Object> uploadFile = UploadPicUtil.uploadFile(request,system, path,project_code);
		if ((Boolean) uploadFile.get("state")) {
			//System.out.println("上传的文件路径为：" + path + "\\"+ uploadFile.get("filename"));
			return ResultMessage.MessageToJson(0, uploadFile.get("filename")
					.toString());
		} else {
			return ResultMessage.MessageToJson(1, "上传失败！");
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param request
	 * @param system
	 * @param project_code
	 * @param project_progress
	 * @param suffix
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/doDeleteFiles/{system}", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String doDeleteFiles(HttpServletRequest request, @PathVariable("system") String system,
			String project_code, String file_name) {
		try {
			request.setCharacterEncoding("utf-8"); // 设置编码
			String fileName = request.getSession().getServletContext().getRealPath(FilesLocalSRC)+ "\\" + system + "\\" + project_code + "\\" + file_name;
			System.out.println("删除文件，文件路径为："+fileName);
			File file = new File(fileName);
			if (!file.exists()) {
				System.out.println("删除文件失败:" + fileName + "不存在！");
				return ResultMessage.MessageToJson(1, "删除文件失败:" + file_name
						+ "不存在！");
			} else {
				if (deleteFile(fileName)) {
					return ResultMessage.MessageToJson(0, "删除成功！");
				} else {
					return ResultMessage.MessageToJson(1, "删除失败！未知错误");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			return ResultMessage.MessageToJson(1, "删除失败：" + e.getMessage());
		}

	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				System.out.println("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				System.out.println("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			System.out.println("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}

	/**
	 * 列举文件夹下的文件
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/showFilesLists/{system}", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String showFilesLists(HttpServletRequest request, 
			@PathVariable("system") String system, 
			String project_code,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		FileUtils fileUtils = new FileUtils();
		
		String filePath = FilesLocalSRC + "/" + system+ "/" + project_code;
		System.out.println("列举文件夹下的文件，文件夹路径为："+filePath);
		String uploadFilePath = request.getSession().getServletContext().getRealPath(filePath);
		File file =new File(uploadFilePath);
		System.out.println("判断此上传文件的路径是否存在:"+file.getPath());
		//如果文件夹不存在则创建    
		if(!file.exists()&&!file.isDirectory())
		{       
		    file.mkdirs();// mkdir()创建此抽象路径名称指定的目录（及只能创建一级的目录，且需要存在父目录）
		    			//mkdirs()创建此抽象路径指定的目录，包括所有必须但不存在的父目录。（及可以创建多级目录，无论是否存在父目录）
		    System.out.println("//不存在，创建");  
		} else   
		{  
		    System.out.println("//目录存在");  
		}
		// 获取符合条件的文件searchFilesReturnList
		List<String> result = fileUtils.searchFilesReturnList(request,response,filePath , project_code);
		String dataString = "";
		Map<String, String> fileNameMap = new HashMap<String, String>();
		for (int i = 0; i < result.size(); i++) {// 循环显示文件
			System.out.println("{\"key\":\"name\",\"domnload_url\":"
					+ result.get(i) + "},");
			dataString = dataString + "{\"name\":\"" + result.get(i)
					+ "\",\"domnload_url\":\"" + result.get(i) + "\"},";
		}
		try {
			dataString = "[" + dataString.substring(0, dataString.length() - 1)
					+ "]";
			dataString = "{\"code\": \"0\",\"msg\": \"成功\",\"count\":\""
					+ fileNameMap.size() + "\",\"data\": " + dataString + "}";
		} catch (Exception e) {
			dataString = "{\"code\": \"1\",\"msg\": \"无数据\",\"count\":\"0\",\"data\": \"\"}";
		}

		System.out.println(dataString);
		// response.getWriter().print(Message);
		return dataString;
	}

	/**
	 * 下载文件：中文乱码的问题，使用多重判断文件是否存在解决
	 * 先判断接收的文件名是否存在，如果不存在，再判断转码后的文件名是否存在
	 * 如果都不存在，则判断文件不存在
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/downLoadFilesLists/{system}/{project_code}", produces = "text/html;charset=UTF-8")
	public void downLoadFile(HttpServletRequest request, 
			@PathVariable("system") String system,
			@PathVariable("project_code") String project_code,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String fileName = request.getParameter("filename");
		System.out.println("下载文件：后台接受到的fileName=" + fileName);
		FileUtils fileUtils = new FileUtils();
		String FilePath=request.getSession().getServletContext().getRealPath(FilesLocalSRC)+ "\\" + system+"\\"+project_code;
		  try {
	            fileUtils.downloadSolve(FilePath,fileName,request,response);
	        }catch (ServletException e){
	            e.printStackTrace();
		    	response.getWriter().print(e.getMessage());
	        }catch (IOException e){
	            e.printStackTrace();
		    	response.getWriter().print(e.getMessage());
	        }
	}

	/**
	 * 下载某个目录下所有文件压缩后的压缩文件
	 * @param request
	 * @param system 系统代码
	 * @param project_code 项目唯一编码
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/downLoadZipFiles/{system}/{project_code}", produces = "text/html;charset=UTF-8")
	public void downLoadZipFiles(HttpServletRequest request, 
			@PathVariable("system") String system,
			@PathVariable("project_code") String project_code,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		FileUtils fileUtils = new FileUtils();
		String sourceFilePath=request.getSession().getServletContext().getRealPath(FilesLocalSRC)+ "\\" + system+"\\"+project_code;
		String zipFilePath=request.getSession().getServletContext().getRealPath(FilesLocalSRC)+ "\\" + system+"\\"+FilesZipLocalSRC;
		String fileName=project_code+"_"+GetDateTimeNow.getDateTimeRandomToID();
		FileUtils.fileToZipAndDownload(sourceFilePath, zipFilePath, fileName, request, response);
	}

}
