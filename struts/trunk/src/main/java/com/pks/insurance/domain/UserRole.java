package com.pks.insurance.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserRole {

	private String ssn;
	private String authority;

	/**
	 * @return the ssn
	 */
	@Id
	public String getSsn() {
		return ssn;
	}

	/**
	 * @param ssn
	 *            the ssn to set
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	/**
	 * @return the authority
	 */
	public String getAuthority() {
		return authority;
	}

	/**
	 * @param authority
	 *            the authority to set
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
