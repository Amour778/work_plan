package com.workplan.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileUtils {
	static int countFiles = 0;// 声明统计文件个数的变量
	static int countFolders = 0;// 声明统计文件夹的变量

	/**
	 * 
	 * @param request
	 * @param response
	 * @param filePath 例如弱电项目上传的文件路径为：Files_Upload/wcp/
	 * @param keyword 关键字，用于搜索符合条件的文件
	 * @return
	 */
	public List<String> searchFilesReturnList(HttpServletRequest request, HttpServletResponse response,String filePath,String keyword) {
		// TODO Auto-generated method stub
		String uploadFilePath = request.getSession().getServletContext().getRealPath(filePath);
		File folder = new File(uploadFilePath);
		creatFile(uploadFilePath);
		List<String> result = searchFileTiList(folder, keyword);// 调用方法获得文件数组
		System.out.println("在 " + folder + " 以及所有子文件时查找对象" + keyword);
		System.out.println("查找了" + countFiles + " 个文件，" + countFolders
				+ " 个文件夹，共找到 " + result.size() + " 个符合条件的文件：");
		for (int i = 0; i < result.size(); i++) {// 循环显示文件
			System.out.println(result.get(i));// 显示文件绝对路径
        }
		return result;
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
	 * 递归查找包含关键字的文件：方法中注释了该目录下文件夹中文件的查找
	 * @param folder
	 * @param keyWord
	 * @return
	 */
	public static  List<String> searchFileTiList(File folder, final String keyWord) {// 递归查找包含关键字的文件
		File[] subFolders = folder.listFiles(new FileFilter() {// 运用内部匿名类获得文件
					public boolean accept(File pathname) {// 实现FileFilter类的accept方法
						if (pathname.isFile()){// 如果是文件
							countFiles++;
						}else{
							// 如果是目录
							countFolders++;
						}
						if (pathname.isDirectory()
								|| (pathname.isFile() && pathname.getName()
										.toLowerCase().contains(
												keyWord.toLowerCase())))// 目录或文件包含关键字
							return true;
						return false;
					}
				});

		List<String> result = new ArrayList<String>();// 声明一个集合
		for (int i = 0; i < subFolders.length; i++) {// 循环显示文件夹或文件
			if (subFolders[i].isFile()) {// 如果是文件则将文件添加到结果列表中
				result.add(subFolders[i].getName());
			}
			/*
			 else {// 如果是文件夹，则递归调用本方法，然后把所有的文件加到结果列表中
					File[] foldResult = searchFile(subFolders[i], keyWord);
					for (int j = 0; j < foldResult.length; j++) {// 循环显示文件
						result.add(foldResult[j]);// 文件保存到集合中
					}
				}
			*/
		}
		return result;
	}
	
	
	
	/**
	 * 下载的实际方法
	 * @param FilePath
	 * @param fileName
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	  public void downloadSolve(String FilePath,String fileName,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  	String fileSaveRootPath = FilePath;
		    String path = fileSaveRootPath + "\\" + fileName;
		    System.out.println("下载的路径为path=" + path);
		    File file = new File(path);
		    if (!(file.exists())) {
		    	fileName =  new String(fileName.getBytes("ISO8859-1"), "UTF-8");
		    	path = fileSaveRootPath + "\\" + fileName;
			    System.out.println("上面的路径不存在，转码后下载的路径为path=" + path);
		    	file = new File(path);
		    	  if (!(file.exists())) {
			    	String Message = "{\"msg\": \"文件"+fileName+"不存在\"}";
			    	return ;
		    	  }
		    }
	        FileInputStream fileInputStream = new FileInputStream(file);
	        //设置Http响应头告诉浏览器下载这个附件
	        response.setHeader("Content-Disposition", "attachment;Filename=" + URLEncoder.encode(fileName, "UTF-8"));
	        //response.setHeader("Content-Disposition", "attachment;Filename=" + fileName);
	        OutputStream outputStream = response.getOutputStream();
	        byte[] bytes = new byte[2048];
	        int len = 0;
	        while ((len = fileInputStream.read(bytes))>0){
	            outputStream.write(bytes,0,len);
	        }
	        fileInputStream.close();
	        outputStream.close();
	    }
	  
	  
	  /**
	   * 压缩指定目录下的文件并下载
	   * @param sourceFilePath 需要压缩的目录
	   * @param zipFilePath 文件压缩后存放的目录(ZIP存放的目录)
	   * @param fileName 压缩包的名称
	   */
	  public static void fileToZipAndDownload(String sourceFilePath,String zipFilePath,String fileName,HttpServletRequest request, HttpServletResponse response){
		  System.out.println("sourceFilePath 需要压缩的目录："+sourceFilePath);
		  System.out.println("zipFilePath 文件压缩后存放的目录(ZIP存放的目录)："+zipFilePath);
		  System.out.println("fileName 压缩包的名称："+fileName);
		  creatFile(zipFilePath);
          boolean flag = fileToZip(sourceFilePath, zipFilePath, fileName);  
          if(flag){
              System.out.println("文件打包成功!");  
      		  try {
                  FileUtils fileUtils = new FileUtils();
      	          fileUtils.downloadSolve(zipFilePath, fileName+".zip",request,response);
      		  }catch (Exception e) {
				e.printStackTrace();
			}
          }else{
              System.out.println("文件打包失败!");  
          }  
      }
	  
	  /**
	   * 打包某个目录下的所有文件 
	   * @param sourceFilePath
	   * @param zipFilePath
	   * @param fileName
	   * @return
	   */
      public static boolean fileToZip(String sourceFilePath,String zipFilePath,String fileName){  
          boolean flag = false;  
          File sourceFile = new File(sourceFilePath);  
          FileInputStream fis = null;  
          BufferedInputStream bis = null;  
          FileOutputStream fos = null;  
          ZipOutputStream zos = null;  

          if(sourceFile.exists() == false){  
              System.out.println("待压缩的文件目录："+sourceFilePath+"不存在.");  
          }else{  
              try {  
                  File zipFile = new File(zipFilePath + "/" + fileName +".zip");  
                  if(zipFile.exists()){  
                      System.out.println(zipFilePath + "目录下存在名字为:" + fileName +".zip" +"打包文件.");  
                  }else{  
                      File[] sourceFiles = sourceFile.listFiles();  
                      if(null == sourceFiles || sourceFiles.length<1){  
                          System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");  
                      }else{  
                          fos = new FileOutputStream(zipFile);  
                          zos = new ZipOutputStream(new BufferedOutputStream(fos));  
                          byte[] bufs = new byte[1024*10];  
                          for(int i=0;i<sourceFiles.length;i++){  
                              //创建ZIP实体，并添加进压缩包  
                              ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());  
                              zos.putNextEntry(zipEntry);  
                              //读取待压缩的文件并写进压缩包里  
                              fis = new FileInputStream(sourceFiles[i]);  
                              bis = new BufferedInputStream(fis, 1024*10);  
                              int read = 0;  
                              while((read=bis.read(bufs, 0, 1024*10)) != -1){  
                                  zos.write(bufs,0,read);  
                              }  
                          }  
                          flag = true;  
                      }  
                  }  
              } catch (FileNotFoundException e) {  
                  e.printStackTrace();  
                  throw new RuntimeException(e);  
              } catch (IOException e) {  
                  e.printStackTrace();  
                  throw new RuntimeException(e);  
              } finally{  
                  //关闭流  
                  try {  
                      if(null != bis) bis.close();  
                      if(null != zos) zos.close();  
                  } catch (IOException e) {  
                      e.printStackTrace();  
                      throw new RuntimeException(e);  
                  }  
              }  
          }  
          return flag;  
      }
	  
	  
}