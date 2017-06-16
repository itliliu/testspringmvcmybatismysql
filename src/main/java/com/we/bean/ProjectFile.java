package com.we.bean;

public class ProjectFile {
    private Integer projectfileid;

    private Integer projectbriefid;

    private String projectbrieffileurl;

    private String projectproposalfileurl;

    private String requestformfileurl;

    private Integer status;

    private Long insertdate;

    private Long lastmodifydate;

    public Integer getProjectfileid() {
        return projectfileid;
    }

    public void setProjectfileid(Integer projectfileid) {
        this.projectfileid = projectfileid;
    }

    public Integer getProjectbriefid() {
        return projectbriefid;
    }

    public void setProjectbriefid(Integer projectbriefid) {
        this.projectbriefid = projectbriefid;
    }

    public String getProjectbrieffileurl() {
        return projectbrieffileurl;
    }

    public void setProjectbrieffileurl(String projectbrieffileurl) {
        this.projectbrieffileurl = projectbrieffileurl == null ? null : projectbrieffileurl.trim();
    }

    public String getProjectproposalfileurl() {
        return projectproposalfileurl;
    }

    public void setProjectproposalfileurl(String projectproposalfileurl) {
        this.projectproposalfileurl = projectproposalfileurl == null ? null : projectproposalfileurl.trim();
    }

    public String getRequestformfileurl() {
        return requestformfileurl;
    }

    public void setRequestformfileurl(String requestformfileurl) {
        this.requestformfileurl = requestformfileurl == null ? null : requestformfileurl.trim();
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