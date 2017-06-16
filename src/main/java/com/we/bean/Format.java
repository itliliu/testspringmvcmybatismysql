package com.we.bean;

public class Format {
    private Integer formatid;

    private String formattype;

    private Integer status;

    private Long insertdate;

    private Long lastmodifydate;

    public Integer getFormatid() {
        return formatid;
    }

    public void setFormatid(Integer formatid) {
        this.formatid = formatid;
    }

    public String getFormattype() {
        return formattype;
    }

    public void setFormattype(String formattype) {
        this.formattype = formattype;
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