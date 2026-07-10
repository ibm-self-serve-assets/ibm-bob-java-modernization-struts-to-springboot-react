package com.pks.insurance.action;

import java.text.SimpleDateFormat;
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

import com.pks.insurance.domain.User;
import com.pks.insurance.form.UserActionForm;
import com.pks.insurance.service.UserManager;

public class UpdateUserAction extends Action {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UpdateUserAction.class);
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
		UserActionForm userForm = (UserActionForm) form;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd");
		Date dateOfBirth = null;
		/*
		 * try { dateOfBirth = dateFormat.parse(dateOfBirthString); } catch
		 * (ParseException e) { LOGGER.error(e.getMessage()); }
		 */

		User user = new User();
		BeanUtils.copyProperties(user, userForm);

		LOGGER.info("regDto:" + user);
		userManager.updateUser(user);
		LOGGER.info("isUserReg: " + user);

		// Set ssn in session and open home page
		HttpSession session = request.getSession(false);
		session.setAttribute("ssn", user.getSsn());
		return mapping.findForward(SUCCESS);
	}

}
