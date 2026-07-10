package com.pks.insurance.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pks.insurance.dao.UserDAO;
import com.pks.insurance.dao.VehicleDAO;
import com.pks.insurance.domain.User;
import com.pks.insurance.domain.Vehicle;
import com.pks.insurance.service.UserManager;

/**
 * Performs activities related to the Registration.
 * 
 * @author Pankaj Soni
 * 
 */
@Service("userManager")
public class UserManagerImpl implements UserManager {

	// private static final Logger LOGGER = LoggerFactory
	// .getLogger(UserManagerImpl.class);
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private VehicleDAO vehicleDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pks.insurance.service.impl.UserManager#saveUser(com.pks.insurance
	 * .domain.User)
	 */
	@Override
	public User saveUser(User user) {
		userDAO.saveUser(user);
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pks.insurance.service.impl.UserManager#isUser(com.pks.insurance.domain
	 * .User)
	 */
	@Override
	public boolean isUser(User user) {
		return userDAO.isUser(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pks.insurance.service.impl.UserManager#getUser(java.lang.String)
	 */
	@Override
	public User getUser(String ssn) {
		return userDAO.getUser(ssn);
	}

	@Override
	public List<User> getUsers() {
		return userDAO.getUsers();
	}

	@Override
	public List<User> getUsers(String ssn) {
		return userDAO.getUsers(ssn);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pks.insurance.service.impl.UserManager#saveVehicle(com.pks.insurance
	 * .domain.Vehicle)
	 */
	@Override
	public Vehicle saveVehicle(Vehicle vehicle) {
		vehicleDAO.saveVehicle(vehicle);
		return vehicle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pks.insurance.service.impl.UserManager#updateUser(com.pks.insurance
	 * .domain.User)
	 */
	@Override
	public void updateUser(User user) {
		userDAO.updateUser(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pks.insurance.service.impl.UserManager#authenticateUser(com.pks.insurance
	 * .domain.User)
	 */
	@Override
	public int authenticateUser(User user) {
		return userDAO.authenticateUser(user);
	}

	@Override
	public void removeUser(String ssn) {
		userDAO.removeUser(ssn);
	}


}
