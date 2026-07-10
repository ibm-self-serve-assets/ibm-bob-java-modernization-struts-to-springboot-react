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

import com.pks.insurance.domain.User;
import com.pks.insurance.helper.UserLevels;
import com.pks.insurance.service.UserManager;
@Component("/login")
public class LoginAction extends Action {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(LoginAction.class);
	private static final String ADMIN_SUCCESS = "adminSuccess";
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
		DynaActionForm loginForm = (DynaActionForm) form;
		User user = new User();
		String ssn = loginForm.getString("ssn");
		user.setSsn(ssn);
		user.setPasswd(loginForm.getString("passwd"));

		int authResult = userManager.authenticateUser(user);
		LOGGER.info("authResult: " + authResult);

		// For Normal Valid User
		if (authResult == UserLevels.VALID_USER) {
			// HttpSession session = request.getSession(false);
			// session.setAttribute("ssn", ssn);
			return mapping.findForward(SUCCESS);

		} else if (authResult == UserLevels.VALID_ADMIN) {
			// For Admin user
			// HttpSession session = request.getSession(false);
			// session.setAttribute("ssn", ssn);

			// Retrive list of all the users
			request.setAttribute("customers", userManager.getUsers());
			return mapping.findForward(ADMIN_SUCCESS);
		} else {
			// Display Error Message to the user for incorrect login credentials
			request.setAttribute("error",
					"The username or password you entered is incorrect.");
		}
		return mapping.findForward(FAILURE);
	}

}
