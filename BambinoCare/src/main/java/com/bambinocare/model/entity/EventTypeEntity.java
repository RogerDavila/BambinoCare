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

	@Column(name = "event_desc", nullable = false)
	private String eventDesc;

	public EventTypeEntity(Integer idEventType, String eventDesc) {
		super();
		this.idEventType = idEventType;
		this.eventDesc = eventDesc;
	}

	public EventTypeEntity() {
	}

	public Integer getIdEventType() {
		return idEventType;
	}

	public void setIdEventType(Integer idEventType) {
		this.idEventType = idEventType;
	}

	public String getEventDesc() {
		return eventDesc;
	}

	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}

}
