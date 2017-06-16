package com.we.bean;

/**
 * milestone bean
 * 
 * @author Hongfeng Ma
 */
public class Milestone {
	private String name;

	private String date;

	public Milestone() {
	}

	public Milestone(String name, String date) {
		this.name = name;
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
