package com.rays.pro4.Bean;

import java.util.Date;

public class MedicationBean extends BaseBean {

	private String fullName;
	private String illness;
	private Date precriptionDate;
	private int dosage;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getIllness() {
		return illness;
	}

	public void setIllness(String illness) {
		this.illness = illness;
	}

	public Date getPrecriptionDate() {
		return precriptionDate;
	}

	public void setPrecriptionDate(Date precriptionDate) {
		this.precriptionDate = precriptionDate;
	}

	public int getDosage() {
		return dosage;
	}

	public void setDosage(int dosage) {
		this.dosage = dosage;
	}
	
	 public String getkey() { 
		  // TODO Auto-generated method stub
	  return id+"";
	  }
	  
   public String getValue() { 
		  // TODO Auto-generated method stub 
    return illness; 
	 }

}
