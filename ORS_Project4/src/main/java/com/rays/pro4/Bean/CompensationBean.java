package com.rays.pro4.Bean;

import java.util.Date;

public class CompensationBean  extends BaseBean{

	private String staffMember;
	private long paymemtAmount;
	private Date dateApplied;
	private String state;

	public String getStaffMember() {
		return staffMember;
	}

	public void setStaffMember(String staffMember) {
		this.staffMember = staffMember;
	}

	public long getPaymemtAmount() {
		return paymemtAmount;
	}

	public void setPaymemtAmount(long paymemtAmount) {
		this.paymemtAmount = paymemtAmount;
	}

	public Date getDateApplied() {
		return dateApplied;
	}

	public void setDateApplied(Date dateApplied) {
		this.dateApplied = dateApplied;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	 public String getkey() { 
		  // TODO Auto-generated method stub
	  return id+"";
	  }
	  
   public String getValue() { 
		  // TODO Auto-generated method stub 
    return null; 
	 }
   
   

}
