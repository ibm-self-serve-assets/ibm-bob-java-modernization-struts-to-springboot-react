package com.pks.insurance.form;

import org.apache.struts.validator.ValidatorActionForm;

public class UserActionForm extends ValidatorActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7969586693461014126L;
	private String ssn;
	private String passwd;
	private String cpasswd;
	private String firstName;
	private String lastName;
	private String gender;
	private String mobileNo;
	private String email;
	private String city;
	private String bloodGroup;
	private String drivingLicence;
	private String dateOfBirthString;

	/**
	 * @return the ssn
	 */
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
	 * @return the passwd
	 */
	public String getPasswd() {
		return passwd;
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
	public String getFirstName() {
		return firstName;
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
	public String getLastName() {
		return lastName;
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
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param mobileNo
	 *            the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
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
	public String getBloodGroup() {
		return bloodGroup;
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
	public String getDrivingLicence() {
		return drivingLicence;
	}

	/**
	 * @param drivingLicence
	 *            the drivingLicence to set
	 */
	public void setDrivingLicence(String drivingLicence) {
		this.drivingLicence = drivingLicence;
	}

	/**
	 * @return the dateOfBirthString
	 */
	public String getDateOfBirthString() {
		return dateOfBirthString;
	}

	/**
	 * @param dateOfBirthString
	 *            the dateOfBirthString to set
	 */
	public void setDateOfBirthString(String dateOfBirthString) {
		this.dateOfBirthString = dateOfBirthString;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserActionForm [ssn=");
		builder.append(ssn);
		builder.append(", passwd=");
		builder.append(passwd);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", gender=");
		builder.append(gender);
		builder.append(", mobileNo=");
		builder.append(mobileNo);
		builder.append(", email=");
		builder.append(email);
		builder.append(", city=");
		builder.append(city);
		builder.append(", bloodGroup=");
		builder.append(bloodGroup);
		builder.append(", drivingLicence=");
		builder.append(drivingLicence);
		builder.append(", dateOfBirthString=");
		builder.append(dateOfBirthString);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @return the cpasswd
	 */
	public String getCpasswd() {
		return cpasswd;
	}

	/**
	 * @param cpasswd
	 *            the cpasswd to set
	 */
	public void setCpasswd(String cpasswd) {
		this.cpasswd = cpasswd;
	}

}
