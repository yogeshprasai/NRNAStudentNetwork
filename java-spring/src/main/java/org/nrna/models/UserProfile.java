package org.nrna.models;

import org.nrna.models.dto.User;

import java.io.Serializable;

public class UserProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	private String firstName;
	private String middleName;
	private String lastName;
	private String email;
	private String phoneNumber;

	public UserProfile() {

	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public static UserProfile userDetailsToUserProfile(User user) {
		UserProfile userProfile = new UserProfile();
		userProfile.setFirstName(user.getFirstName());
		userProfile.setMiddleName(user.getMiddleName());
		userProfile.setLastName(user.getLastName());
		userProfile.setEmail(user.getEmail());
		userProfile.setPhoneNumber(user.getPhoneNumber());

		return userProfile;
	}
}
