package com.bambinocare.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tutory")
public class TutoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tutory_id", unique = true, nullable = false)
	private Integer tutoryId;

	@Column(name = "course", nullable = false)
	private String course;

	@Column(name = "topic", nullable = false)
	private String topic;

	@Column(name = "comments", nullable = true)
	private String comments;

	public TutoryEntity(Integer tutoryId, String course, String topic, String comments) {
		super();
		this.tutoryId = tutoryId;
		this.course = course;
		this.topic = topic;
		this.comments = comments;
	}

	public TutoryEntity() {
	}

	public Integer getTutoryId() {
		return tutoryId;
	}

	public void setTutoryId(Integer tutoryId) {
		this.tutoryId = tutoryId;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
