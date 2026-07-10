package com.pks.insurance.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pks.insurance.dao.UserDAO;
import com.pks.insurance.domain.User;
import com.pks.insurance.domain.Vehicle;
import com.pks.insurance.service.PremiumManager;

/**
 * Performs activities related to the Registration.
 * 
 * @author Pankaj Soni
 * 
 */
@Service("premiumManager")
public class PremiumManagerImpl implements PremiumManager {
	
	@Autowired
	private UserDAO userDAO;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PremiumManagerImpl.class);

	/**
	 * Business logic to retrive premium quote for a user
	 * 
	 * @param ssn
	 * @return quote for vehicle
	 */
	@Override
	public Map<String, Object> getQuote(String ssn) {
		LOGGER.debug("begin");
		User user = userDAO.getUser(ssn);
		// LOGGER.info("data retrived for quote: " + customer);
		Vehicle vehicle = user.getVehicle();
		if (vehicle.getType() == null || vehicle == null) {
			LOGGER.warn("No correct vehicle details present for the customer: "
					+ ssn);
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		long timediff = new Date().getTime() - user.getDateOfBirth().getTime();
		int convFactor = 1000 * 3600 * 24 * 365;
		float age = (float) timediff / convFactor;
		int totalAccident = vehicle.getTotalAccident();
		int policyAmount = vehicle.getPolicyAmount();
		float riskFactor = age / 210 + (float) totalAccident / 10;
		float premiumAnnually = policyAmount * riskFactor;
		float premiumQuarterly = premiumAnnually / 4;
		float premiumMonthly = premiumAnnually / 12;
		map.put("customer", user);
		map.put("premiumAnnually", String.valueOf((int) premiumAnnually));
		map.put("premiumQuarterly", String.valueOf((int) premiumQuarterly));
		map.put("premiumMonthly", String.valueOf((int) premiumMonthly));
		LOGGER.debug("map: " + map);
		LOGGER.debug("end");
		return map;
	}
}
