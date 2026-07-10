package com.pks.insurance.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Used to store Vehicle relation.
 * 
 * @author Pankaj Soni
 * 
 */
@Entity
@Table(name = "VEHICLE")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class Vehicle {
	/**
	 * Social service number of the citizen
	 */
	private String ssn;
	/**
	 * Specifies 2 for two wheeler and 4 for four wheeler
	 */
	private String type;
	/**
	 * Various models of the company
	 */
	private String model;
	/**
	 * Make is the company of vehicle
	 */
	private String make;
	/**
	 * Registration number of vehicle
	 */
	private String regNo;
	/**
	 * Type of insurance policy
	 */
	private String policyType;
	/**
	 * Amount of insurance policy
	 */
	private int policyAmount;
	/**
	 * Manufacturing year of the vehicle
	 */
	private String mfYear;
	/**
	 * No of accidents done by user
	 */
	private int totalAccident;
	/**
	 * Date on which quotation generated for the user
	 */
	private Date quoteDate;

	/**
	 * @return the totalAccident
	 */
	@Column(name = "TOTAL_ACCIDENT", precision = 1)
	public int getTotalAccident() {
		return this.totalAccident;
	}

	/**
	 * @param totalAccident
	 *            the totalAccident to set
	 */
	public void setTotalAccident(int totalAccident) {
		this.totalAccident = totalAccident;
	}

	/**
	 * @return the ssn
	 */
	@Id
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
	 * @return the regNo
	 */
	@Column(name = "REG_NO")
	public String getRegNo() {
		return this.regNo;
	}

	/**
	 * @param regNo
	 *            the regNo to set
	 */
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	/**
	 * @return the type
	 */
	@Column(name = "TYPE")
	public String getType() {
		return this.type;
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
		return this.model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	@Column(name = "MODEL")
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the make
	 */
	@Column(name = "MAKE")
	public String getMake() {
		return this.make;
	}

	/**
	 * @param make
	 *            the make to set
	 */
	public void setMake(String make) {
		this.make = make;
	}

	/**
	 * @return the policyType
	 */
	@Column(name = "POLICY_TYPE")
	public String getPolicyType() {
		return this.policyType;
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
	@Column(name = "POLICY_AMOUNT", precision = 6)
	public int getPolicyAmount() {
		return this.policyAmount;
	}

	/**
	 * @param policyAmount
	 *            the policyAmount to set
	 */
	public void setPolicyAmount(int policyAmount) {
		this.policyAmount = policyAmount;
	}

	/**
	 * @return the mfYear
	 */
	@Column(name = "MF_YEAR", length = 4)
	public String getMfYear() {
		return this.mfYear;
	}

	/**
	 * @param mfYear
	 *            the mfYear to set
	 */
	public void setMfYear(String mfYear) {
		this.mfYear = mfYear;
	}

	/**
	 * @return the quoteDate
	 */
	@Column(name = "QUOTE_DATE")
	@Temporal(TemporalType.DATE)
	public Date getQuoteDate() {
		return this.quoteDate;
	}

	/**
	 * @param quoteDate
	 *            the quoteDate to set
	 */
	public void setQuoteDate(Date quoteDate) {
		this.quoteDate = quoteDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleDto [ssn=");
		builder.append(this.ssn);
		builder.append(", type=");
		builder.append(this.type);
		builder.append(", model=");
		builder.append(this.model);
		builder.append(", make=");
		builder.append(this.make);
		builder.append(", regNo=");
		builder.append(this.regNo);
		builder.append(", policyType=");
		builder.append(this.policyType);
		builder.append(", policyAmount=");
		builder.append(this.policyAmount);
		builder.append(", mfYear=");
		builder.append(this.mfYear);
		builder.append(", totalAccident=");
		builder.append(this.totalAccident);
		builder.append(", quoteDate=");
		builder.append(this.quoteDate);
		builder.append("]");
		return builder.toString();
	}

}
