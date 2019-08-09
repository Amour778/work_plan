package com.workplan.tools;

import java.util.List;

import net.sf.json.JSONArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * JSON数据相关操作
 * 
 * @author 01059101
 * 
 */
public class GsonUtil {
	public static void main(String[] args) {
		/*
		 * String jsonData =
		 * "{'name':'John', 'age':20,'grade':{'course':'English','score':100,'level':'A'}}"
		 * ; Student student = GsonUtil.parseJsonWithGson(jsonData,
		 * Student.class); System.out.println(student);
		 */
	}

	public static String List2Json(List<?> list) {
		JSONArray json = JSONArray.fromObject(list);
		return json.toString();
	}

	// 一条json数据
	public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
		Gson gson = new Gson();
		T result = gson.fromJson(jsonData, type);
		return result;
	}

	// 将Json数组解析成相应的映射对象列表
	public static <T> List<T> parseJsonArrayWithGson(String jsonData,
			Class<T> type) {
		Gson gson = new Gson();
		List<T> result = gson.fromJson(jsonData, new TypeToken<List<T>>() {
		}.getType());
		return result;
	}

	/**
	 * 微信小程序
	 * @author 01059101
	 *
	 */
	public class MINIProngramOPENID {
		String session_key;
		String openid;
		String errcode;
		String errmsg;

		public String getSession_key() {
			return this.session_key;
		}

		public void setSession_key(String sessionKey) {
			this.session_key = sessionKey;
		}

		public String getOpenid() {
			return this.openid;
		}

		public void setOpenid(String openid) {
			this.openid = openid;
		}

		public String getErrcode() {
			return this.errcode;
		}

		public void setErrcode(String errcode) {
			this.errcode = errcode;
		}

		public String getErrmsg() {
			return this.errmsg;
		}

		public void setErrmsg(String errmsg) {
			this.errmsg = errmsg;
		}
	}
}
/*
 * class Student { private String name; private List<Grade> grade; //
 * 因为grade是个数组，所以要定义成List
 * 
 * public class Grade { private String course; private String score;
 * 
 * public String getCourse() { return course; }
 * 
 * public void setCourse(String course) { this.course = course; }
 * 
 * public String getScore() { return score; }
 * 
 * public void setScore(String score) { this.score = score; }
 * 
 * }
 * 
 * public String getName() { return name; }
 * 
 * public void setName(String name) { this.name = name; }
 * 
 * public List<Grade> getGrade() { return grade; }
 * 
 * public void setGrade(List<Grade> grade) { this.grade = grade; } }
 */