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
import com.rays.pro4.Bean.CompensationBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.CompensationModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "CompensationListCtl", urlPatterns = { "/ctl/CompensationListCtl" })
public class CompensationListCtl extends BaseCtl {
	@Override

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
		System.out.println("popullllllllllllllllllllllllllllllllllllll");

		CompensationBean bean = new CompensationBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setStaffMember(DataUtility.getString(request.getParameter("staffMember")));
		bean.setPaymemtAmount(DataUtility.getLong(request.getParameter("paymentAmount")));
		bean.setDateApplied(DataUtility.getDate(request.getParameter("dateApplied")));
		bean.setState(DataUtility.getString(request.getParameter("state")));
		System.out.println(bean.getState()+"stateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;
		List nextList = null;

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		CompensationBean bean = (CompensationBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));
		//System.out.println(">>>>>>>>>>>>>>>helooo" + bean.getDob());

		CompensationModel model = new CompensationModel();

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
		System.out.println("CompensationListCtl doPost Start");

		List list;
		List nextList = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println(DataUtility.getString(request.getParameter("operation")) + "oppppppppppppppppppppppppppppppppppppp");
		
		CompensationBean bean = (CompensationBean) populateBean(request);

		String[] ids = request.getParameterValues("ids");
		CompensationModel model = new CompensationModel();
		
		if (OP_SEARCH.equalsIgnoreCase(op)) {
			
			if(request.getParameter("staffMember").equals("")&& request.getParameter("paymentAmount").equals("")
					&& request.getParameter("dateApplied").equals("")&& request.getParameter("state").equals("")) {
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

			ServletUtility.redirect(ORSView.COMPENSATION_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COMPENSATION_LIST_CTL, request, response);
			return;

		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
				CompensationBean deletebean = new CompensationBean();
				for (String id : ids) {
					deletebean.setId(DataUtility.getInt(id));
					try {
						model.delete(deletebean);
					} catch (ApplicationException e) {

						ServletUtility.handleException(e, request, response);
						return;
					}

					ServletUtility.setSuccessMessage("Compensation is Deleted Successfully", request);
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
		return ORSView.COMPENSATION_LIST_VIEW;
	}

}
