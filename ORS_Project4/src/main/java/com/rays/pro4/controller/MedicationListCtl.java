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
import com.rays.pro4.Bean.MedicationBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.MedicationModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "MedicationListCtl", urlPatterns = { "/ctl/MedicationListCtl" })
public class MedicationListCtl extends BaseCtl {
	@Override

	protected void preload(HttpServletRequest request) {
		MedicationModel model = new MedicationModel();
		Map <Integer,String > map = new HashMap();
		
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

		List list = null;
		List nextList = null;

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		MedicationBean bean = (MedicationBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));
		//System.out.println(">>>>>>>>>>>>>>>helooo" + bean.getDob());

		MedicationModel model = new MedicationModel();

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
		System.out.println("MedicationListCtl doPost Start");

		List list;
		List nextList = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println(DataUtility.getString(request.getParameter("operation")) + "oppppppppppppppppppppppppppppppppppppp");
		
		MedicationBean bean = (MedicationBean) populateBean(request);

		String[] ids = request.getParameterValues("ids");
		MedicationModel model = new MedicationModel();
		
		if (OP_SEARCH.equalsIgnoreCase(op)) {
			
			if(request.getParameter("fullName").equals("")&& request.getParameter("illness").equals("")
					&& request.getParameter("prescriptionDate").equals("")&& request.getParameter("dosage").equals("")) {
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

			ServletUtility.redirect(ORSView.MEDICATION_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MEDICATION_LIST_CTL, request, response);
			return;

		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
				MedicationBean deletebean = new MedicationBean();
				for (String id : ids) {
					deletebean.setId(DataUtility.getInt(id));
					try {
						model.delete(deletebean);
					} catch (ApplicationException e) {

						ServletUtility.handleException(e, request, response);
						return;
					}

					ServletUtility.setSuccessMessage("Medication is Deleted Successfully", request);
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
		return ORSView.MEDICATION_LIST_VIEW;
	}

}
