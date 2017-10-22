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
@Table(name="bambino")
public class BambinoEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_bambino",nullable=false, unique=true)
	private Integer idBambino;
	
	@JoinColumn(name="id_client", nullable=false)
	@OneToOne(fetch = FetchType.EAGER)
	private ClientEntity client;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="lastname", nullable=false)
	private String lastname;
	
	@Column(name="age", nullable=false)
	private Integer age;
	
	@Column(name="medical_situation", nullable=false)
	private String medicalSituation;
	
	@Column(name="degree")
	private String degree;
	
	@Column(name="observation", nullable=false)
	private String observation;

	
	public BambinoEntity(Integer idBambino, ClientEntity client, String name, String lastname, Integer age,
			String medicalSituation, String degree, String observation) {
		super();
		this.idBambino = idBambino;
		this.client = client;
		this.name = name;
		this.lastname = lastname;
		this.age = age;
		this.medicalSituation = medicalSituation;
		this.degree = degree;
		this.observation = observation;
	}

	public BambinoEntity() {
	}

	public Integer getIdBambino() {
		return idBambino;
	}

	public void setIdBambino(Integer idBambino) {
		this.idBambino = idBambino;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getMedicalSituation() {
		return medicalSituation;
	}

	public void setMedicalSituation(String medicalSituation) {
		this.medicalSituation = medicalSituation;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}
	
}
