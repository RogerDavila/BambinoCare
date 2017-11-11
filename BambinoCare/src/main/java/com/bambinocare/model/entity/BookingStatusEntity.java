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
	
	@Column(name="booking_status_desc", nullable=false)
	private String bookingStatusDesc;

	public BookingStatusEntity(Integer idBookingStatus, String bookingStatusDesc) {
		super();
		this.idBookingStatus = idBookingStatus;
		this.bookingStatusDesc = bookingStatusDesc;
	}

	public BookingStatusEntity() {
	}

	public Integer getIdBookingStatus() {
		return idBookingStatus;
	}

	public void setIdBookingStatus(Integer idBookingStatus) {
		this.idBookingStatus = idBookingStatus;
	}

	public String getBookingStatusDesc() {
		return bookingStatusDesc;
	}

	public void setBookingStatusDesc(String bookingStatusDesc) {
		this.bookingStatusDesc = bookingStatusDesc;
	}
	
}
