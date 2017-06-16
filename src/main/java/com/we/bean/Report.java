package com.we.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.we.tool.CustomDateDeserialize;

public class Report {
	private Integer reportid;

	private Integer userid;

	private String name;

	private Integer projectid;

	private Long insertdate;

	private Long lastmodifydate;

	private Integer powerbitemplateid;

	private Integer tilesprotemplateid;

	private String comment;

	private Integer reporttypeid;

	private String reporttype;

	private Long duedate;

	private Integer formatid;

	private String format;

	private String operator;

	private String powerbitemplatename;

	private String tilesprotemplatename;

	private String reportfileurl;

	public Integer getReportid() {
		return reportid;
	}

	public void setReportid(Integer reportid) {
		this.reportid = reportid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getProjectid() {
		return projectid;
	}

	public void setProjectid(Integer projectid) {
		this.projectid = projectid;
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

	public Integer getPowerbitemplateid() {
		return powerbitemplateid;
	}

	public void setPowerbitemplateid(Integer powerbitemplateid) {
		this.powerbitemplateid = powerbitemplateid;
	}

	public Integer getTilesprotemplateid() {
		return tilesprotemplateid;
	}

	public void setTilesprotemplateid(Integer tilesprotemplateid) {
		this.tilesprotemplateid = tilesprotemplateid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment == null ? null : comment.trim();
	}

	public String getReporttype() {
		return reporttype;
	}

	public void setReporttype(String reporttype) {
		this.reporttype = reporttype == null ? null : reporttype.trim();
	}

	public Long getDuedate() {
		return duedate;
	}

	@JsonDeserialize(using = CustomDateDeserialize.class)
	public void setDuedate(Long duedate) {
		this.duedate = duedate;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format == null ? null : format.trim();
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getPowerbitemplatename() {
		return powerbitemplatename;
	}

	public void setPowerbitemplatename(String powerbitemplatename) {
		this.powerbitemplatename = powerbitemplatename;
	}

	public String getTilesprotemplatename() {
		return tilesprotemplatename;
	}

	public void setTilesprotemplatename(String tilesprotemplatename) {
		this.tilesprotemplatename = tilesprotemplatename;
	}

	public String getReportfileurl() {
		return reportfileurl;
	}

	public void setReportfileurl(String reportfileurl) {
		this.reportfileurl = reportfileurl;
	}

	public Integer getReporttypeid() {
		return reporttypeid;
	}

	public void setReporttypeid(Integer reporttypeid) {
		this.reporttypeid = reporttypeid;
	}

	public Integer getFormatid() {
		return formatid;
	}

	public void setFormatid(Integer formatid) {
		this.formatid = formatid;
	}

}