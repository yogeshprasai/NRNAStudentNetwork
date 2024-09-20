package org.nrna.models.dto;

import org.hibernate.annotations.Type;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "users", 
		uniqueConstraints = {
			@UniqueConstraint(columnNames = "email") 
		})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	@Size(max = 32)
	private String firstName;

	@Size(max = 32)
	private String middleName;

	@Size(max = 32)
	private String lastName;
	
	@Size(max = 15)
	private String phoneNumber;

	private boolean showPhoneNumber;

	private boolean isHelper;

	@Lob
	@Type(type = "org.hibernate.type.ImageType")
	private byte[] profilePicture;
	
	@OneToOne(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Address address;
	
	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public @NotBlank @Size(max = 50) @Email String getEmail() {
		return email;
	}

	public void setEmail(@NotBlank @Size(max = 50) @Email String email) {
		this.email = email;
	}

	public @NotBlank @Size(max = 120) String getPassword() {
		return password;
	}

	public void setPassword(@NotBlank @Size(max = 120) String password) {
		this.password = password;
	}

	public @Size(max = 32) String getFirstName() {
		return firstName;
	}

	public void setFirstName(@Size(max = 32) String firstName) {
		this.firstName = firstName;
	}

	public @Size(max = 32) String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(@Size(max = 32) String middleName) {
		this.middleName = middleName;
	}

	public @Size(max = 32) String getLastName() {
		return lastName;
	}

	public void setLastName(@Size(max = 32) String lastName) {
		this.lastName = lastName;
	}

	public @Size(max = 15) String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(@Size(max = 15) String phoneNumber) {
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

	public byte[] getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
