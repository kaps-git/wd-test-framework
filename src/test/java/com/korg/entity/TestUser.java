package com.korg.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestUser {

	private String userid;
	private String yuid;
	private String password;
	private List<PaymentMethod> paymentMethodList;
	
	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}
	/**
	 * @return the yuid
	 */
	public String getYuid() {
		return yuid;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @return the paymentMethodList
	 */
	public List<PaymentMethod> getPaymentMethodList() {
		return paymentMethodList;
	}
	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	/**
	 * @param yuid the yuid to set
	 */
	public void setYuid(String yuid) {
		this.yuid = yuid;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @param paymentMethodList the paymentMethodList to set
	 */
	public void setPaymentMethodList(PaymentMethod[] paymentMethodArr) {
		this.paymentMethodList = Arrays.asList(paymentMethodArr);
	}
	
	public String toString() {
		String separator = "|";
		String val = "UserID: " + this.userid + separator +  
				     "YUID: " + this.yuid + separator + 
				     "Password: "+ this.password + separator + 
				     "# of PM: " + this.paymentMethodList.size();
		return val;		     
	}
}
