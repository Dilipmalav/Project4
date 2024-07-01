package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.BankBean;
import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.BankModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;
/**
 * The Class BankCtl.
 * 
 * @author Dilip Malav
 * 
 */
@WebServlet(name = "BankListCtl", urlPatterns = { "/ctl/BankListCtl" })
public class BankListCtl extends BaseCtl{
	

	
	private static Logger log = Logger.getLogger(BankListCtl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#preload(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	protected void preload(HttpServletRequest request) {

		
		BankModel umodel = new BankModel();

		try {
			
		List ulist = umodel.list(0,0);

		
		request.setAttribute("accu", ulist);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.
	 * HttpServletRequest)
	 */	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		BankBean bean = new BankBean();

		bean.setId(DataUtility.getLong(request.getParameter("cid")));
		bean.setC_Name(DataUtility.getString(request.getParameter("cName")));
		bean.setAccount(DataUtility.getString(request.getParameter("accu")));


		return bean;
	}

	/**
	 * Contains Display logics.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("BankListCtl doGet Start");
		List list = null;
		List nextList = null;

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		BankBean bean = (BankBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));

//	        get the selected checkbox ids array for delete list

		// String[] ids = request.getParameterValues("ids");
		BankModel model = new BankModel();

		try {
			list = model.search(bean, pageNo, pageSize);
			System.out.println("list" + list);

			nextList = model.search(bean, pageNo + 1, pageSize);

			request.setAttribute("nextlist", nextList.size());

			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			//ServletUtility.setBean(bean, request);
			
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("BankListCtl doGet End");
		
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Contains Submit logics.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("BankListCtl doPost Start");
		System.out.println("BankListCtl doPost Start");

		List list;
		List nextList = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		String op = DataUtility.getString(request.getParameter("operation"));
		BankBean bean = (BankBean) populateBean(request);
		// get the selected checkbox ids array for delete list
		String[] ids = request.getParameterValues("ids");
		BankModel model = new BankModel();
		System.out.println("op-new chali....");
		
		if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.BANK_CTL, request, response);
			return;
		} else if (OP_SEARCH.equalsIgnoreCase(op)) {
			System.out.println("search chali");
			pageNo = 1;
		}
		 
		else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;
			
		} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
			pageNo--;
		} 
		
		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.BANK_LIST_CTL, request, response);
			return;
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
				BankBean deletebean = new BankBean();
				for (String id : ids) {
					deletebean.setId(DataUtility.getInt(id));
					try {
						model.delete(deletebean);
					} catch (ApplicationException e) {
						log.error(e);
						ServletUtility.handleException(e, request, response);
						return;
					}

					ServletUtility.setSuccessMessage("User is Deleted Successfully", request);
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
			log.error(e);
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
		log.debug("BankListCtl doGet End");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.BANK_LIST_VIEW;
	}


}
