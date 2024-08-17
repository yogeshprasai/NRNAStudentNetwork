package com.yogesh.springjwt.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "address")
public class UserAddress {

	private static final long serialVersionUID = 1L;

	public UserAddress(String name, String addLine1, String city, String state, String zipCode, User user) {
		this.name = name;
		this.addLine1 = addLine1;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.user = user;
	}

	public UserAddress() {

	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(max = 50)
	private String name;

	@Size(max = 50)
	private String addLine1;

	@Size(max = 20)
	private String city;

	@Size(max = 20)
	private String state;

	@Size(max = 20)
	private String zipCode;
	
	@ManyToOne
	@JoinColumn(name="person_user_id", nullable=false)
	@JsonIgnore
	private User user;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddLine1() {
		return addLine1;
	}

	public void setAddLine1(String addLine1) {
		this.addLine1 = addLine1;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
