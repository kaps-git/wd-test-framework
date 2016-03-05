package com.korg.entity;

public class PaymentMethod {
	private String ccType;
	private String ccNumber;
	private String cvv;
	private String expiryMonth;
	private String expiryYear;
	
	public PaymentMethod(){
		//set default expiry month and year
		expiryMonth = "March";
		expiryYear = "2020";
	}
	
	/**
	 * @return the ccType
	 */
	public String getCcType() {
		return ccType;
	}
	/**
	 * @return the ccNumber
	 */
	public String getCcNumber() {
		return ccNumber;
	}
	/**
	 * @return the cvv
	 */
	public String getCvv() {
		return cvv;
	}
	/**
	 * @return the expiryMonth
	 */
	public String getExpiryMonth() {
		return expiryMonth;
	}
	/**
	 * @return the expiryYear
	 */
	public String getExpiryYear() {
		return expiryYear;
	}
	/**
	 * @param ccType the ccType to set
	 */
	public void setCcType(String ccType) {
		this.ccType = ccType;
	}
	/**
	 * @param ccNumber the ccNumber to set
	 */
	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}
	/**
	 * @param cvv the cvv to set
	 */
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	/**
	 * @param expiryMonth the expiryMonth to set
	 */
	public void setExpiryMonth(String expiryMonth) {
		this.expiryMonth = expiryMonth;
	}
	/**
	 * @param expiryYear the expiryYear to set
	 */
	public void setExpiryYear(String expiryYear) {
		this.expiryYear = expiryYear;
	}
}
