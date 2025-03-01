package org.nrna.dto.response;

import org.nrna.dto.UserDetailsImpl;

public class LoginResponse {
	
	private static final long serialVersionUID = 1L;

	private String token;
	private Long id;
	private String email;
	
	public LoginResponse() {}

	public LoginResponse(Long id, String email) {
		this.id = id;
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static LoginResponse convertUserDetailsToUserResponse(UserDetailsImpl userDetails) {
		LoginResponse userResponse = new LoginResponse();
		userResponse.setId(userDetails.getId());
		userResponse.setEmail(userDetails.getEmail());
		return userResponse;
	}
	
}
