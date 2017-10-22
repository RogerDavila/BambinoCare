package com.bambinocare.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="client")
public class ClientEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_client",nullable=false, unique=true)
	private Integer idClient;
	
	@JoinColumn(name="id_user", nullable=false)
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private UserEntity user;
	
	@Column(name="employment", nullable=false)
	private String employment;
	
	@Column(name="street", nullable=false)
	private String street;
	
	@Column(name="suburb", nullable=false)
	private String suburb;
	
	@Column(name="town", nullable=false)
	private String town;
	
	@Column(name="state", nullable=false)
	private String state;
	
	public ClientEntity(Integer idClient, UserEntity user, String employment, String street, String suburb, String town,
			String state, byte[] ife) {
		super();
		this.idClient = idClient;
		this.user = user;
		this.employment = employment;
		this.street = street;
		this.suburb = suburb;
		this.town = town;
		this.state = state;
	}

	
	
	public ClientEntity() {
	}



	public Integer getIdClient() {
		return idClient;
	}

	public void setIdClient(Integer idClient) {
		this.idClient = idClient;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getEmployment() {
		return employment;
	}

	public void setEmployment(String employment) {
		this.employment = employment;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getSuburb() {
		return suburb;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
