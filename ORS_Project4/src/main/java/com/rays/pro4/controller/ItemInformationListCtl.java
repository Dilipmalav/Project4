
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
import com.rays.pro4.Bean.ItemInformationBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.ItemInformationModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "ItemInformationListCtl", urlPatterns = { "/ctl/ItemInformationListCtl" })
public class ItemInformationListCtl extends BaseCtl {
	
	@Override
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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;
		List nextList = null;

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		ItemInformationBean bean = (ItemInformationBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));
		//System.out.println(">>>>>>>>>>>>>>>helooo" + bean.getAppointmentDate());

		ItemInformationModel model = new ItemInformationModel();

		try {
			list = model.search(bean, pageNo, pageSize);
			System.out.println("list" + list);

			nextList = model.search(bean, pageNo + 1, pageSize);

			request.setAttribute("nextlist", nextList.size());

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
		    ServletUtility.setBean(bean, request);

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
		ItemInformationBean bean = (ItemInformationBean) populateBean(request);

		String[] ids = request.getParameterValues("ids");
		ItemInformationModel model = new ItemInformationModel();
		
		 if (OP_SEARCH.equalsIgnoreCase(op)) {
				
				if(request.getParameter("title").equals("")&& request.getParameter("cost").equals("")
						&& request.getParameter("purchaseDate").equals("")&& request.getParameter("category").equals("")) {
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

			ServletUtility.redirect(ORSView.ITEMINFORMATION_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ITEMINFORMATION_LIST_CTL, request, response);
			return;

		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
				ItemInformationBean deletebean = new ItemInformationBean();
				for (String id : ids) {
					deletebean.setId(DataUtility.getInt(id));
					try {
						model.delete(deletebean);
					} catch (ApplicationException e) {

						ServletUtility.handleException(e, request, response);
						return;
					}

					ServletUtility.setSuccessMessage("ItemInformation is Deleted Successfully", request);
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
		return ORSView.ITEMINFORMATION_LIST_VIEW;
	}

}