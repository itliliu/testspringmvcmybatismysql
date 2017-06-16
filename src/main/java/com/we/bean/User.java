package com.we.bean;

import java.util.List;

public class User {
    private Integer userid;

    private String username;

    private Integer roleid;

    private String email;

    private Integer status;

    private Long insertdate;

    private Long lastmodifydate;
    
    private String password;
    
    private List<Integer> clientids;
    
    private Integer defaultClientids;
    
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Integer> getClientids() {
		return clientids;
	}

	public void setClientids(List<Integer> clientids) {
		this.clientids = clientids;
	}

	public Integer getDefaultClientids() {
		return defaultClientids;
	}

	public void setDefaultClientids(Integer defaultClientids) {
		this.defaultClientids = defaultClientids;
	}

	
    
}