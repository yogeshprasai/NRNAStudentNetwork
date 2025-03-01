package org.nrna.dto;

import org.nrna.dto.response.UserAddress;

public class UserProfileAndAddress {

    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean showPhoneNumber;
    private String university;
    private String profilePicture;
    private UserAddress userAddress;

    public UserProfileAndAddress(){

    }

    public UserProfileAndAddress(String firstName, String middleName, String lastName, String email, String phoneNumber, boolean showPhoneNumber, String university, String profilePicture, UserAddress userAddress) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.showPhoneNumber = showPhoneNumber;
        this.university = university;
        this.profilePicture = profilePicture;
        this.userAddress = userAddress;
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

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }
}
