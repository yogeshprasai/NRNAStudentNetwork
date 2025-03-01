package org.nrna.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.nrna.dao.User;
import org.nrna.dto.response.UserAddress;

public class UserProfileAndAddress {

    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean showPhoneNumber;
    private boolean isStudent;
    private String university;
    private boolean isVolunteer;
    private boolean isApplyForVolunteer;
    private boolean isAdmin;
    private String profilePicture;
    private UserAddress userAddress;

    public UserProfileAndAddress(){

    }

    public UserProfileAndAddress(String firstName, String middleName, String lastName, String email,
                                    String phoneNumber, boolean isVolunteer, boolean isApplyForVolunteer, boolean isAdmin, UserAddress userAddress) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.showPhoneNumber = showPhoneNumber;
        this.isStudent = isStudent;
        this.university = university;
        this.isVolunteer = isVolunteer;
        this.isApplyForVolunteer = isApplyForVolunteer;
        this.isAdmin = isAdmin;
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

    @JsonGetter("isVolunteer")
    public boolean isVolunteer() {
        return isVolunteer;
    }

    @JsonSetter("isVolunteer")
    public void setVolunteer(boolean volunteer) {
        isVolunteer = volunteer;
    }

    @JsonGetter("isApplyForVolunteer")
    public boolean isApplyForVolunteer() {
        return isApplyForVolunteer;
    }

    @JsonSetter("isApplyForVolunteer")
    public void setApplyForVolunteer(boolean applyForVolunteer) {
        isApplyForVolunteer = applyForVolunteer;
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
        userProfileAndAddress.setStudent(user.isStudent());
        userProfileAndAddress.setUniversity(user.getUniversity());
        userProfileAndAddress.setVolunteer(user.isVolunteer());
        userProfileAndAddress.setApplyForVolunteer(user.isApplyForVolunteer());
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
