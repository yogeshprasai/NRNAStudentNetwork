package org.nrna.dto.response;

import org.nrna.dao.Address;

import java.io.Serializable;

public class UserAddress implements Serializable {

	private static final long serialVersionUID = 1L;

	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zipCode;

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
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

	public static UserAddress converterAddressToUserAddress(Address address){
		UserAddress userAddress = new UserAddress();
		userAddress.setAddressLine1(address.getAddressLine1());
		userAddress.setAddressLine2(address.getAddressLine2());
		userAddress.setCity(address.getCity());
		userAddress.setState(address.getState());
		userAddress.setZipCode(address.getZipCode());
		return userAddress;
	}
}
