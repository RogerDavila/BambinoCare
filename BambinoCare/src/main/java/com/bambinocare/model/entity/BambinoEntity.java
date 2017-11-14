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
@Table(name = "bambino")
public class BambinoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bambino_id", nullable = false, unique = true)
	private Integer bambinoId;

	@JoinColumn(name = "client_id", nullable = false)
	@OneToOne(fetch = FetchType.EAGER)
	private ClientEntity client;

	@Column(name = "firstname", nullable = false)
	private String firstname;

	@Column(name = "lastname", nullable = false)
	private String lastname;

	@Column(name = "age", nullable = false)
	private Integer age;

	@Column(name = "medical_situation", nullable = false)
	private String medicalSituation;

	@Column(name = "grade")
	private String grade;

	@Column(name = "comments", nullable = false)
	private String comments;

	public BambinoEntity(Integer bambinoId, ClientEntity client, String firstname, String lastname, Integer age,
			String medicalSituation, String grade, String comments) {
		super();
		this.bambinoId = bambinoId;
		this.client = client;
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
		this.medicalSituation = medicalSituation;
		this.grade = grade;
		this.comments = comments;
	}

	public BambinoEntity() {
	}

	public Integer getBambinoId() {
		return bambinoId;
	}

	public void setBambinoId(Integer bambinoId) {
		this.bambinoId = bambinoId;
	}

	public ClientEntity getClient() {
		return client;
	}

	public void setClient(ClientEntity client) {
		this.client = client;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
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

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
