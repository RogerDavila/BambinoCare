package com.bambinocare.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="booking_type")
public class BookingTypeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_booking_type",unique=true,nullable = false)
	private Integer idBookingType;
	
	@Column(name="booking_desc", nullable=false)
	private String bookingDesc;

	
	public BookingTypeEntity(Integer idBookingType, String bookingDesc) {
		super();
		this.idBookingType = idBookingType;
		this.bookingDesc = bookingDesc;
	}

	public BookingTypeEntity() {
	}

	public Integer getIdBookingType() {
		return idBookingType;
	}

	public void setIdBookingType(Integer idBookingType) {
		this.idBookingType = idBookingType;
	}

	public String getBookingDesc() {
		return bookingDesc;
	}

	public void setBookingDesc(String bookingDesc) {
		this.bookingDesc = bookingDesc;
	}
	
}
