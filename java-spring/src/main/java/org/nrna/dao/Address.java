package org.nrna.dao;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "address")
public class Address {

	private static final long serialVersionUID = 1L;

	public Address() {

	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Size(max = 20)
	@Column(name = "city")
	private String city;

	@Size(max = 20)
	@Column(name = "state")
	private String state;

	@Size(max = 20)
	@Column(name = "zip_code")
	private String zipCode;
	
	@OneToOne
	@JoinColumn(name="person_user_id", nullable=false)
	@JsonIgnore
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public @Size(max = 20) String getCity() {
		return city;
	}

	public void setCity(@Size(max = 20) String city) {
		this.city = city;
	}

	public @Size(max = 20) String getState() {
		return state;
	}

	public void setState(@Size(max = 20) String state) {
		this.state = state;
	}

	public @Size(max = 20) String getZipCode() {
		return zipCode;
	}

	public void setZipCode(@Size(max = 20) String zipCode) {
		this.zipCode = zipCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
