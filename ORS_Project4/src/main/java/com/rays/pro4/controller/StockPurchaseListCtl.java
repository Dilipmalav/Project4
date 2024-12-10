
package com.rays.pro4.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.StockPurchaseBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.StockPurchaseModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "StockPurchaseListCtl", urlPatterns = { "/ctl/StockPurchaseListCtl" })
public class StockPurchaseListCtl extends BaseCtl {
	
	@Override
	protected void preload(HttpServletRequest request) {
		
		StockPurchaseModel model = new StockPurchaseModel();
		Map<Integer, String> map = new HashMap();

		map.put(1, "Market");
		map.put(2, "Limit");
		
		request.setAttribute("ilnes", map);
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		StockPurchaseBean bean = new StockPurchaseBean();

bean.setId(DataUtility.getLong(request.getParameter("id")));
		
		bean.setQuantity(DataUtility.getLong(request.getParameter("quantity")));
		
		bean.setPurchasePrice(DataUtility.getDouble(request.getParameter("purchasePrice")));
		bean.setPurchaseDate(DataUtility.getDate(request.getParameter("purchaseDate")));
		bean.setOrderType(DataUtility.getString(request.getParameter("orderType")));


		return bean;
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;
		List nextList = null;

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		StockPurchaseBean bean = (StockPurchaseBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));
	//	System.out.println(">>>>>>>>>>>>>>>helooo" + bean.getAppointmentDate());

		StockPurchaseModel model = new StockPurchaseModel();

		try {
			list = model.search(bean, pageNo, pageSize);
			System.out.println("list" + list);

			nextList = model.search(bean, pageNo + 1, pageSize);

			request.setAttribute("nextlist", nextList.size());

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			// ServletUtility.setBean(bean, request);

		} catch (ApplicationException e) {

			ServletUtility.handleException(e, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("PaymentListCtl doPost Start");

		List list;
		List nextList = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		String op = DataUtility.getString(request.getParameter("operation"));
		StockPurchaseBean bean = (StockPurchaseBean) populateBean(request);

		String[] ids = request.getParameterValues("ids");
		StockPurchaseModel model = new StockPurchaseModel();
		
		
          if (OP_SEARCH.equalsIgnoreCase(op)) {
			
			if(request.getParameter("quantity").equals("")&& request.getParameter("purchasePrice").equals("")
					&& request.getParameter("purchaseDate").equals("")&& request.getParameter("orderType").equals("")) {
				ServletUtility.setErrorMessage("fill at least one field", request);
			}
				
			}
		

		if (OP_SEARCH.equalsIgnoreCase(op)) {
			pageNo = 1;
		} else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;
		} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
			pageNo--;

		} else if (OP_NEW.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.STOCKPURCHASE_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.STOCKPURCHASE_LIST_CTL, request, response);
			return;

		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
				StockPurchaseBean deletebean = new StockPurchaseBean();
				for (String id : ids) {
					deletebean.setId(DataUtility.getInt(id));
					try {
						model.delete(deletebean);
					} catch (ApplicationException e) {

						ServletUtility.handleException(e, request, response);
						return;
					}

					ServletUtility.setSuccessMessage("StockPurchase is Deleted Successfully", request);
				}
			} else {
				ServletUtility.setErrorMessage("Select at least one record", request);
			}
		}
		try {

			list = model.search(bean, pageNo, pageSize);

			nextList = model.search(bean, pageNo + 1, pageSize);

			request.setAttribute("nextlist", nextList.size());

		} catch (ApplicationException e) {

			ServletUtility.handleException(e, request, response);
			return;
		}
		if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
			ServletUtility.setErrorMessage("No record found ", request);
		}
		ServletUtility.setList(list, request);
		ServletUtility.setBean(bean, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);

		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.STOCKPURCHASE_LIST_VIEW;
	}

}