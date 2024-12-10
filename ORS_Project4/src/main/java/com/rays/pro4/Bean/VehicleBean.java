 package com.rays.pro4.Bean;

import java.util.Date;

public class VehicleBean extends BaseBean {

	private String number;
	private Date purchaseDate;
	private int insuranceAmount;
	private String colour;
	
	
	 public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public int getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(int insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getkey() { 
		  // TODO Auto-generated method stub
	  return id+"";
	  }
	  
     public String getValue() { 
		  // TODO Auto-generated method stub 
      return colour; 
	 }
}
