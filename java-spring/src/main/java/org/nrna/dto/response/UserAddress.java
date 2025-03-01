package org.nrna.dto.response;

import org.nrna.dao.Address;

import java.io.Serializable;

public class UserAddress implements Serializable {

	private static final long serialVersionUID = 1L;

	private String city;
	private String state;
	private String zipCode;

	public UserAddress() {

	}

	public UserAddress(String city, String state, String zipCode) {
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}



	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public static UserAddress convertAddressToUserAddress(Address address){
		UserAddress userAddress = new UserAddress();
		userAddress.setCity(address.getCity());
		userAddress.setState(address.getState());
		userAddress.setZipCode(address.getZipCode());
		return userAddress;
	}
}
