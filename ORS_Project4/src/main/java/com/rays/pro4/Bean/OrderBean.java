package com.rays.pro4.Bean;

import java.util.Date;

public class OrderBean extends BaseBean {
	
	private String productName;
	private Date dob;
	private Long quantity;
	private String customer;
	public String getProductName() {
		return productName;
	}		
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
