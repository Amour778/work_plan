package com.workplan.bean;


public class WeakCurrentProjectsBonusSharesBean {
	/**
	 * 奖金/质保金分配
	 */
	private String ID;
	private String project_code;
	private String user_id;
	private String user_name;
	private String proportion;
	private String money;
	private String returned_money;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getProject_code() {
		return project_code;
	}
	public void setProject_code(String projectCode) {
		project_code = projectCode;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String userId) {
		user_id = userId;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String userName) {
		user_name = userName;
	}
	public String getProportion() {
		return proportion;
	}
	public void setProportion(String proportion) {
		this.proportion = proportion;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getReturned_money() {
		return returned_money;
	}
	public void setReturned_money(String returned_money) {
		this.returned_money = returned_money;
	}
	
	
	
	}