package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.PortfolioManagementBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.PortfolioManagementModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "PortfolioManagementCtl", urlPatterns = { "/ctl/PortfolioManagementCtl" })
public class PortfolioManagementCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("portfolioName"))) {

			request.setAttribute("portfolioName", PropertyReader.getValue("error.require", "portfolioName"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("portfolioName"))) {
			request.setAttribute("portfolioName", "portfolioName contain alphabate only");

			pass = false;
		}
		 else if (request.getParameter("portfolioName").length()<=3 || request.getParameter("portfolioName").length()>=30) {
			request.setAttribute("portfolioName", "portfolioName contain alphabate between 3 to 30");

			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("initialInvestmentAmount"))) {
			request.setAttribute("initialInvestmentAmount", PropertyReader.getValue("error.require", "initialInvestmentAmount"));
			pass = false;
			
		} else if (!DataValidator.isInteger(request.getParameter("initialInvestmentAmount"))) {
			request.setAttribute("initialInvestmentAmount", "initialInvestmentAmount contain only intager");
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("investmentStartegy"))) {

			request.setAttribute("investmentStartegy", PropertyReader.getValue("error.require", "investmentStartegy"));
			pass = false;
         } 
		else if (!DataValidator.isName(request.getParameter("investmentStartegy"))) {
			request.setAttribute("investmentStartegy", "investmentStartegy contain alphabate only");

			pass = false;
		}
		else if (request.getParameter("investmentStartegy").length()<=10 || request.getParameter("investmentStartegy").length()>=200) {
			request.setAttribute("investmentStartegy", "investmentStartegy contain alphabate between 10 to 200");

			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("riskToleranceLevel"))) {
			request.setAttribute("riskToleranceLevel", PropertyReader.getValue("error.require", "riskToleranceLevel"));
			pass = false;

		} 
		
		
		return pass;

	}

	protected void preload(HttpServletRequest request) {
		PortfolioManagementModel model = new PortfolioManagementModel();
		Map<Integer, String> map = new HashMap();
		
		map.put(1, "Low");
		map.put(2, "Medium");
		map.put(3, "High");
		
		

		request.setAttribute("prolist", map);
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		PortfolioManagementBean bean = new PortfolioManagementBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setPortfolioName(DataUtility.getString(request.getParameter("portfolioName")));
		bean.setInitialInvestmentAmount(DataUtility.getLong(request.getParameter("initialInvestmentAmount")));
		bean.setRiskToleranceLevel(DataUtility.getString(request.getParameter("riskToleranceLevel")));
		
		bean.setInvestmentStartegy(DataUtility.getString(request.getParameter("investmentStartegy")));

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ctl===================================================");
		String op = DataUtility.getString(request.getParameter("operation"));

		PortfolioManagementModel model = new PortfolioManagementModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println("portfoliomanagement Edit Id >= " + id);

		if (id != 0 && id > 0) {

			System.out.println("in id > 0  condition " + id);
			PortfolioManagementBean bean;

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

		PortfolioManagementModel model = new PortfolioManagementModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			

			PortfolioManagementBean bean = (PortfolioManagementBean) populateBean(request);

			try {
				if (id > 0) {

					model.update(bean);
					ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("PortfolioManagement  is successfully Updated", request);
				} else {
					long pk = model.add(bean);

					ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("PortfolioManagement is successfully Added", request);

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

			PortfolioManagementBean bean = (PortfolioManagementBean) populateBean(request);
			try {
				model.delete(bean);

				ServletUtility.redirect(ORSView.PORTFOLIOMANAGEMENT_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.PORTFOLIOMANAGEMENT_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.PORTFOLIOMANAGEMENT_VIEW;
	}

}
