package com.pks.insurance.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pks.insurance.domain.Vehicle;
import com.pks.insurance.form.VehicleActionForm;
import com.pks.insurance.service.UserManager;
@Component("/vehicleRegister")
public class RegisterVehicleAction extends Action {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RegisterVehicleAction.class);
	private static final String SUCCESS = "success";
	private static final String FAILURE = "failure";
	@Autowired
	private UserManager userManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.
	 * ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		VehicleActionForm vehicleForm = (VehicleActionForm) form;
		HttpSession session = request.getSession(false);
		Vehicle vehicle = new Vehicle();
		BeanUtils.copyProperties(vehicle, vehicleForm);
		vehicle.setSsn((String) session.getAttribute("ssn"));
		vehicle.setQuoteDate(new Date());
		LOGGER.info(":vehicleDto:" + vehicle);
		vehicle = userManager.saveVehicle(vehicle);
		LOGGER.info("::::vehicleRegistered::::" + vehicle);
		return mapping.findForward(SUCCESS);
	}

}
