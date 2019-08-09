package com.workplan.bean;

public class WorkItemSecondBean {

	// ID
	private long ID;

	// 一级ID
	private String superior_item_id;
	
	// 一级名称
	private String superior_item_name;

	// 事项ID
	private String itemId;

	// 事项名称
	private String itemName;

	// 组织
	private String organization;

	// 工作内容
	private String workContent;

	// 步骤
	private String step;

	// 完成周期
	private String completionCycle;

	// 责任人
	private String personLiable;

	// 配合人
	private String partner;

	// 开始时间
	private String startTime;

	// 完成时间
	private String completionTime;

	// 状态：-1表示未开始，0表示进行中，1表示完成
	private int state;

	// 事项添加日期
	private String creationDate;
	// 进度
	private String progress;
	
	public long getID() {
		return this.ID;
	};

	public void setID(long ID) {
		this.ID = ID;
	}

	public String getSuperior_item_id() {
		return superior_item_id;
	}

	public void setSuperior_item_id(String superiorItemId) {
		superior_item_id = superiorItemId;
	}

	public String getSuperior_item_name() {
		return superior_item_name;
	}

	public void setSuperior_item_name(String superiorItemName) {
		superior_item_name = superiorItemName;
	}

	public String getItemId() {
		return this.itemId;
	};

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return this.itemName;
	};

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getOrganization() {
		return this.organization;
	};

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getWorkContent() {
		return this.workContent;
	};

	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}

	public String getStep() {
		return this.step;
	};

	public void setStep(String step) {
		this.step = step;
	}

	public String getCompletionCycle() {
		return this.completionCycle;
	};

	public void setCompletionCycle(String completionCycle) {
		this.completionCycle = completionCycle;
	}

	public String getPersonLiable() {
		return this.personLiable;
	};

	public void setPersonLiable(String personLiable) {
		this.personLiable = personLiable;
	}

	public String getPartner() {
		return this.partner;
	};

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getStartTime() {
		return this.startTime;
	};

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getCompletionTime() {
		return this.completionTime;
	};

	public void setCompletionTime(String completionTime) {
		this.completionTime = completionTime;
	}

	public int getState() {
		return this.state;
	};

	public void setState(int state) {
		this.state = state;
	}

	public String getCreationDate() {
		return this.creationDate;
	};

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	
	

}
