package com.workplan.handler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoHandler {
	/**
	 * 用户选择添加一级事项并添加相关数据
	 * 
	 * @param title
	 * @return
	 */
	@RequestMapping(value = "/getDemoData", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getDemoData() {
		System.out.println("D:\\user\\01059101\\Workspaces\\MyEclipse 8.5\\work_plan\\src\\json.json");
		String returnString = ReadFile("D:\\user\\01059101\\Workspaces\\MyEclipse 8.5\\work_plan\\src\\json.json");
		return returnString;
	}

	public String ReadFile(String Path) {
		BufferedReader reader = null;
		String laststr = "";
		try {
			FileInputStream fileInputStream = new FileInputStream(Path);
			InputStreamReader inputStreamReader = new InputStreamReader(
					fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				laststr += tempString;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return laststr;
	}

}
