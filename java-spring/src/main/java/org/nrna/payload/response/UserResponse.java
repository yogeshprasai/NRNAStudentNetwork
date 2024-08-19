package org.nrna.payload.response;

import java.util.List;
import org.nrna.models.User;
import org.nrna.models.UserAddress;
import org.nrna.security.services.UserDetailsImpl;

public class UserResponse {
	
	private static final long serialVersionUID = 1L;
	
	private String token;
	private String type = "Bearer";
	private Long id;
	private String password;
	private String email;
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
		this.phoneNumber = phoneNumber;
		this.userAddress = userAddress;
	}

	public UserResponse(String token, String type, Long id, String password, String email, String role, String phoneNumber, List<UserAddress> userAddress) {
		this.token = token;
		this.type = type;
		this.id = id;
		this.password = password;
		this.email = email;
		this.role = role;
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

	public static UserResponse userDetailsToUserResponse(String jwt, UserDetailsImpl userDetails) {
		UserResponse userResponse = new UserResponse();
		userResponse.setToken(jwt);
		userResponse.setId(userDetails.getId());
		userResponse.setRole(userDetails.getRole());
		userResponse.setEmail(userDetails.getEmail());
		
		return userResponse;
	}
	
}
