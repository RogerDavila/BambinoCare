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
	
	@Column(name="booking_type_desc", nullable=false)
	private String bookingTypeDesc;

	public BookingTypeEntity(Integer idBookingType, String bookingTypeDesc) {
		super();
		this.idBookingType = idBookingType;
		this.bookingTypeDesc = bookingTypeDesc;
	}

	public BookingTypeEntity() {
	}

	public Integer getIdBookingType() {
		return idBookingType;
	}

	public void setIdBookingType(Integer idBookingType) {
		this.idBookingType = idBookingType;
	}

	public String getBookingTypeDesc() {
		return bookingTypeDesc;
	}

	public void setBookingTypeDesc(String bookingTypeDesc) {
		this.bookingTypeDesc = bookingTypeDesc;
	}

}
