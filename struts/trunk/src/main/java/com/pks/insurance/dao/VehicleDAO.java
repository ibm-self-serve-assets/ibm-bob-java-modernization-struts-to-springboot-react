/**
 * 
 */
package com.pks.insurance.dao;

import com.pks.insurance.domain.Vehicle;

/**
 * Contain methods for interacting with Vehicle relation.
 * 
 * @author Pankaj Soni
 * 
 */
public interface VehicleDAO {

	/**
	 * Update or Save vehicle.
	 * 
	 * @param vehicle
	 */
	void saveVehicle(Vehicle vehicle);
}
