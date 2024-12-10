package com.rays.pro4.Bean;

import java.util.Date;

public class TradeHistoryBean  extends BaseBean{

	private String userId;
	private Date startDate;
	private Date endDate;
	private String trasactionType;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getTrasactionType() {
		return trasactionType;
	}

	public void setTrasactionType(String trasactionType) {
		this.trasactionType = trasactionType;
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
