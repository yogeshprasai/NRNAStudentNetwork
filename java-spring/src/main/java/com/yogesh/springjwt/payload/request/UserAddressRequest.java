package com.yogesh.springjwt.payload.request;

import com.yogesh.springjwt.models.User;
import com.yogesh.springjwt.models.UserAddress;

public class UserAddressRequest {

	private static final long serialVersionUID = 1L;

	public UserAddressRequest(Long userId, Long id, String name, String addLine1, String city, String state, String zipCode) {

		this.userId = userId;
		this.id = id;
		this.name = name;
		this.addLine1 = addLine1;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}

	public UserAddressRequest() {

	}
	
	private Long userId;
	
	private Long id;
	
	private String name;

	private String addLine1;

	private String city;

	private String state;

	private String zipCode;
	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddLine1() {
		return addLine1;
	}

	public void setAddLine1(String addLine1) {
		this.addLine1 = addLine1;
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
	
	
	public UserAddress userAddressRequestToUserAddress(User user, UserAddressRequest userAddressRequest) {
		
		UserAddress userAddress = new UserAddress();
		userAddress.setName(userAddressRequest.getName());
		userAddress.setAddLine1(userAddressRequest.getAddLine1());
		userAddress.setCity(userAddressRequest.getCity());
		userAddress.setState(userAddressRequest.getState());
		userAddress.setZipCode(userAddressRequest.getZipCode());
		userAddress.setUser(user);
		
		return userAddress;
	}
}
