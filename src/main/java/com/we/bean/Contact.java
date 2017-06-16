package com.we.bean;

/**
 * Contact bean
 * 
 * @author Hongfeng Ma
 */
public class Contact {
	private String name;
	private String title;
	private String email;
	private String tel;

	public Contact() {
	}

	public Contact(String name, String title, String email, String tel) {
		this.name = name;
		this.title = title;
		this.email = email;
		this.tel = tel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
}
