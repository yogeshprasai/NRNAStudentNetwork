package org.nrna.models.response;

import org.nrna.models.dto.UniversityOutreach;

import java.util.ArrayList;
import java.util.List;


public class UniversityOutreachResponse {
    private String fullName;
    private String phoneNumber;
    private String email;
    private String associatedUniversities;
    private String isNSU;

    public UniversityOutreachResponse(String fullName, String phoneNumber, String email, String associatedUniversities, String isNSU) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.associatedUniversities = associatedUniversities;
        this.isNSU = isNSU;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAssociatedUniversities() {
        return associatedUniversities;
    }

    public void setAssociatedUniversities(String associatedUniversities) {
        this.associatedUniversities = associatedUniversities;
    }

    public String getIsNSU() {
        return isNSU;
    }

    public void setIsNSU(String isNSU) {
        this.isNSU = isNSU;
    }

//    public static List<UniversityOutreachResponse> universityOutreachToResponse(List<UniversityOutreach> universityOutreaches){
//        List<UniversityOutreachResponse> universityOutreachResponses = new ArrayList<>();
//        universityOutreaches.forEach(universityOutreach -> {
//            UniversityOutreachResponse universityOutreachResponse = new UniversityOutreachResponse();
//            universityOutreachResponse.setFullName(universityOutreach.getFullName());
//            universityOutreachResponse.setPhoneNumber(universityOutreach.getPhoneNumber());
//            universityOutreachResponse.setEmail(universityOutreach.getEmail());
//            universityOutreachResponse.setAssociatedUniversities(universityOutreach.getAssociatedUniversities());
//            universityOutreachResponse.setIsNSU(universityOutreach.getIsNSU());
//            universityOutreachResponses.add(universityOutreachResponse);
//        });
//        return universityOutreachResponses;
//    }
}
