package org.nrna.models;

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
	
	@Size(max = 32)
	private String role;
	
	@Size(max = 15)
	private String phoneNumber;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<UserAddress> userAddress;
	
	public User() {
	}

	public User(Long id, String email, String password, String firstName, String middleName, String lastName, String role, String phoneNumber, List<UserAddress> userAddress) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.role = role;
		this.phoneNumber = phoneNumber;
		this.userAddress = userAddress;
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

	public @Size(max = 32) String getRole() {
		return role;
	}

	public void setRole(@Size(max = 32) String role) {
		this.role = role;
	}

	public @Size(max = 15) String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(@Size(max = 15) String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<UserAddress> getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(List<UserAddress> userAddress) {
		this.userAddress = userAddress;
	}
}
