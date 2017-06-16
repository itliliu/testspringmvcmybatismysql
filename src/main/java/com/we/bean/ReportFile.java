package com.we.bean;

public class ReportFile {
    private Integer reportfileid;
    
    private Integer projectid;
    
    private Integer reportid;

    private String reportfileurl;

    private String powerbitemplateurl;

    private String tilesprotemplateurl;
    
    private Integer powerbitemplateid;

    private Integer tilesprotemplateid;

    private Integer status;

    private Long insertdate;

    private Long lastmodifydate;

    public Integer getReportfileid() {
        return reportfileid;
    }

    public void setReportfileid(Integer reportfileid) {
        this.reportfileid = reportfileid;
    }

    public Integer getReportid() {
        return reportid;
    }

    public void setReportid(Integer reportid) {
        this.reportid = reportid;
    }

    public String getReportfileurl() {
        return reportfileurl;
    }

    public void setReportfileurl(String reportfileurl) {
        this.reportfileurl = reportfileurl == null ? null : reportfileurl.trim();
    }

    public String getPowerbitemplateurl() {
        return powerbitemplateurl;
    }

    public void setPowerbitemplateurl(String powerbitemplateurl) {
        this.powerbitemplateurl = powerbitemplateurl == null ? null : powerbitemplateurl.trim();
    }

    public String getTilesprotemplateurl() {
        return tilesprotemplateurl;
    }

    public void setTilesprotemplateurl(String tilesprotemplateurl) {
        this.tilesprotemplateurl = tilesprotemplateurl == null ? null : tilesprotemplateurl.trim();
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

	public Integer getProjectid() {
		return projectid;
	}

	public void setProjectid(Integer projectid) {
		this.projectid = projectid;
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
	
}