package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.ClientBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.ClientModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "ClientCtl", urlPatterns = { "/ctl/ClientCtl" })
public class ClientCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("fullName"))) {
			request.setAttribute("fullName", PropertyReader.getValue("error.require", "fullName"));
			pass = false;
		}
		 else if (!DataValidator.isTooLong(request.getParameter("fullName"), 2)) {
			request.setAttribute("fullName", "fullName contain 100 words");
			pass = false;
		}
		else if(request.getParameter("fullName").length()<=5 || request.getParameter("fullName").length() >=15){
			request.setAttribute("fullName", "full name bteween 5 to 15");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("appointmentDate"))) {
			request.setAttribute("appointmentDate", PropertyReader.getValue("error.require", "appointmentDate"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("appointmentDate"))) {
			request.setAttribute("appointmentDate", PropertyReader.getValue("error.date", "appointmentDate"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("phone"))) {
			request.setAttribute("phone", PropertyReader.getValue("error.require", "phone"));
			pass = false;
		}

		else if (!DataValidator.isPhoneNo(request.getParameter("phone"))) {
			request.setAttribute("phone", "phone no start with 6 to9 and contain only integer value");
			pass = false;

		}

		if (DataValidator.isNull(request.getParameter("illness"))) {
			request.setAttribute("illness", PropertyReader.getValue("error.require", "illness"));
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
		ClientModel model = new ClientModel();
		Map<Integer, String> map = new HashMap();

		map.put(1, "Cancer");
		map.put(2, "Malaria");
		map.put(3, "Diabetes ");
	

		request.setAttribute("ilnes", map);
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		ClientBean bean = new ClientBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		
		bean.setFullName(DataUtility.getString(request.getParameter("fullName")));
		bean.setAppointmentDate(DataUtility.getDate(request.getParameter("appointmentDate")));
		bean.setPhone(DataUtility.getLong(request.getParameter("phone")));
		bean.setIllness(DataUtility.getString(request.getParameter("illness")));

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		ClientModel model = new ClientModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("order Edit Id >= " + id);

		if (id != 0 && id > 0) {

			System.out.println("in id > 0  condition " + id);
			ClientBean bean;

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

		ClientModel model = new ClientModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			ClientBean bean = (ClientBean) populateBean(request);

			try {
				if (id > 0) {

					model.update(bean);
					ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("Client  is successfully Updated", request);
				} else {
					System.out.println(" U ctl DoPost 33333");
					long pk = model.add(bean);

					//ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("Client is successfully Added", request);

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

			ClientBean bean = (ClientBean) populateBean(request);
			try {
				model.delete(bean);

				ServletUtility.redirect(ORSView.CLIENT_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.CLIENT_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.CLIENT_VIEW;
	}
}
