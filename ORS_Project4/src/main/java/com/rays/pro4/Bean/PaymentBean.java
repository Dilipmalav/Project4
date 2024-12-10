package com.rays.pro4.Bean;

public class PaymentBean extends BaseBean{
	//private int id;
	private String c_Name;
	private String account;
//	public int getId() {
//		return id;
//	}
//	public void setId(int id) {
//		this.id = id;
//	}
	public String getC_Name() {
		return c_Name;
	}
	public void setC_Name(String c_Name) {
		this.c_Name = c_Name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return account;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		 return account;
	}
	
	

}
