package org.nrna.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="university_outreach")
public class UniversityOutreach {

    @Id
    private int id;

    @Column(name="full_name")
    private String fullName;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="email")
    private String email;

    @Column(name="associated_universities")
    private String associatedUniversities;

    @Column(name="is_nsu")
    private String isNSU;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
