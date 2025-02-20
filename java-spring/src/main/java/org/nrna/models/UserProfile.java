package org.nrna.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.nrna.models.dto.User;

import java.io.Serializable;

public class UserProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	private String firstName;
	private String middleName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private boolean showPhoneNumber;
	private boolean isStudent;
	private String university;
	private boolean applyForVolunteer;
	private boolean isVolunteer;
	private String profilePicture;
	private boolean isAdmin;

	public UserProfile() {

	}

	public UserProfile(String firstName, String middleName, String lastName, String email, String phoneNumber,
					   boolean applyForVolunteer, boolean isVolunteer, boolean isAdmin) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.showPhoneNumber = showPhoneNumber;
		this.isStudent = isStudent;
		this.university = university;
		this.applyForVolunteer = applyForVolunteer;
		this.isVolunteer = isVolunteer;
		this.isAdmin = isAdmin;
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

	public boolean isShowPhoneNumber() {
		return showPhoneNumber;
	}

	public void setShowPhoneNumber(boolean showPhoneNumber) {
		this.showPhoneNumber = showPhoneNumber;
	}

	@JsonGetter("isStudent")
	public boolean isStudent() {
		return isStudent;
	}

	@JsonSetter("isStudent")
	public void setStudent(boolean student) {
		isStudent = student;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	@JsonGetter("isApplyForVolunteer")
	public boolean isApplyForVolunteer() {
		return applyForVolunteer;
	}

	@JsonSetter("isApplyForVolunteer")
	public void setApplyForVolunteer(boolean applyForVolunteer) {
		this.applyForVolunteer = applyForVolunteer;
	}

	@JsonGetter("isVolunteer")
	public boolean isVolunteer() {
		return isVolunteer;
	}

	@JsonSetter("isVolunteer")
	public void setVolunteer(boolean volunteer) {
		isVolunteer = volunteer;
	}

	@JsonGetter("isAdmin")
	public boolean isAdmin() {
		return isAdmin;
	}

	@JsonSetter("isAdmin")
	public void setAdmin(boolean admin) {
		isAdmin = admin;
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
		userProfile.setShowPhoneNumber(user.isShowPhoneNumber());
		userProfile.setStudent(user.isStudent());
		userProfile.setUniversity(user.getUniversity());
		userProfile.setApplyForVolunteer(user.isApplyForVolunteer());
		userProfile.setVolunteer(user.isVolunteer());
		userProfile.setAdmin(user.isAdmin());
		if(user.getProfilePicture() != null) {
			userProfile.setProfilePicture(new String(user.getProfilePicture()));
		}
		return userProfile;
	}
}
