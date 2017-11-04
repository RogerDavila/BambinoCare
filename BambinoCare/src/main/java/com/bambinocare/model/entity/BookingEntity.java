package com.bambinocare.model.entity;

import java.util.Date;

import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name = "booking")
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
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = ISO.DATE)
	private Date date;
	
	@Column(name = "hour", nullable = false)
	private String hour;

	@Column(name = "duration", nullable = false)
	private Double duration;

	@JoinColumn(name = "id_booking_status", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	private BookingStatusEntity bookingStatus;

	@JoinColumn(name = "id_nanny", nullable = true)
	@ManyToOne(fetch = FetchType.EAGER)
	private NannyEntity nanny;
	
	@Column(name = "cost", nullable = false)
	private Double cost;
	
	@JoinColumn(name = "id_tutory", nullable = true)
	@OneToOne(fetch = FetchType.EAGER, optional=true)
	@Transient
	private TutoryEntity tutory;

	@JoinColumn(name = "id_event", nullable = true)
	@OneToOne(fetch = FetchType.EAGER, optional=true)
	@Transient
	private EventEntity event;

	public BookingEntity(Integer idBooking, UserEntity user, BookingTypeEntity bookingType, Date date, String hour,
			Double duration, BookingStatusEntity bookingStatus, NannyEntity nanny, Double cost, TutoryEntity tutory,
			EventEntity event) {
		super();
		this.idBooking = idBooking;
		this.user = user;
		this.bookingType = bookingType;
		this.date = date;
		this.hour = hour;
		this.duration = duration;
		this.bookingStatus = bookingStatus;
		this.nanny = nanny;
		this.cost = cost;
		this.tutory = tutory;
		this.event = event;
	}

	public BookingEntity() {
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public Integer getIdBooking() {
		return idBooking;
	}

	public void setIdBooking(Integer idBooking) {
		this.idBooking = idBooking;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public BookingTypeEntity getBookingType() {
		return bookingType;
	}

	public void setBookingType(BookingTypeEntity bookingType) {
		this.bookingType = bookingType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public BookingStatusEntity getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(BookingStatusEntity bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public NannyEntity getNanny() {
		return nanny;
	}

	public void setNanny(NannyEntity nanny) {
		this.nanny = nanny;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public TutoryEntity getTutory() {
		return tutory;
	}

	public void setTutory(TutoryEntity tutory) {
		this.tutory = tutory;
	}

	public EventEntity getEvent() {
		return event;
	}

	public void setEvent(EventEntity event) {
		this.event = event;
	}
	
}
