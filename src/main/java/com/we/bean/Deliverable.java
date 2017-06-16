package com.we.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.we.tool.CustomDueDateDeserialize;

/**
 * deliverable bean
 * 
 * @author hongfengma
 */
public class Deliverable {
	private String name;
	private Integer formatid;
	private String format;
	private String duedate;
	private Integer reporttypeid;
	private String reporttype;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getDuedate() {
		return duedate;
	}

	@JsonDeserialize(using = CustomDueDateDeserialize.class)
	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}

	public String getReporttype() {
		return reporttype;
	}

	public void setReporttype(String reporttype) {
		this.reporttype = reporttype;
	}

	public Integer getFormatid() {
		return formatid;
	}

	public void setFormatid(Integer formatid) {
		this.formatid = formatid;
	}

	public Integer getReporttypeid() {
		return reporttypeid;
	}

	public void setReporttypeid(Integer reporttypeid) {
		this.reporttypeid = reporttypeid;
	}

}
