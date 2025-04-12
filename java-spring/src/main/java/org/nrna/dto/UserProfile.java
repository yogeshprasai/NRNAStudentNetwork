package org.nrna.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.nrna.dao.User;

import java.io.Serializable;

public class UserProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	private String firstName;
	private String middleName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String city;
	private String state;
	private String zipCode;
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
					   String city, String state, String zipCode,
					   boolean applyForVolunteer, boolean isVolunteer, boolean isAdmin) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
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
		userProfile.setCity(user.getAddress().getCity());
		userProfile.setState(user.getAddress().getState());
		userProfile.setZipCode(user.getAddress().getZipCode());
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

	@Override
	public String toString() {
		return "UserProfile { " +
				"firstName='" + firstName + '\'' +
				", middleName='" + middleName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", showPhoneNumber=" + showPhoneNumber +
				", isStudent=" + isStudent +
				", university='" + university + '\'' +
				", applyForVolunteer=" + applyForVolunteer +
				", isVolunteer=" + isVolunteer +
				", isAdmin=" + isAdmin +
				'}';
	}
}
