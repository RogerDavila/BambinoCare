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
@Table(name = "nanny")
public class NannyEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "nanny_id", unique = true, nullable = false)
	private Integer nannyId;

	@JoinColumn(name = "user_id", nullable = false)
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private UserEntity user;

	@Column(name = "age", nullable = false)
	private Integer age;

	@Column(name = "street", nullable = false)
	private String street;

	@Column(name = "neighborhood", nullable = false)
	private String neighborhood;

	@Column(name = "city", nullable = false)
	private String city;

	@Column(name = "state", nullable = false)
	private String state;

	@Column(name = "career", nullable = false)
	private String career;

	@Column(name = "school", nullable = false)
	private String school;

	@Column(name = "qualities", nullable = false)
	private String qualities;

	@Column(name = "hobbies", nullable = false)
	private String hobbies;

	@Column(name = "bambino_reason", nullable = false)
	private String bambinoReason;

	@Column(name = "ife_file", nullable = false)
	private byte[] ifeFile;

	@Column(name = "curp_file", nullable = false)
	private byte[] curpFile;

	@Column(name = "degree_file", nullable = true)
	private byte[] degreeFile;

	public NannyEntity(Integer nannyId, UserEntity user, Integer age, String street, String neighborhood, String city,
			String state, String career, String school, String course, String qualities, String hobbies,
			String bambinoReason, String childrenReason, String comments, byte[] ifeFile, byte[] curpFile,
			byte[] degreeFile) {
		super();
		this.nannyId = nannyId;
		this.user = user;
		this.age = age;
		this.street = street;
		this.neighborhood = neighborhood;
		this.city = city;
		this.state = state;
		this.career = career;
		this.school = school;
		this.qualities = qualities;
		this.hobbies = hobbies;
		this.bambinoReason = bambinoReason;
		this.ifeFile = ifeFile;
		this.curpFile = curpFile;
		this.degreeFile = degreeFile;
	}
	
	public NannyEntity(UserEntity user) {
		this.user = user;
	}

	public NannyEntity() {
	}

	public Integer getNannyId() {
		return nannyId;
	}

	public void setNannyId(Integer nannyId) {
		this.nannyId = nannyId;
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

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
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

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getQualities() {
		return qualities;
	}

	public void setQualities(String qualities) {
		this.qualities = qualities;
	}

	public String getHobbies() {
		return hobbies;
	}

	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	public String getBambinoReason() {
		return bambinoReason;
	}

	public void setBambinoReason(String bambinoReason) {
		this.bambinoReason = bambinoReason;
	}

	public byte[] getIfeFile() {
		return ifeFile;
	}

	public void setIfeFile(byte[] ifeFile) {
		this.ifeFile = ifeFile;
	}

	public byte[] getCurpFile() {
		return curpFile;
	}

	public void setCurpFile(byte[] curpFile) {
		this.curpFile = curpFile;
	}

	public byte[] getDegreeFile() {
		return degreeFile;
	}

	public void setDegreeFile(byte[] degreeFile) {
		this.degreeFile = degreeFile;
	}

}
