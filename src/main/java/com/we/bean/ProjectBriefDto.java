package com.we.bean;

import java.util.Date;

public class ProjectBriefDto {
    private Integer briefId;

    private Integer userId;

    private Integer clientId;

    private String briefName;

    private Integer status;

    private Date createDate;

    private Date modifyDate;

    private Boolean isStar;

    private String briefContext;

    public Integer getBriefId() {
        return briefId;
    }

    public void setBriefId(Integer briefId) {
        this.briefId = briefId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getBriefName() {
        return briefName;
    }

    public void setBriefName(String briefName) {
        this.briefName = briefName == null ? null : briefName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Boolean getIsStar() {
        return isStar;
    }

    public void setIsStar(Boolean isStar) {
        this.isStar = isStar;
    }

    public String getBriefContext() {
        return briefContext;
    }

    public void setBriefContext(String briefContext) {
        this.briefContext = briefContext == null ? null : briefContext.trim();
    }
}