package org.nrna.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.nrna.models.dto.User;

import java.io.Serializable;
import java.util.Base64;

public class UserProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	private String firstName;
	private String middleName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private boolean isHelper;
	private String profilePicture;

	public UserProfile() {

	}

	public UserProfile(String firstName, String middleName, String lastName, String email, String phoneNumber, boolean isHelper) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.isHelper = isHelper;
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

	@JsonGetter("isHelper")
	public boolean isHelper() {
		return isHelper;
	}

	@JsonSetter("isHelper")
	public void setHelper(boolean helper) {
		isHelper = helper;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public static UserProfile userDetailsToUserProfile(User user) {
		UserProfile userProfile = new UserProfile();
		userProfile.setFirstName(user.getFirstName());
		userProfile.setMiddleName(user.getMiddleName());
		userProfile.setLastName(user.getLastName());
		userProfile.setEmail(user.getEmail());
		userProfile.setPhoneNumber(user.getPhoneNumber());
		userProfile.setHelper(user.isHelper());
		if(user.getProfilePicture() != null) {
			userProfile.setProfilePicture(new String(user.getProfilePicture()));
		}
		return userProfile;
	}
}
