package com.pks.insurance.domain;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Used to store Registration relation. Customer has a Vehicle relationship.
 * 
 * @author Pankaj Soni
 * 
 */
@Entity
@Table(name = "CUSTOMER")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
public class User {
	/**
	 * Social service number of the citizen
	 */
	private String ssn;
	/**
	 * Confidential code used for authentication
	 */
	private String passwd;
	/**
	 * First Name of the user
	 */
	private String firstName;
	/**
	 * Last Name of the user
	 */
	private String lastName;
	/**
	 * Gender specifies M for male and F for female
	 */
	private String gender;
	/**
	 * Date of Birth of the user
	 */
	private java.util.Date dateOfBirth;
	/**
	 * Mobile number of user
	 */
	private String mobileNo;
	/**
	 * E Mail of user
	 */
	private String email;
	/**
	 * City of user
	 */
	private String city;
	/**
	 * Blood group of user
	 */
	private String bloodGroup;
	/**
	 * Unique driving licence number of user
	 */
	private String drivingLicence;

	private Integer enabled;
	/**
	 * Vehicle information of user
	 */
	private Vehicle vehicle;

	/**
	 * @return the ssn
	 */
	@Id
	@Column(name = "SSN", length = 20, nullable = false)
	public String getSsn() {
		return this.ssn;
	}

	/**
	 * @param ssn
	 *            the ssn to set
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	/**
	 * @return the passwd
	 */
	@Column(name = "PASSWD", length = 20)
	public String getPasswd() {
		return this.passwd;
	}

	/**
	 * @param passwd
	 *            the passwd to set
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * @return the firstName
	 */
	@Column(name = "FNAME")
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	@Column(name = "LNAME")
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the gender
	 */
	@Column(name = "GENDER", length = 1)
	public String getGender() {
		return this.gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the dateOfBirth
	 */
	@Column(name = "DOB")
	@Temporal(TemporalType.DATE)
	public java.util.Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	/**
	 * @param dateOfBirth
	 *            the dateOfBirth to set
	 */
	public void setDateOfBirth(java.util.Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the mobileNo
	 */
	@Column(name = "mobileno", length = 10)
	public String getMobileNo() {
		return this.mobileNo;
	}

	/**
	 * @param mobileNo
	 *            the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @return the email
	 */
	@Column(name = "EMAIL")
	public String getEmail() {
		return this.email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the city
	 */
	@Column(name = "CITY")
	public String getCity() {
		return this.city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the bloodGroup
	 */
	@Column(name = "BLOODGROUP")
	public String getBloodGroup() {
		return this.bloodGroup;
	}

	/**
	 * @param bloodGroup
	 *            the bloodGroup to set
	 */
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	/**
	 * @return the drivingLicence
	 */
	@Column(name = "DL")
	public String getDrivingLicence() {
		return this.drivingLicence;
	}

	/**
	 * @param drivingLicence
	 *            the drivingLicence to set
	 */
	public void setDrivingLicence(String drivingLicence) {
		this.drivingLicence = drivingLicence;
	}

	/**
	 * @return the enabled
	 */
	public Integer getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	/**
	 * @param vehicle
	 *            the vehicle to set
	 */
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	/**
	 * @return the vehicle
	 */
	@OneToOne(cascade = CascadeType.ALL)
	@Basic(fetch = FetchType.LAZY)
	public Vehicle getVehicle() {
		return this.vehicle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CustomerDto [ssn=");
		builder.append(this.ssn);
		builder.append(", passwd=");
		builder.append(this.passwd);
		builder.append(", firstName=");
		builder.append(this.firstName);
		builder.append(", lastName=");
		builder.append(this.lastName);
		builder.append(", gender=");
		builder.append(this.gender);
		builder.append(", dateOfBirth=");
		builder.append(this.dateOfBirth);
		builder.append(", mobileNo=");
		builder.append(this.mobileNo);
		builder.append(", email=");
		builder.append(this.email);
		builder.append(", city=");
		builder.append(this.city);
		builder.append(", bloodGroup=");
		builder.append(this.bloodGroup);
		builder.append(", drivingLicence=");
		builder.append(this.drivingLicence);
		builder.append(",vehicle=");
		builder.append(this.vehicle);
		builder.append("]");
		return builder.toString();
	}

}
