package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.TradeHistoryBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.TradeHistoryModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "TradeHistoryCtl", urlPatterns = { "/ctl/TradeHistoryCtl" })
public class TradeHistoryCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("userId"))) {
			request.setAttribute("userId", PropertyReader.getValue("error.require", "userId"));
			pass = false;
		}
		 else if(!DataValidator.validateAlphanumeric(request.getParameter("userId")) ) {
			 request.setAttribute("userId", "Special character is not allowed ");
			pass = false;
		}
		
		
//		 else if (!DataValidator.isTooLong(request.getParameter("userId"), 2)) {
//			request.setAttribute("userId", "fullName contain 100 words");
//			pass = false;
//		}
		else if(request.getParameter("userId").length()<=5 || request.getParameter("userId").length() >=15){
			request.setAttribute("userId", "userId  bteween 5 to 15 words ");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("startDate"))) {
			request.setAttribute("startDate", PropertyReader.getValue("error.require", "startDate"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("startDate"))) {
			request.setAttribute("startDate", PropertyReader.getValue("error.date", "startDate"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("endDate"))) {
			request.setAttribute("endDate", PropertyReader.getValue("error.require", "endDate"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("endDate"))) {
			request.setAttribute("endDate", PropertyReader.getValue("error.date", "endDate"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("transctionType"))) {
			request.setAttribute("transctionType", PropertyReader.getValue("error.require", "transctionType"));
			pass = false;
		}

		/*
		 * else if (!DataValidator.isName(request.getParameter("customer"))) {
		 * request.setAttribute("customer", "customer  must contains alphabet only");
		 * pass = false; }
		 */
		return pass;

	}

	protected void preload(HttpServletRequest request) {
		TradeHistoryModel model = new TradeHistoryModel();
		Map<Integer, String> map = new HashMap();

		map.put(1, "Bay");
		map.put(2, "Sell");
		map.put(3, "All ");
	

		request.setAttribute("ilnes", map);
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		TradeHistoryBean bean = new TradeHistoryBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		
		bean.setUserId(DataUtility.getString(request.getParameter("userId")));
		bean.setStartDate(DataUtility.getDate(request.getParameter("startDate")));
		bean.setEndDate(DataUtility.getDate(request.getParameter("endDate")));
		bean.setTrasactionType(DataUtility.getString(request.getParameter("transctionType")));

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		TradeHistoryModel model = new TradeHistoryModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("order Edit Id >= " + id);

		if (id != 0 && id > 0) {

			System.out.println("in id > 0  condition " + id);
			TradeHistoryBean bean;

			try {
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);

			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println(">>>><<<<>><<><<><<><>" + id + op);

		TradeHistoryModel model = new TradeHistoryModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			TradeHistoryBean bean = (TradeHistoryBean) populateBean(request);

			try {
				if (id > 0) {

					model.update(bean);
					ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("TradeHistory  is successfully Updated", request);
				} else {
					System.out.println(" U ctl DoPost 33333");
					long pk = model.add(bean);

					//ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("TradeHistory is successfully Added", request);

					bean.setId(pk);
				}

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			TradeHistoryBean bean = (TradeHistoryBean) populateBean(request);
			try {
				model.delete(bean);

				ServletUtility.redirect(ORSView.TRADEHISTORY_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.TRADEHISTORY_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.TRADEHISTORY_VIEW;
	}
	
	
}
