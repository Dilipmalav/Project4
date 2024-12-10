package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.PhysicianBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.PhysicianModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "PhysicianCtl", urlPatterns = { "/ctl/PhysicianCtl" })
public class PhysicianCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("fullName"))) {
			request.setAttribute("fullName", PropertyReader.getValue("error.require", "fullName"));
			pass = false;

		} else if (!DataValidator.isTooLong(request.getParameter("fullName"), 2)) {
			request.setAttribute("fullName", "fullName contain 100 words");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("birthDate"))) {
			request.setAttribute("birthDate", PropertyReader.getValue("error.require", "birthDate"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("birthDate"))) {
			request.setAttribute("birthDate", PropertyReader.getValue("error.date", "birthDate"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("phone"))) {
			request.setAttribute("phone", PropertyReader.getValue("error.require", "phone"));
			pass = false;
		}

		else if (!DataValidator.isInteger(request.getParameter("phone"))) {
			request.setAttribute("phone", "phone contain intger value only");
			pass = false;

		}

		if (DataValidator.isNull(request.getParameter("specialization"))) {
			request.setAttribute("specialization", PropertyReader.getValue("error.require", "specialization"));
			pass = false;
		}

		/*
		 * else if (!DataValidator.isName(request.getParameter("customer"))) {
		 * request.setAttribute("customer", "customer  must contains alphabet only");
		 * pass = false; }
		 */
		return pass;

	}
	@Override
	protected void preload(HttpServletRequest request) {
		PhysicianModel model = new PhysicianModel();
		Map<Integer, String> map = new HashMap();

		map.put(1, "Dermatologists");
		map.put(2, "Cardiology");
		map.put(3, "Nephrology");
		
		request.setAttribute("splztion", map);
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		PhysicianBean bean = new PhysicianBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFullName(DataUtility.getString(request.getParameter("fullName")));
		bean.setBirthDate(DataUtility.getDate(request.getParameter("birthDate")));
		bean.setPhone(DataUtility.getLong(request.getParameter("phone")));
		bean.setSpecialization(DataUtility.getString(request.getParameter("specialization")));

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		PhysicianModel model = new PhysicianModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("order Edit Id >= " + id);

		if (id != 0 && id > 0) {

			System.out.println("in id > 0  condition " + id);
			PhysicianBean bean;

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

		PhysicianModel model = new PhysicianModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			PhysicianBean bean = (PhysicianBean) populateBean(request);

			try {
				if (id > 0) {

					model.update(bean);
					ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("Physician  is successfully Updated", request);
				} else {
					System.out.println(" U ctl DoPost 33333");
					long pk = model.add(bean);

					ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("Physician is successfully Added", request);

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

			PhysicianBean bean = (PhysicianBean) populateBean(request);
			try {
				model.delete(bean);

				ServletUtility.redirect(ORSView.PHYSICIAN_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.PHYSICIAN_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.PHYSICIAN_VIEW;
	}

}
