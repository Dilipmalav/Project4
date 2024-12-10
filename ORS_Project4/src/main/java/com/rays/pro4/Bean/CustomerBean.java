package com.rays.pro4.Bean;

public class CustomerBean extends BaseBean {

	private String clientName;
	private String location;
	private long contactNumber;
	private String importance;

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public long getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(long contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}


	  public String getkey() { 
		  // TODO Auto-generated method stub
	  return id+"";
	  }
	  
    public String getValue() { 
		  // TODO Auto-generated method stub 
     return importance; 
	 }

}
