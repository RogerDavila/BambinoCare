package com.bambinocare.model.entity;

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
@Table(name="emergency_contact")
public class EmergencyContactEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_contacto",nullable=false, unique=true)
	private Integer idContacto;
	
	@JoinColumn(name="id_client", nullable=false)
	@OneToOne(fetch = FetchType.EAGER)
	private ClientEntity client;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="lastname", nullable=false)
	private String lastname;
	
	@Column(name="telephone", nullable=false)
	private String telephone;
	
	@Column(name="street", nullable=false)
	private String street;
	
	@Column(name="suburb", nullable=false)
	private String suburb;
	
	@Column(name="town", nullable=false)
	private String town;
	
	@Column(name="state", nullable=false)
	private String state;
	
	@Column(name="relationship", nullable=false)
	private String relationship;

	public EmergencyContactEntity(Integer idContacto, ClientEntity client, String name, String lastname,
			String telephone, String street, String suburb, String town, String state, String relationship) {
		super();
		this.idContacto = idContacto;
		this.client = client;
		this.name = name;
		this.lastname = lastname;
		this.telephone = telephone;
		this.street = street;
		this.suburb = suburb;
		this.town = town;
		this.state = state;
		this.relationship = relationship;
	}

	public EmergencyContactEntity() {
	}

	public Integer getIdContacto() {
		return idContacto;
	}

	public void setIdContacto(Integer idContacto) {
		this.idContacto = idContacto;
	}

	public ClientEntity getClient() {
		return client;
	}

	public void setClient(ClientEntity client) {
		this.client = client;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}	

}
