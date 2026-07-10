/**
 * 
 */
package com.pks.insurance.dao;

import java.util.List;

import com.pks.insurance.domain.User;
import com.pks.insurance.helper.UserLevels;

/**
 * Contain methods for interacting with Registration relation.
 * 
 * @author Pankaj Soni
 * 
 */
public interface UserDAO extends UserLevels {
	/**
	 * Create new user.
	 * 
	 * @param user
	 * @return boolean
	 */
	void saveUser(User user);

	/**
	 * Check whether user pre-exists in system.
	 * 
	 * @param user
	 * @return boolean
	 */
	boolean isUser(User user);

	/**
	 * Update user details.
	 * 
	 * @param user
	 */
	void updateUser(User user);

	/**
	 * Retrive user detail.
	 * 
	 * @param ssn
	 * @return User
	 */
	User getUser(String ssn);

	/**
	 * Retrives list of all the users.
	 * 
	 * @return List
	 */
	List<User> getUsers();

	/**
	 * Search similar users.
	 * 
	 * @param ssn
	 * @return List
	 */
	List<User> getUsers(String ssn);

	/**
	 * Deletes the user.
	 * 
	 * @param ssn
	 * @return total user deleted
	 */
	void removeUser(String ssn);

	/**
	 * User authentication.
	 * 
	 * @param user
	 * @return {@link UserLevels}
	 */
	int authenticateUser(User user);

}
