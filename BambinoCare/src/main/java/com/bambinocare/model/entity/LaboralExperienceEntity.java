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
	@Column(name="laboral_experience_id",nullable=false, unique=true)
	private Integer laboralExperienceId;
	
	@JoinColumn(name="nanny_id", nullable=false)
	@OneToOne(fetch = FetchType.EAGER)
	private NannyEntity nanny;
	
	@Column(name="organization", nullable=false)
	private String organization;
	
	@Column(name="job_title", nullable=false)
	private String jobTitle;
	
	@Column(name="activities", nullable=false)
	private String activities;
	
	@Column(name="time_worked", nullable=false)
	private String timeWorked;

	public LaboralExperienceEntity(Integer laboralExperienceId, NannyEntity nanny, String organization, String jobTitle,
			String activities, String timeWorked) {
		super();
		this.laboralExperienceId = laboralExperienceId;
		this.nanny = nanny;
		this.organization = organization;
		this.jobTitle = jobTitle;
		this.activities = activities;
		this.timeWorked = timeWorked;
	}

	public LaboralExperienceEntity() {
	}

	public Integer getLaboralExperienceId() {
		return laboralExperienceId;
	}

	public void setLaboralExperienceId(Integer laboralExperienceId) {
		this.laboralExperienceId = laboralExperienceId;
	}

	public NannyEntity getNanny() {
		return nanny;
	}

	public void setNanny(NannyEntity nanny) {
		this.nanny = nanny;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getActivities() {
		return activities;
	}

	public void setActivities(String activities) {
		this.activities = activities;
	}

	public String getTimeWorked() {
		return timeWorked;
	}

	public void setTimeWorked(String timeWorked) {
		this.timeWorked = timeWorked;
	}
	
}
