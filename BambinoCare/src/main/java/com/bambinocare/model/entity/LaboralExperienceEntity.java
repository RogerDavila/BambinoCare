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
@Table(name="laboral_experience")
public class LaboralExperienceEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_laboral_experience",nullable=false, unique=true)
	private Integer idLaboralExperience;
	
	@JoinColumn(name="id_nanny", nullable=false)
	@OneToOne(fetch = FetchType.EAGER)
	private NannyEntity nanny;
	
	@Column(name="company", nullable=false)
	private String company;
	
	@Column(name="position", nullable=false)
	private String position;
	
	@Column(name="activity", nullable=false)
	private String activity;
	
	@Column(name="time_worked", nullable=false)
	private String timeWorked;

	public LaboralExperienceEntity(Integer idLaboralExperience, NannyEntity nanny, String company, String position,
			String activity, String timeWorked) {
		super();
		this.idLaboralExperience = idLaboralExperience;
		this.nanny = nanny;
		this.company = company;
		this.position = position;
		this.activity = activity;
		this.timeWorked = timeWorked;
	}

	public LaboralExperienceEntity() {
	}

	public Integer getIdLaboralExperience() {
		return idLaboralExperience;
	}

	public void setIdLaboralExperience(Integer idLaboralExperience) {
		this.idLaboralExperience = idLaboralExperience;
	}

	public NannyEntity getNanny() {
		return nanny;
	}

	public void setNanny(NannyEntity nanny) {
		this.nanny = nanny;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getTimeWorked() {
		return timeWorked;
	}

	public void setTimeWorked(String timeWorked) {
		this.timeWorked = timeWorked;
	}
	
}
