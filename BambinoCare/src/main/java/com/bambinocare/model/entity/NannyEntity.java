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
@Table(name="nanny")
public class NannyEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_nanny", unique=true, nullable=false)
	private Integer idNanny;
	
	@JoinColumn(name="id_user", nullable = false)
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private UserEntity user;
	
	@Column(name="age",nullable=false)
	private Integer age;
	
	@Column(name="street", nullable=false)
	private String street;
	
	@Column(name="suburb", nullable=false)
	private String suburb;
	
	@Column(name="town", nullable=false)
	private String town;
	
	@Column(name="state", nullable=false)
	private String state;
	
	@Column(name="study_level",nullable=false)
	private String studyLevel;
	
	@Column(name="study_place", nullable=false)
	private String studyPlace;

	@Column(name="capacitationCertification", nullable=false)
	private String capacitationCertification;
	
	@Column(name="qualitie", nullable=false)
	private String qualitie;
	
	@Column(name="hobby", nullable=false)
	private String hobby;
	
	@Column(name="bambino_reason", nullable=false)
	private String bambinoReason;
	
	@Column(name="children_reason", nullable=false)
	private String childrenReason;
	
	@Column(name="observation", nullable=false)
	private String observation;
	
	@Column(name="ife", nullable=false)
	private byte[] ife;
	
	@Column(name="curp", nullable=false)
	private byte[] curp;
	
	@Column(name="college_degree", nullable=false)
	private byte[] collegeDegree;

	
	
	public NannyEntity(Integer idNanny, UserEntity user, Integer age, String street, String suburb,
			String town, String state, String studyLevel, String studyPlace, String capacitationCertification,
			String qualitie, String hobby, String bambinoReason, String childrenReason, String observation, byte[] ife,
			byte[] curp, byte[] collegeDegree) {
		super();
		this.idNanny = idNanny;
		this.user = user;
		this.age = age;
		this.street = street;
		this.suburb = suburb;
		this.town = town;
		this.state = state;
		this.studyLevel = studyLevel;
		this.studyPlace = studyPlace;
		this.capacitationCertification = capacitationCertification;
		this.qualitie = qualitie;
		this.hobby = hobby;
		this.bambinoReason = bambinoReason;
		this.childrenReason = childrenReason;
		this.observation = observation;
		this.ife = ife;
		this.curp = curp;
		this.collegeDegree = collegeDegree;
	}
	
	
	public NannyEntity(UserEntity user) {
		super();
		this.user = user;
	}

	public NannyEntity() {
	}

	public Integer getIdNanny() {
		return idNanny;
	}

	public void setIdNanny(Integer idNanny) {
		this.idNanny = idNanny;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
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

	public String getStudyLevel() {
		return studyLevel;
	}

	public void setStudyLevel(String studyLevel) {
		this.studyLevel = studyLevel;
	}

	public String getStudyPlace() {
		return studyPlace;
	}

	public void setStudyPlace(String studyPlace) {
		this.studyPlace = studyPlace;
	}

	public String getCapacitationCertification() {
		return capacitationCertification;
	}

	public void setCapacitationCertification(String capacitationCertification) {
		this.capacitationCertification = capacitationCertification;
	}

	public String getQualitie() {
		return qualitie;
	}

	public void setQualitie(String qualitie) {
		this.qualitie = qualitie;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getBambinoReason() {
		return bambinoReason;
	}

	public void setBambinoReason(String bambinoReason) {
		this.bambinoReason = bambinoReason;
	}

	public String getChildrenReason() {
		return childrenReason;
	}

	public void setChildrenReason(String childrenReason) {
		this.childrenReason = childrenReason;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public byte[] getIfe() {
		return ife;
	}

	public void setIfe(byte[] ife) {
		this.ife = ife;
	}

	public byte[] getCurp() {
		return curp;
	}

	public void setCurp(byte[] curp) {
		this.curp = curp;
	}

	public byte[] getCollegeDegree() {
		return collegeDegree;
	}

	public void setCollegeDegree(byte[] collegeDegree) {
		this.collegeDegree = collegeDegree;
	}
	
}
