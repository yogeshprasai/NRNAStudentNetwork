package org.nrna.models;

import org.nrna.models.dto.User;

public class UserProfileAndAddress {

    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean showPhoneNumber;
    private boolean isHelper;
    private String profilePicture;
    private UserAddress userAddress;

    public UserProfileAndAddress(){

    }

    public UserProfileAndAddress(String firstName, String middleName, String lastName, String email, String phoneNumber, boolean isHelper, UserAddress userAddress) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.showPhoneNumber = showPhoneNumber;
        this.isHelper = isHelper;
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

    public boolean isHelper() {
        return isHelper;
    }

    public void setHelper(boolean helper) {
        isHelper = helper;
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

    public static UserProfileAndAddress userToUserProfileAndAddress(User user) {
        UserProfileAndAddress userProfileAndAddress = new UserProfileAndAddress();
        userProfileAndAddress.setFirstName(user.getFirstName());
        userProfileAndAddress.setMiddleName(user.getMiddleName());
        userProfileAndAddress.setLastName(user.getLastName());
        userProfileAndAddress.setEmail(user.getEmail());
        userProfileAndAddress.setPhoneNumber(user.getPhoneNumber());
        userProfileAndAddress.setShowPhoneNumber(user.isShowPhoneNumber());
        userProfileAndAddress.setHelper(user.isHelper());
        if(user.getProfilePicture() != null) {
            userProfileAndAddress.setProfilePicture(new String(user.getProfilePicture()));
        }
        if(user.getAddress() != null) {
            userProfileAndAddress.setUserAddress(UserAddress.converterAddressToUserAddress(user.getAddress()));
        }else {
            userProfileAndAddress.setUserAddress(null);
        }

        return userProfileAndAddress;
    }
}
