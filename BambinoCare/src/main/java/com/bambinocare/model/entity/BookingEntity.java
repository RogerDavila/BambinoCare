package com.bambinocare.model.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "entity")
public class BookingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_booking")
	private Integer idBooking;

	@JoinColumn(name="id_user", nullable=false)
	@ManyToOne(fetch = FetchType.EAGER)
	private UserEntity user;

	@JoinColumn(name = "id_booking_type", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	private BookingTypeEntity bookingType;

	@Column(name = "date", nullable = false)
	private Date date;

	@Column(name = "duration", nullable = false)
	private Double duration;

	@JoinColumn(name = "id_booking_status", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	private BookingStatusEntity bookingStatus;

	@JoinColumn(name = "id_nanny", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	private NannyEntity nanny;
	
	@Column(name = "cost", nullable = false)
	private Double cost;
	
	@JoinColumn(name = "id_tutory", nullable = true)
	@OneToOne(fetch = FetchType.EAGER)
	private TutoryEntity tutory;

	@JoinColumn(name = "id_event", nullable = true)
	@OneToOne(fetch = FetchType.EAGER)
	private EventEntity event;
}
