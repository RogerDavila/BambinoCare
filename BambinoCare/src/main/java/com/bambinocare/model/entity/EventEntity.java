package com.bambinocare.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="event")
public class EventEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_event",nullable=false, unique=true)
	private Integer idEvent;
	
	@Column(name="event_type", nullable=false)
	private String eventType;
	
	@Column(name="street", nullable=false)
	private String street;
	
	@Column(name="suburb", nullable=false)
	private String suburb;
	
	@Column(name="town", nullable=false)
	private String town;
	
	@Column(name="state", nullable=false)
	private String state;
	
	@Column(name="bambinos_no", nullable=false)
	private Integer bambinosNo;
	
	@Column(name="age", nullable=false)
	private Integer age;
	
	@Column(name="observations", nullable=false)
	private String observations;

	public EventEntity(Integer idEvent, String eventType, String street, String suburb, String town, String state,
			Integer bambinosNo, Integer age, String observations) {
		super();
		this.idEvent = idEvent;
		this.eventType = eventType;
		this.street = street;
		this.suburb = suburb;
		this.town = town;
		this.state = state;
		this.bambinosNo = bambinosNo;
		this.age = age;
		this.observations = observations;
	}

	
	
	public EventEntity() {
	}

	public Integer getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(Integer idEvent) {
		this.idEvent = idEvent;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
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

	public Integer getBambinosNo() {
		return bambinosNo;
	}

	public void setBambinosNo(Integer bambinosNo) {
		this.bambinosNo = bambinosNo;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}
	
}
