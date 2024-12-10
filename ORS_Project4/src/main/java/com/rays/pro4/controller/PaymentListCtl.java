package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.PaymentBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.PaymentModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "PaymentListCtl", urlPatterns = { "/ctl/PaymentListCtl" })
public class PaymentListCtl extends BaseCtl {

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		PaymentBean bean = new PaymentBean();

		bean.setId(DataUtility.getLong(request.getParameter("cid")));
		bean.setC_Name(DataUtility.getString(request.getParameter("cName")));
		bean.setAccount(DataUtility.getString(request.getParameter("accu")));

		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List list = null;
		List nextList = null;

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		PaymentBean bean = (PaymentBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));

		PaymentModel model = new PaymentModel();

		try {
			list = model.search(bean, pageNo, pageSize);
			System.out.println("list" + list);

			nextList = model.search(bean, pageNo + 1, pageSize);

			request.setAttribute("nextlist", nextList.size());

			ServletUtility.setList(list, request);

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
		PaymentBean bean = (PaymentBean) populateBean(request);
		
		String[] ids = request.getParameterValues("ids");
		PaymentModel model = new PaymentModel();
		

		
		 if (OP_SEARCH.equalsIgnoreCase(op)) {
			System.out.println("search chali");
			pageNo = 1;
		
	}else if(OP_DELETE.equalsIgnoreCase(op)){
		pageNo = 1;
		if (ids != null && ids.length > 0) {
			PaymentBean deletebean = new PaymentBean();
			for (String id : ids) {
				deletebean.setId(DataUtility.getInt(id));
				try {
					model.delete(deletebean);
				} catch (ApplicationException e) {

					ServletUtility.handleException(e, request, response);
					return;
				}

				ServletUtility.setSuccessMessage("Payment is Deleted Successfully", request);
			}
		} else {
			ServletUtility.setErrorMessage("Select at least one record", request);
		}
	}try
	{

		list = model.search(bean, pageNo, pageSize);

			
			  nextList = model.search(bean, pageNo + 1, pageSize);
			  
			  request.setAttribute("nextlist", nextList.size());
			 
	}catch(
	ApplicationException e)
	{

		ServletUtility.handleException(e, request, response);
		return;
	}if(list==null||list.size()==0&&!OP_DELETE.equalsIgnoreCase(op))
	{
		ServletUtility.setErrorMessage("No record found ", request);
	}ServletUtility.setList(list,request);ServletUtility.setBean(bean,request);ServletUtility.setPageNo(pageNo,request);ServletUtility.setPageSize(pageSize,request);

	ServletUtility.forward(getView(), request, response);
		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.PAYMENT_LIST_VIEW;
	}

}
