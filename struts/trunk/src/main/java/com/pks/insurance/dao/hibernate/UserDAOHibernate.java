package com.pks.insurance.dao.hibernate;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pks.insurance.dao.UserDAO;
import com.pks.insurance.domain.User;
import com.pks.insurance.domain.Vehicle;

/**
 * Implementation class for RegistrationDAO
 * 
 * @author Pankaj Soni
 */
@Repository("userDao")
public class UserDAOHibernate implements UserDAO {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserDAOHibernate.class);

	@Autowired
	private HibernateTemplate template;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.pks.insurance.dao.UserDAO#saveUser(com.pks.insurance.domain.User)
	 */
	@Override
	@Transactional
	public final void saveUser(User user) {
		Vehicle vehicle = new Vehicle();
		vehicle.setSsn(user.getSsn());
		user.setVehicle(vehicle);
		template.saveOrUpdate(user);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.pks.insurance.dao.UserDAO#isUser(com.pks.insurance.domain.User)
	 */
	@Override
	public final boolean isUser(final User user) {
		if (getUser(user.getSsn()) != null)
			return true;
		else
			return false;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.pks.insurance.dao.UserDAO#updateUser(com.pks.insurance.domain.User)
	 */
	@Override
	@Transactional
	public final void updateUser(User user) {
		user.setVehicle(template.load(Vehicle.class, user.getSsn()));
		template.merge(user);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.pks.insurance.dao.UserDAO#getUser(java.lang.String)
	 */
	@Override
	public final User getUser(final String ssn) {
		return template.get(User.class, ssn);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.pks.insurance.dao.UserDAO#getUsers()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public final List<User> getUsers() {
		return template.find("from User");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.pks.insurance.dao.UserDAO#getUsers(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public final List<User> getUsers(final String ssn) {
		return template.find("from User where ssn like ?", "%" + ssn + "%");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.pks.insurance.dao.UserDAO#removeUser(java.lang.String)
	 */
	@Override
	@Transactional
	public final void removeUser(final String ssn) {
		Object user = template.load(User.class, ssn);
		if (user != null)
			template.delete(user);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.pks.insurance.dao.UserDAO#authenticateUser(com.pks.insurance.domain.User)
	 */
	@Override
	public final int authenticateUser(final User user) {
		final String sql = "from User where ssn=? AND passwd=?";
		@SuppressWarnings("rawtypes")
		List result = template.find(sql,
				new String[] { user.getSsn(), user.getPasswd() });
		if (result.size() == 0) {
			LOGGER.warn("INVALID_USER: " + result);
			return INVALID_USER;
		} else {
			if ("adminadmin".equals(user.getSsn())) {
				LOGGER.warn("VALID_ADMIN: " + result);
				return VALID_ADMIN;
			}
			LOGGER.warn("VALID_USER: " + result);
			return VALID_USER;
		}
	}
}
