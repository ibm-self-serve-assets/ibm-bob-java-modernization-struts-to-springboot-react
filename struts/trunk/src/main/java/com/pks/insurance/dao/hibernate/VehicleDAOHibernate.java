/**
 * 
 */
package com.pks.insurance.dao.hibernate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pks.insurance.dao.VehicleDAO;
import com.pks.insurance.domain.Vehicle;

/**
 * Implementation class for VehicleDAO.
 * 
 * @author Pankaj Soni
 * 
 */
@Repository("vehicleDao")
public class VehicleDAOHibernate implements VehicleDAO {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(VehicleDAOHibernate.class);

	@Autowired
	private HibernateTemplate template;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.pks.insurance.dao.VehicleDAO#saveVehicle(com.pks.insurance.domain.Vehicle)
	 */
	@Override
	@Transactional
	public final void saveVehicle(final Vehicle vehicle) {
		template.saveOrUpdate(vehicle);
	}

}
