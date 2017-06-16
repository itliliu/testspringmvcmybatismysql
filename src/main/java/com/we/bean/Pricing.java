package com.we.bean;

import java.util.List;

/**
 * pricing bean
 * 
 * @author Hongfeng Ma
 */
public class Pricing {

	private String total;
	private List<Price> list;

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public List<Price> getList() {
		return list;
	}

	public void setList(List<Price> list) {
		this.list = list;
	}

}
