package com.we.bean;

public class Template {
	private Integer templateid;

	private String templatename;

	private Integer reporttype;

	private Integer format;

	private Integer status;

	private Integer clientid;

	private String type;

	private Integer userid;

	private Long insertdate;

	private Long lastmodifydate;

	private String description;

	private String fileurl;

	private Integer powerbitemplateid;

	private Integer isused;

	private Integer isdeactivated;

	public Integer getTemplateid() {
		return templateid;
	}

	public void setTemplateid(Integer templateid) {
		this.templateid = templateid;
	}

	public String getTemplatename() {
		return templatename;
	}

	public void setTemplatename(String templatename) {
		this.templatename = templatename == null ? null : templatename.trim();
	}

	public Integer getReporttype() {
		return reporttype;
	}

	public void setReporttype(Integer reporttype) {
		this.reporttype = reporttype;
	}

	public Integer getFormat() {
		return format;
	}

	public void setFormat(Integer format) {
		this.format = format;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getClientid() {
		return clientid;
	}

	public void setClientid(Integer clientid) {
		this.clientid = clientid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Long getInsertdate() {
		return insertdate;
	}

	public void setInsertdate(Long insertdate) {
		this.insertdate = insertdate;
	}

	public Long getLastmodifydate() {
		return lastmodifydate;
	}

	public void setLastmodifydate(Long lastmodifydate) {
		this.lastmodifydate = lastmodifydate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}

	public Integer getPowerbitemplateid() {
		return powerbitemplateid;
	}

	public void setPowerbitemplateid(Integer powerbitemplateid) {
		this.powerbitemplateid = powerbitemplateid;
	}

	public Integer getIsused() {
		return isused;
	}

	public void setIsused(Integer isused) {
		this.isused = isused;
	}

	public Integer getIsdeactivated() {
		return isdeactivated;
	}

	public void setIsdeactivated(Integer isdeactivated) {
		this.isdeactivated = isdeactivated;
	}

}