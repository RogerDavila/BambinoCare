package com.bambinocare.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "event_type")
public class EventTypeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_event_type", unique = true, nullable = false)
	private Integer idEventType;

	@Column(name = "event_type_desc", nullable = false)
	private String eventTypeDesc;

	public EventTypeEntity(Integer idEventType, String eventTypeDesc) {
		super();
		this.idEventType = idEventType;
		this.eventTypeDesc = eventTypeDesc;
	}

	public EventTypeEntity() {
	}

	public Integer getIdEventType() {
		return idEventType;
	}

	public void setIdEventType(Integer idEventType) {
		this.idEventType = idEventType;
	}

	public String getEventTypeDesc() {
		return eventTypeDesc;
	}

	public void setEventTypeDesc(String eventTypeDesc) {
		this.eventTypeDesc = eventTypeDesc;
	}
	
	
}
