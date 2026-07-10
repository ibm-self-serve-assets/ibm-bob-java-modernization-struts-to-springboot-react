package com.pks.insurance.form;

import org.apache.struts.validator.ValidatorActionForm;

public class VehicleActionForm extends ValidatorActionForm {

	private static final long serialVersionUID = -5515707060157548393L;

	private String type;
	private String model;
	private String make;
	private String regNo;
	private String policyType;
	private String policyAmount;
	private String mfYear;
	private int totalAccident;

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the make
	 */
	public String getMake() {
		return make;
	}

	/**
	 * @param make
	 *            the make to set
	 */
	public void setMake(String make) {
		this.make = make;
	}

	/**
	 * @return the regNo
	 */
	public String getRegNo() {
		return regNo;
	}

	/**
	 * @param regNo
	 *            the regNo to set
	 */
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	/**
	 * @return the policyType
	 */
	public String getPolicyType() {
		return policyType;
	}

	/**
	 * @param policyType
	 *            the policyType to set
	 */
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	/**
	 * @return the policyAmount
	 */
	public String getPolicyAmount() {
		return policyAmount;
	}

	/**
	 * @param policyAmount
	 *            the policyAmount to set
	 */
	public void setPolicyAmount(String policyAmount) {
		this.policyAmount = policyAmount;
	}

	/**
	 * @return the mfYear
	 */
	public String getMfYear() {
		return mfYear;
	}

	/**
	 * @param mfYear
	 *            the mfYear to set
	 */
	public void setMfYear(String mfYear) {
		this.mfYear = mfYear;
	}

	/**
	 * @return the totalAccident
	 */
	public int getTotalAccident() {
		return totalAccident;
	}

	/**
	 * @param totalAccident
	 *            the totalAccident to set
	 */
	public void setTotalAccident(int totalAccident) {
		this.totalAccident = totalAccident;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleActionForm [type=");
		builder.append(type);
		builder.append(", model=");
		builder.append(model);
		builder.append(", make=");
		builder.append(make);
		builder.append(", regNo=");
		builder.append(regNo);
		builder.append(", policyType=");
		builder.append(policyType);
		builder.append(", policyAmount=");
		builder.append(policyAmount);
		builder.append(", mfYear=");
		builder.append(mfYear);
		builder.append(", totalAccident=");
		builder.append(totalAccident);
		builder.append("]");
		return builder.toString();
	}

}
