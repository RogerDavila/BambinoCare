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
@Table(name="personal_reference")
public class PersonalReferenceEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_reference",nullable=false, unique=true)
	private Integer idReference;
	
	@JoinColumn(name="id_nanny", nullable=false)
	@OneToOne(fetch = FetchType.EAGER)
	private NannyEntity nanny;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="lastname", nullable=false)
	private String lastname;
	
	@Column(name="telephone", nullable=false)
	private String telephone;
	
	@Column(name="time_meet", nullable=false)
	private String time_meet;	
	
	@Column(name="relationship", nullable=false)
	private String relationship;

	public PersonalReferenceEntity(Integer idReference, NannyEntity nanny, String name, String lastname,
			String telephone, String time_meet, String relationship) {
		super();
		this.idReference = idReference;
		this.nanny = nanny;
		this.name = name;
		this.lastname = lastname;
		this.telephone = telephone;
		this.time_meet = time_meet;
		this.relationship = relationship;
	}

	public PersonalReferenceEntity() {
	}

	public Integer getIdReference() {
		return idReference;
	}

	public void setIdReference(Integer idReference) {
		this.idReference = idReference;
	}

	public NannyEntity getNanny() {
		return nanny;
	}

	public void setNanny(NannyEntity nanny) {
		this.nanny = nanny;
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

	public String getTime_meet() {
		return time_meet;
	}

	public void setTime_meet(String time_meet) {
		this.time_meet = time_meet;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}	

}
