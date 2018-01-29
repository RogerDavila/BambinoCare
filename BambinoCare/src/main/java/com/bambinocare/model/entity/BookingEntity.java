package com.bambinocare.model.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	@Column(name = "booking_id")
	private Integer bookingId;

	@JoinColumn(name = "client_id", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	private ClientEntity client;
	
	@ManyToMany
	@JoinTable(name="booking_bambino")
	private Set<BambinoEntity> bambino;

	@JoinColumn(name = "booking_type_id", nullable = false)
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

	@JoinColumn(name = "booking_status_id", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	private BookingStatusEntity bookingStatus;

	@JoinColumn(name = "nanny_id", nullable = true)
	@ManyToOne(fetch = FetchType.EAGER)
	private NannyEntity nanny;

	@Column(name = "cost", nullable = false)
	private Double cost;

	@JoinColumn(name = "tutory_id", nullable = true)
	@OneToOne(fetch = FetchType.EAGER, optional = true)
	private TutoryEntity tutory;

	@JoinColumn(name = "event_id", nullable = true)
	@OneToOne(fetch = FetchType.EAGER, optional = true)
	private EventEntity event;
	
	@JoinColumn(name = "payment_type_id", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	private PaymentTypeEntity paymentType;
	
	@Transient
	private List<String> bambinoId;
	
	@Column(name="start_datetime", columnDefinition="DATETIME", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDateTime;
	
	@Column(name = "finish_datetime",columnDefinition="DATETIME",  nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date finishDateTime;
	
	public BookingEntity() {
	}

	public BookingEntity(Integer bookingId, ClientEntity client, Set<BambinoEntity> bambino,
			BookingTypeEntity bookingType, Date date, String hour, Double duration, BookingStatusEntity bookingStatus,
			NannyEntity nanny, Double cost, TutoryEntity tutory, EventEntity event, PaymentTypeEntity paymentType,
			List<String> bambinoId, Date startDateTime, Date finishDateTime) {
		super();
		this.bookingId = bookingId;
		this.client = client;
		this.bambino = bambino;
		this.bookingType = bookingType;
		this.date = date;
		this.hour = hour;
		this.duration = duration;
		this.bookingStatus = bookingStatus;
		this.nanny = nanny;
		this.cost = cost;
		this.tutory = tutory;
		this.event = event;
		this.paymentType = paymentType;
		this.bambinoId = bambinoId;
		this.startDateTime = startDateTime;
		this.finishDateTime = finishDateTime;
	}

	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	public ClientEntity getClient() {
		return client;
	}

	public void setClient(ClientEntity client) {
		this.client = client;
	}

	public Set<BambinoEntity> getBambino() {
		return bambino;
	}

	public void setBambino(Set<BambinoEntity> bambino) {
		this.bambino = bambino;
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

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
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

	public PaymentTypeEntity getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentTypeEntity paymentType) {
		this.paymentType = paymentType;
	}

	public List<String> getBambinoId() {
		return bambinoId;
	}

	public void setBambinoId(List<String> bambinoId) {
		this.bambinoId = bambinoId;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getFinishDateTime() {
		return finishDateTime;
	}

	public void setFinishDateTime(Date finishDateTime) {
		this.finishDateTime = finishDateTime;
	}

}