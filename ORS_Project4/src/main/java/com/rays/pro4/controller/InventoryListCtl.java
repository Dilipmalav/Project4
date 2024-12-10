
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
import com.rays.pro4.Bean.InventoryBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.InventoryModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "InventoryListCtl", urlPatterns = { "/ctl/InventoryListCtl" })
public class InventoryListCtl extends BaseCtl {
	
	@Override
	protected void preload(HttpServletRequest request) {
		
		InventoryModel model = new InventoryModel();
		Map<Integer, String> map = new HashMap();

		map.put(1, "Leptop");
		map.put(2, "Mobile");
		map.put(3, "Ac ");
		request.setAttribute("ilnes", map);
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		InventoryBean bean = new InventoryBean();

bean.setId(DataUtility.getLong(request.getParameter("id")));
		
		bean.setSupplierName(DataUtility.getString(request.getParameter("supplierName")));
		bean.setLastUpdatedDate(DataUtility.getDate(request.getParameter("lastUpdatedDate")));
		bean.setQuantity(DataUtility.getLong(request.getParameter("quantity")));
		bean.setProduct(DataUtility.getString(request.getParameter("product")));

		return bean;
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;
		List nextList = null;

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		InventoryBean bean = (InventoryBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));
		//System.out.println(">>>>>>>>>>>>>>>helooo" + bean.getAppointmentDate());

		InventoryModel model = new InventoryModel();

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
		InventoryBean bean = (InventoryBean) populateBean(request);

		String[] ids = request.getParameterValues("ids");
		InventoryModel model = new InventoryModel();
		
      if (OP_SEARCH.equalsIgnoreCase(op)) {
			
			if(request.getParameter("supplierName").equals("")&& request.getParameter("lastUpdatedDate").equals("")
					&& request.getParameter("quantity").equals("")&& request.getParameter("product").equals("")) {
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

			ServletUtility.redirect(ORSView.INVENTORY_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.INVENTORY_LIST_CTL, request, response);
			return;

		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
				InventoryBean deletebean = new InventoryBean();
				for (String id : ids) {
					deletebean.setId(DataUtility.getInt(id));
					try {
						model.delete(deletebean);
					} catch (ApplicationException e) {

						ServletUtility.handleException(e, request, response);
						return;
					}

					ServletUtility.setSuccessMessage("Inventory is Deleted Successfully", request);
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
		return ORSView.INVENTORY_LIST_VIEW;
	}

}