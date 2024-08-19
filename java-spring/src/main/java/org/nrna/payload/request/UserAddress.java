package org.nrna.payload.request;

public class UserAddress {

	private static final long serialVersionUID = 1L;

	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zipCode;

	public UserAddress(String addressLine1, String addressLine2, String city, String state, String zipCode) {
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}
}
