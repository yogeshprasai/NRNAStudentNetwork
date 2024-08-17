package com.yogesh.springjwt.payload.response;

import java.util.List;
import com.yogesh.springjwt.models.User;
import com.yogesh.springjwt.models.UserAddress;
import com.yogesh.springjwt.security.services.UserDetailsImpl;

public class UserResponse {
	
	private static final long serialVersionUID = 1L;
	
	private String token;
	private String type = "Bearer";
	private Long id;
	private String password;
	private String email;	
	private int age;
	private String dob;
	private String gender;
	private String language;
	private String name;
	private String role;
	private String phoneNumber;
	private List<UserAddress> userAddress;
	
	
	public UserResponse() {}

	public UserResponse(String token, Long id, String password, String email, int age,
			String dob, String gender, String language, String name,
			String phoneNumber, List<UserAddress> userAddress) {
		this.token = token;
		this.id = id;
		this.password = password;
		this.email = email;
		this.age = age;
		this.dob = dob;
		this.gender = gender;
		this.language = language;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.userAddress = userAddress;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	

	public List<UserAddress> getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(List<UserAddress> userAddress) {
		this.userAddress = userAddress;
	}

	public static UserResponse userToUserResponse(User user) {
		UserResponse userResponse = new UserResponse();
		userResponse.setAge(user.getAge());
		userResponse.setDob(user.getDob());
		userResponse.setEmail(user.getEmail());
		userResponse.setGender(user.getGender());
		userResponse.setId(user.getId());
		userResponse.setLanguage(user.getLanguage());
		userResponse.setName(user.getName());
		userResponse.setRole(user.getName());
		userResponse.setPassword(user.getPassword());
		userResponse.setPhoneNumber(user.getPhoneNumber());
		userResponse.setUserAddress(user.getUserAddress());
		
		return userResponse;
	}
	
	public static UserResponse userDetailsToUserResponse(String jwt, UserDetailsImpl userDetails) {
		UserResponse userResponse = new UserResponse();
		userResponse.setToken(jwt);
		userResponse.setId(userDetails.getId());
		userResponse.setRole(userDetails.getRole());
		userResponse.setEmail(userDetails.getEmail());
		
		return userResponse;
	}
	
}
