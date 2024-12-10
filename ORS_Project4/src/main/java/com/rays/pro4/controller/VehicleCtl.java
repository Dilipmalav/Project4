package com.rays.pro4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.VehicleBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.CustomerModel;
import com.rays.pro4.Model.RoleModel;
import com.rays.pro4.Model.VehicleModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

//TODO: Auto-generated Javadoc
/**
 * The Class VehicleCtl.
 * 
 * @author Dilip Malav
 * 
 */
@WebServlet(name = "VehicleCtl", urlPatterns = { "/ctl/VehicleCtl" })
public class VehicleCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = Logger.getLogger(VehicleCtl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#preload(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		System.out.println("uctl preload");
		
		VehicleModel model = new VehicleModel();
		Map<Integer, String> map = new HashMap();

		map.put(1, "red");
		map.put(2, "voilet");
		map.put(3, "black");
		map.put(4, "blue");
		map.put(5, "yellow");

		request.setAttribute("prolist", map);
		
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#validate(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");
		log.debug("VehicleCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("number"))) {
			request.setAttribute("number", PropertyReader.getValue("error.require", "number"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("number"))) {
			request.setAttribute("number", "number not contain specail charcter");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("purchaseDate"))) {
			request.setAttribute("purchaseDate", PropertyReader.getValue("error.require", "purchaseDate"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("purchaseDate"))) {
			request.setAttribute("purchaseDate", PropertyReader.getValue("error.date", "purchaseDate"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("insuranceAmount"))) {
			request.setAttribute("insuranceAmount", PropertyReader.getValue("error.require", "insuranceAmount"));
			pass = false;
		}

		else if (!DataValidator.isInteger(request.getParameter("insuranceAmount"))) {
			request.setAttribute("insuranceAmount", "insuranceAmount contain intger value only");
			pass = false;

		}

		if (DataValidator.isNull(request.getParameter("colour"))) {
			request.setAttribute("colour", PropertyReader.getValue("error.require", "division"));
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
		log.debug("VehicleCtl Method populatebean Started");

		VehicleBean bean = new VehicleBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
	

		bean.setInsuranceAmount(DataUtility.getInt(request.getParameter("insuranceAmount")));
		
		bean.setNumber(DataUtility.getString(request.getParameter("number")));

		bean.setColour(DataUtility.getString(request.getParameter("colour")));

		bean.setPurchaseDate(DataUtility.getDate(request.getParameter("purchaseDate")));
		//System.out.println("PurchaseDate" + bean.getPurchaseDate());

		populateDTO(bean, request);

		log.debug("VehicleCtl Method populatebean Ended");

		return bean;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */

	/**
	 *
	 */
	/**
	 *
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("VehicleCtl Method doGet Started");
		System.out.println("u ctl do get 1111111");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		VehicleModel model = new VehicleModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			System.out.println("in id > 0  condition");
			VehicleBean bean;
			try {
				bean = model.findByPK(id);
				System.out.println(bean);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);  
				return;
			}
		}
		log.debug("VehicleCtl Method doGet Ended");
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

		log.debug("VehicleCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println(id);

		System.out.println(">>>><<<<>><<><<><>**********" + id + op);

		VehicleModel model = new VehicleModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			VehicleBean bean = (VehicleBean) populateBean(request);
			System.out.println(" v ctl DoPost 11111111");

			try {
				if (id > 0) {
 
					// System.out.println("hi i am in dopost update");
					model.update(bean);
					ServletUtility.setBean(bean, request);
					System.out.println(" v ctl DoPost 222222");
					ServletUtility.setSuccessMessage("Vehicle is successfully Updated", request);

				} else {
					System.out.println(" v ctl DoPost 33333");
					long pk = model.add(bean);
					// bean.setId(pk);
					// ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("Vehicle is successfully Added", request);
					//ServletUtility.forward(getView(), request, response);
					bean.setId(pk);
				}
				/*
				 * ServletUtility.setBean(bean, request);
				 * ServletUtility.setSuccessMessage("Vehicle is successfully saved", request);
				 */

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				System.out.println(" v ctl D post 4444444");
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			System.out.println(" v ctl D p 5555555");

			VehicleBean bean = (VehicleBean) populateBean(request);
			try {
				model.delete(bean);
				System.out.println(" v ctl D Post  6666666");
				ServletUtility.redirect(ORSView.VEHICLE_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" v  ctl Do post 77777");

			ServletUtility.redirect(ORSView.VEHICLE_LIST_CTL, request, response);
			return;
		}
		log.debug("VehicleCtl Method doPostEnded");
		
		ServletUtility.forward(getView(), request, response);


	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.VEHICLE_VIEW;
	}

}
