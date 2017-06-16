package com.we.bean;

public class ReportType {
    private Integer reporttypeid;

    private String reporttype;

    private Integer status;

    private Long insertdate;

    private Long lastmodifydate;

    public Integer getReporttypeid() {
        return reporttypeid;
    }

    public void setReporttypeid(Integer reporttypeid) {
        this.reporttypeid = reporttypeid;
    }

    public String getReporttype() {
        return reporttype;
    }

    public void setReporttype(String reporttype) {
        this.reporttype = reporttype;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
}