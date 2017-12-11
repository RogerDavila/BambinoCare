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
	@Column(name="client_id",nullable=false, unique=true)
	private Integer clientId;
	
	@JoinColumn(name="user_id", nullable=false)
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private UserEntity user;
	
	@Column(name="job", nullable=false)
	private String job;
	
	@Column(name="street", nullable=false)
	private String street;
	
	@Column(name="neighborhood", nullable=false)
	private String neighborhood;
	
	@JoinColumn(name="city_id", nullable=false)
	@OneToOne(fetch = FetchType.EAGER)
	private CityEntity city;
	
	@JoinColumn(name="state_id", nullable=false)
	@OneToOne(fetch = FetchType.EAGER)
	private StateEntity state;

	public ClientEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ClientEntity(Integer clientId, UserEntity user, String job, String street, String neighborhood,
			CityEntity city, StateEntity state) {
		super();
		this.clientId = clientId;
		this.user = user;
		this.job = job;
		this.street = street;
		this.neighborhood = neighborhood;
		this.city = city;
		this.state = state;
	}

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public CityEntity getCity() {
		return city;
	}

	public void setCity(CityEntity city) {
		this.city = city;
	}

	public StateEntity getState() {
		return state;
	}

	public void setState(StateEntity state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "ClientEntity [clientId=" + clientId + ", user=" + user + ", job=" + job + ", street=" + street
				+ ", neighborhood=" + neighborhood + ", city=" + city + ", state=" + state + "]";
	}
	
	
	
}
