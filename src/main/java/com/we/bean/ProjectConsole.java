package com.we.bean;

public class ProjectConsole {
	private Integer projectBriefID;
	private Integer parentID;
	private String name;
	private Integer status;
	private String clientName;
	private Integer userid;
	private String comment;
	public Integer getProjectBriefID() {
		return projectBriefID;
	}
	public void setProjectBriefID(Integer projectBriefID) {
		this.projectBriefID = projectBriefID;
	}
	public Integer getParentID() {
		return parentID;
	}
	public void setParentID(Integer parentID) {
		this.parentID = parentID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
