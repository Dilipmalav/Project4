package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.CustomerBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.CustomerModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "CustomerCtl", urlPatterns = { "/ctl/CustomerCtl" })
public class CustomerCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("clientName"))) {

			request.setAttribute("clientName", PropertyReader.getValue("error.require", "clientName"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("clientName"))) {
			request.setAttribute("clientName", "clientName contain alphabate only");

			pass = false;
		}
		else if (DataValidator.isTooLong(request.getParameter("clientName"), 15)) {
			request.setAttribute("clientName", " only 15 letters are allowed ");
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("location"))) {
			request.setAttribute("location", PropertyReader.getValue("error.require", "location"));
			pass = false;

		} else if (!DataValidator.validateAlphanumeric(request.getParameter("location"))) {
			request.setAttribute("location", "location contain alphabate & Intager value only");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("contactNumber"))) {
			request.setAttribute("contactNumber", PropertyReader.getValue("error.require", "contactNumber"));
			pass = false;
			
		} else if (!DataValidator.isPhoneNo(request.getParameter("contactNumber"))) {
			request.setAttribute("contactNumber", "Mobile No. must be 10 Digit and No. Series start with 6-9");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("importance"))) {

			request.setAttribute("importance", PropertyReader.getValue("error.require", "importance"));
			pass = false;

		} 
		return pass;

	}

	protected void preload(HttpServletRequest request) {
		CustomerModel model = new CustomerModel();
		Map<Integer, String> map = new HashMap();

		map.put(1, "High");
		map.put(2, "Medium");
		map.put(3, "Low");

		request.setAttribute("prolist", map);
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		CustomerBean bean = new CustomerBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setClientName(DataUtility.getString(request.getParameter("clientName")));
		System.out.println(
				bean.getClientName() + "client aname=============================================================");
		bean.setLocation(DataUtility.getString(request.getParameter("location")));
		bean.setContactNumber(DataUtility.getLong(request.getParameter("contactNumber")));
		bean.setImportance(DataUtility.getString(request.getParameter("importance")));

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		CustomerModel model = new CustomerModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("customer Edit Id >= " + id);

		if (id != 0 && id > 0) {

			System.out.println("in id > 0  condition " + id);
			CustomerBean bean;

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
		System.out.println("cus ctl post----------------------------------------------------");
		String op = DataUtility.getString(request.getParameter("operation"));

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println(">>>><<<<>><<><<><<><>" + id + op);

		CustomerModel model = new CustomerModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			System.out.println("save op------------------------------------------------------");

			CustomerBean bean = (CustomerBean) populateBean(request);

			try {
				if (id > 0) {

					model.update(bean);
					ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("Customer  is successfully Updated", request);
				} else {
					System.out.println(
							"save ---------------------------------------------------------------------------------");
					long pk = model.add(bean);

					ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("Customer is successfully Added", request);

					// bean.setId(pk);
				}

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			CustomerBean bean = (CustomerBean) populateBean(request);
			try {
				model.delete(bean);

				ServletUtility.redirect(ORSView.CUSTOMER_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.CUSTOMER_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.CUSTOMER_VIEW;
	}

}
