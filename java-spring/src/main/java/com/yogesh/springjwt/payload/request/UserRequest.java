package com.yogesh.springjwt.payload.request;

import com.yogesh.springjwt.models.UserAddress;

public class UserRequest {
private static final long serialVersionUID = 1L;
	
	private Long id;
	private String password;
	private String email;	
	private int age;
	private String dob;
	private String gender;
	private String language;
	private String name;
	private String role;
	private String phoneNumber;
		
	public UserRequest() {}

	public UserRequest(String password, String email, int age, String dob, String gender,
			String language, String name, String role, String phoneNumber) {
		this.password = password;
		this.email = email;
		this.age = age;
		this.dob = dob;
		this.gender = gender;
		this.language = language;
		this.name = name;
		this.role = role;
		this.phoneNumber = phoneNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
