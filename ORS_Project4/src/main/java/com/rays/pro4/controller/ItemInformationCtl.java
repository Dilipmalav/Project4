package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.ItemInformationBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.ItemInformationModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "ItemInformationCtl", urlPatterns = { "/ctl/ItemInformationCtl" })
public class ItemInformationCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("title"))) {
			request.setAttribute("title", PropertyReader.getValue("error.require", "title"));
			pass = false;
			
		} else if (!DataValidator.isName(request.getParameter("title"))) {
			request.setAttribute("title", "only letter are allowed ");
			pass = false;
		}

		else if (request.getParameter("title").length() <= 2|| request.getParameter("title").length() >= 15) {
			request.setAttribute("title", " title bteween 2 to 15");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("overView"))) {
			request.setAttribute("overView", PropertyReader.getValue("error.require", "overView"));
			pass = false;
		} 
		 else if (!DataValidator.isName(request.getParameter("overView"))) {
				request.setAttribute("overView", "only letter are allowed ");
				pass = false;
			}
		 
		 else if (request.getParameter("overView").length() <= 10|| request.getParameter("overView").length() >= 250) {
				request.setAttribute("overView", " overView bteween 10 to 250");
				pass = false;
			}
		
		
		
		if (DataValidator.isNull(request.getParameter("cost"))) {
			request.setAttribute("cost", PropertyReader.getValue("error.require", "cost"));
			pass = false;
		}

		else if (!DataValidator.isInteger(request.getParameter("cost"))) {
			request.setAttribute("cost", "cost contain only integer value");
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("purchaseDate"))) {
			request.setAttribute("purchaseDate", PropertyReader.getValue("error.require", "purchaseDate"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("purchaseDate"))) {
			request.setAttribute("purchaseDate", PropertyReader.getValue("error.date", "purchaseDate"));
			pass = false;
		}
		

		if (DataValidator.isNull(request.getParameter("category"))) {
			request.setAttribute("category", PropertyReader.getValue("error.require", "category"));
			pass = false;
		}

		/*
		 * else if (!DataValidator.isName(request.getParameter("category"))) {
		 * request.setAttribute("category", "customer  must contains alphabet only");
		 * pass = false; }
		 */
		return pass;

	}

	protected void preload(HttpServletRequest request) {
		ItemInformationModel model = new ItemInformationModel();
		Map<Integer, String> map = new HashMap();

		map.put(1, "High");
		map.put(2, "Low");
	

		request.setAttribute("ilnes", map);
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		ItemInformationBean bean = new ItemInformationBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setTitle(DataUtility.getString(request.getParameter("title")));
		bean.setOverView(DataUtility.getString(request.getParameter("overView")));
		bean.setCost(DataUtility.getLong(request.getParameter("cost")));
		bean.setPurchaseDate(DataUtility.getDate(request.getParameter("purchaseDate")));
		
		bean.setCategory(DataUtility.getString(request.getParameter("category")));

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));

		ItemInformationModel model = new ItemInformationModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("order Edit Id >= " + id);

		if (id != 0 && id > 0) {

			System.out.println("in id > 0  condition " + id);
			ItemInformationBean bean;

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

		ItemInformationModel model = new ItemInformationModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			ItemInformationBean bean = (ItemInformationBean) populateBean(request);

			try {
				if (id > 0) {

					model.update(bean);
					ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("ItemInformation  is successfully Updated", request);
				} else {
					System.out.println(" U ctl DoPost 33333");
					long pk = model.add(bean);

					// ServletUtility.setBean(bean, request);
					ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("ItemInformation is successfully Added", request);

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

			ItemInformationBean bean = (ItemInformationBean) populateBean(request);
			try {
				model.delete(bean);

				ServletUtility.redirect(ORSView.ITEMINFORMATION_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.ITEMINFORMATION_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ITEMINFORMATION_VIEW;
	}
}
