package com.bambinocare.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="booking_status")
public class BookingStatusEntity {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_booking_status",unique=true,nullable = false)
	private Integer idBookingStatus;
	
	@Column(name="status_desc", nullable=false)
	private String statusDesc;

	public BookingStatusEntity(Integer idBookingStatus, String statusDesc) {
		super();
		this.idBookingStatus = idBookingStatus;
		this.statusDesc = statusDesc;
	}

	public BookingStatusEntity() {
	}

	public Integer getIdBookingStatus() {
		return idBookingStatus;
	}

	public void setIdBookingStatus(Integer idBookingStatus) {
		this.idBookingStatus = idBookingStatus;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	
}
