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
import com.rays.pro4.Bean.BankBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.BankModel;
import com.rays.pro4.Model.RoleModel;
import com.rays.pro4.Model.BankModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "BankCtl", urlPatterns = { "/ctl/BankCtl" })
public class BankCtl extends BaseCtl{
	
	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = Logger.getLogger(BankCtl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#preload(javax.servlet.http.
	 * HttpServletRequest)
	 */
	
	
	  @Override protected boolean validate(HttpServletRequest request) {
	  System.out.println("uctl Validate");
	  log.debug("BankCtl Method validate Started");
	  
	  boolean pass = true;
	  
	  if (DataValidator.isNull(request.getParameter("cName"))) {
	  request.setAttribute("cName", PropertyReader.getValue("error.require","Customer Name"));
	  pass = false;
	  } 
	  else if
	  (!DataValidator.isName(request.getParameter("cName"))) {
	  request.setAttribute("cName","First name must contains alphabet only");
	  pass = false; 
	  }
	  
	  if (DataValidator.isNull(request.getParameter("accu"))) {
	  request.setAttribute("accu", PropertyReader.getValue("error.require","Account ")); 
	  pass = false; 
	  }
	return pass; 
	  }
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.
	 * HttpServletRequest)
	 */

	protected BaseBean populateBean(HttpServletRequest request) {
		System.out.println(" uctl Base bean P bean");
		log.debug("BankCtl Method populatebean Started");

		BankBean bean = new BankBean();

		bean.setId(DataUtility.getLong(request.getParameter("cid")));
		  bean.setC_Name(DataUtility.getString(request.getParameter("cName")));
		  bean.setAccount(DataUtility.getString(request.getParameter("accu")));
		

		populateDTO(bean, request);

		log.debug("BankCtl Method populatebean Ended");

		return bean;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("BankCtl Method doGet Started");
		System.out.println("u ctl do get 1111111");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		BankModel model = new BankModel();
		long id = DataUtility.getLong(request.getParameter("cid"));
		if (id > 0 || op != null) {
			System.out.println("in id > 0  condition");
			BankBean bean;
			try {
				bean = model.findByPK(id);
				System.out.println("Ankit11111111111");
				System.out.println(bean);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		log.debug("BankCtl Method doGet Ended");
		
		ServletUtility.forward(getView(), request, response);
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("uctl Do Post");

		log.debug("BankCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("cid"));

		System.out.println(">>>><<<<>><<><<><<><>**********" + id + op);

		BankModel model = new BankModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			BankBean bean = (BankBean) populateBean(request);
			System.out.println(" U ctl DoPost 11111111");

			try {
				if (id > 0) {

					// System.out.println("hi i am in dopost update");
					model.update(bean);
					ServletUtility.setBean(bean, request);
					System.out.println(" U ctl DoPost 222222");
					ServletUtility.setSuccessMessage("User is successfully Updated", request);

				} else {
					System.out.println(" U ctl DoPost 33333");
					long pk = model.add(bean);
					// bean.setId(pk);
					// ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("User is successfully Added", request);
					//ServletUtility.forward(getView(), request, response);
					bean.setId(pk);
				}
				/*
				 * ServletUtility.setBean(bean, request);
				 * ServletUtility.setSuccessMessage("User is successfully saved", request);
				 */

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				System.out.println(" U ctl D post 4444444");
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			System.out.println(" U ctl D p 5555555");

			BankBean bean = (BankBean) populateBean(request);
			try {
				model.delete(bean);
				System.out.println(" u ctl D Post  6666666");
				ServletUtility.redirect(ORSView.BANK_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.BANK_LIST_CTL, request, response);
			return;
		}
		log.debug("BankCtl Method doPostEnded");
		ServletUtility.forward(getView(), request, response);


	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.BANK_VIEW;
	}
}
	
	