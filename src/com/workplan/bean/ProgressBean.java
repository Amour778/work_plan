package com.workplan.bean;

public class ProgressBean {

	// ID
	private long ID;

	// 二级事项ID
	private String item_id;

	// 进展内容
	private String item_progress;

	// 进展日期
	private String item_date;

	// 编辑人员
	private String edit_user;

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String itemId) {
		item_id = itemId;
	}

	public String getItem_progress() {
		return item_progress;
	}

	public void setItem_progress(String itemProgress) {
		item_progress = itemProgress;
	}

	public String getItem_date() {
		return item_date;
	}

	public void setItem_date(String itemDate) {
		item_date = itemDate;
	}

	public String getEdit_user() {
		return edit_user;
	}

	public void setEdit_user(String editUser) {
		edit_user = editUser;
	}

}
