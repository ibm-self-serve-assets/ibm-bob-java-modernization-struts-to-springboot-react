package com.pks.insurance.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pks.insurance.service.UserManager;
@Component("/searchUser")
public class AdminSearchUserAction extends Action {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AdminSearchUserAction.class);
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
		DynaActionForm searchUserForm = (DynaActionForm) form;
		String ssn = searchUserForm.getString("ssn");

		// Retrive list of all the users matching given ssn
		if ("".equals(ssn)) {
			request.setAttribute("customers", userManager.getUsers());
			return mapping.findForward(SUCCESS);
		}
		request.setAttribute("customers", userManager.getUsers(ssn));
		return mapping.findForward(SUCCESS);
	}

}
