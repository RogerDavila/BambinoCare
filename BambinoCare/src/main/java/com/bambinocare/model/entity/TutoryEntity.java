package com.bambinocare.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tutory")
public class TutoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_tutory",unique=true,nullable = false)
	private Integer idTutory;
	
	@Column(name="course", nullable=false)
	private String course;
	
	@Column(name="topic", nullable=false)
	private String topic;
	
	@Column(name="observations", nullable=false)
	private String observations;

	public TutoryEntity(Integer idTutory, String course, String topic, String observations) {
		super();
		this.idTutory = idTutory;
		this.course = course;
		this.topic = topic;
		this.observations = observations;
	}

	public TutoryEntity() {
	}

	public Integer getIdTutory() {
		return idTutory;
	}

	public void setIdTutory(Integer idTutory) {
		this.idTutory = idTutory;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}
	
}
