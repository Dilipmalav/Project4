package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.CompensationBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.CompensationModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "CompensationCtl", urlPatterns = { "/ctl/CompensationCtl" })
public class CompensationCtl extends BaseCtl {
	

	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");

		boolean pass = true;

//		if (DataValidator.isNull(request.getParameter("clientName"))) {
//
//			request.setAttribute("clientName", PropertyReader.getValue("error.require", "clientName"));
//			pass = false;
//
//		} else if (!DataValidator.isName(request.getParameter("clientName"))) {
//			request.setAttribute("clientName", "clientName contain alphabate only");
//
//			pass = false;
//		}
//		if (DataValidator.isNull(request.getParameter("location"))) {
//			request.setAttribute("location", PropertyReader.getValue("error.require", "location"));
//			pass = false;
//
//		} else if (!DataValidator.isName(request.getParameter("location"))) {
//			request.setAttribute("location", "location contain alphabate only");
//			pass = false;
//		}
//		if (DataValidator.isNull(request.getParameter("contactNumber"))) {
//			request.setAttribute("contactNumber", PropertyReader.getValue("error.require", "contactNumber"));
//			pass = false;
//			
//		} else if (!DataValidator.isPhoneNo(request.getParameter("contactNumber"))) {
//			request.setAttribute("contactNumber", "Mobile No. must be 10 Digit and No. Series start with 6-9");
//			pass = false;
//		}
//		if (DataValidator.isNull(request.getParameter("importance"))) {
//
//			request.setAttribute("importance", PropertyReader.getValue("error.require", "importance"));
//			pass = false;
//
//		} 
		return pass;

	}

	protected void preload1(HttpServletRequest request) {
		CompensationModel model = new CompensationModel();
		Map<Integer, String> map1 = new HashMap();

		map1.put(1, "dilip");
		map1.put(2, "sagar");
		map1.put(3, "ram");
		map1.put(4, "rahul");
		map1.put(5, "prakhar");
		map1.put(6, "ankit");

		request.setAttribute("stafflist", map1);
	}
	
	protected void preload2(HttpServletRequest request) {
		CompensationModel model = new CompensationModel();
		Map<Integer, String> map2 = new HashMap();

		map2.put(1, "Active");
		map2.put(2, "InActive");
		

		request.setAttribute("statelist", map2);
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		CompensationBean bean = new CompensationBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setStaffMember(DataUtility.getString(request.getParameter("staffMember")));
		System.out.println(bean.getStaffMember()+"satttttttttttttttttttttttttttttttttt");
		bean.setPaymemtAmount(DataUtility.getLong(request.getParameter("paymentAmount")));
		bean.setDateApplied(DataUtility.getDate(request.getParameter("dateApplied")));
		bean.setState(DataUtility.getString(request.getParameter("state")));

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		CompensationModel model = new CompensationModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("compensation Edit Id >= " + id);

		if (id != 0 && id > 0) {

			System.out.println("in id > 0  condition " + id);
			CompensationBean bean;

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

		CompensationModel model = new CompensationModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
		

			CompensationBean bean = (CompensationBean) populateBean(request);

			try {
				if (id > 0) {

					model.update(bean);
					ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("Compensation  is successfully Updated", request);
				} else {
					
					long pk = model.add(bean);

					ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("Compensation is successfully Added", request);

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

			CompensationBean bean = (CompensationBean) populateBean(request);
			try {
				model.delete(bean);

				ServletUtility.redirect(ORSView.COMPENSATION_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.COMPENSATION_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.COMPENSATION_VIEW;
	}

}
