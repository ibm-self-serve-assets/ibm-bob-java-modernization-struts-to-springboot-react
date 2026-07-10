package com.pks.insurance.service;

import java.util.List;

import com.pks.insurance.domain.User;
import com.pks.insurance.domain.Vehicle;
import com.pks.insurance.helper.UserLevels;

public interface UserManager {

	/**
	 * Performs registration of the user.
	 * 
	 * @param user
	 * @return boolean
	 */
	User saveUser(User user);

	/**
	 * Checks for existing user.
	 * 
	 * @param user
	 * @return boolean
	 */
	boolean isUser(User user);

	/**
	 * Returns user information
	 * 
	 * @param ssn
	 * @return User
	 */
	User getUser(String ssn);

	/**
	 * Registers vehicle of user.
	 * 
	 * @param vehicle
	 * @return boolean
	 */
	Vehicle saveVehicle(Vehicle vehicle);

	/**
	 * Update user info.
	 * 
	 * @param user
	 * @return boolean
	 */
	void updateUser(User user);

	/**
	 * Perform users login
	 * 
	 * @param user
	 * @return UserLevel for more info {@link UserLevels}
	 */
	int authenticateUser(User user);

	List<User> getUsers();

	List<User> getUsers(String ssn);

	void removeUser(String ssn);

}