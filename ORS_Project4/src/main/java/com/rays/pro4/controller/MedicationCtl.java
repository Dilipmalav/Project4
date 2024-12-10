package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.MedicationBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.MedicationModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "MedicationCtl", urlPatterns = { "/ctl/MedicationCtl" })
public class MedicationCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("fullName"))) {

			request.setAttribute("fullName", PropertyReader.getValue("error.require", "fullName"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("fullName"))) {
			request.setAttribute("fullName", "fullName contain alphabate only");

			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("illness"))) {
			request.setAttribute("illness", PropertyReader.getValue("error.require", "illness"));
			pass = false;

		} /*else if (!DataValidator.isName(request.getParameter("illness"))) {
			request.setAttribute("illness", "illness contain alphabate only");
			pass = false;
		}*/
		if (DataValidator.isNull(request.getParameter("prescriptionDate"))) {
			request.setAttribute("prescriptionDate", PropertyReader.getValue("error.require", "prescriptionDate"));
			pass = false;
			
		} else if (!DataValidator.isDate(request.getParameter("prescriptionDate"))) {
			request.setAttribute("prescriptionDate", PropertyReader.getValue("error.date", "prescriptionDate"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("dosage"))) {

			request.setAttribute("dosage", PropertyReader.getValue("error.require", "dosage"));
			pass = false;

		}  else if (!DataValidator.isInteger(request.getParameter("dosage"))) {
			request.setAttribute("dosage", "dosage contain integer only");
			pass = false;
		}
		
		
		return pass;

	}

	protected void preload(HttpServletRequest request) {
		MedicationModel model = new MedicationModel();
		Map<Integer, String> map = new HashMap();

		map.put(1, "cancer");
		map.put(2, "diabeties");
		map.put(3, "typhiod");

		request.setAttribute("prolist", map);
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		MedicationBean bean = new MedicationBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFullName(DataUtility.getString(request.getParameter("fullName")));
		bean.setIllness(DataUtility.getString(request.getParameter("illness")));
		bean.setPrecriptionDate(DataUtility.getDate(request.getParameter("prescriptionDate")));
		bean.setDosage(DataUtility.getInt(request.getParameter("dosage")));

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		MedicationModel model = new MedicationModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("medication Edit Id >= " + id);

		if (id != 0 && id > 0) {

			System.out.println("in id > 0  condition " + id);
			MedicationBean bean;

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

		MedicationModel model = new MedicationModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			System.out.println("save op------------------------------------------------------");

			MedicationBean bean = (MedicationBean) populateBean(request);

			try {
				if (id > 0) {

					model.update(bean);
					ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("Medication  is successfully Updated", request);
				} else {
					System.out.println(
							"save ---------------------------------------------------------------------------------");
					long pk = model.add(bean);

					ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("Medication is successfully Added", request);

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

			MedicationBean bean = (MedicationBean) populateBean(request);
			try {
				model.delete(bean);

				ServletUtility.redirect(ORSView.MEDICATION_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.MEDICATION_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.MEDICATION_VIEW;
	}

}
