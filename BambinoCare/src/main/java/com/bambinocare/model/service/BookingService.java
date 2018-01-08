package com.bambinocare.model.service;

import java.util.Date;
import java.util.List;

import org.springframework.security.core.userdetails.User;

import com.bambinocare.model.ValidationModel;
import com.bambinocare.model.entity.BookingEntity;
import com.bambinocare.model.entity.NannyEntity;
import com.bambinocare.model.entity.UserEntity;

public interface BookingService {

	List<BookingEntity> findAllBookings();

	BookingEntity createBooking(BookingEntity booking);

	List<BookingEntity> findByUser(UserEntity user);

	BookingEntity findByBookingId(Integer bookingId);

	boolean bookingExist(BookingEntity booking);

	BookingEntity findByBookingIdAndUser(Integer bookingId, UserEntity user);

	BookingEntity findByBookingIdAndUserAndBookingStatusBookingStatusDescNotIn(Integer bookingId, UserEntity user,
			String bookingStatusDesc);

	BookingEntity findByBookingIdAndBookingStatusBookingStatusDescNotIn(Integer bookingId, String... bookingStatusDesc);

	List<BookingEntity> findByNannyAndBookingStatusBookingStatusDesc(NannyEntity nanny, String bookingTypeDesc);

	BookingEntity findByBookingIdAndBookingStatusBookingStatusDescAndNanny(Integer bookingId, String bookingStatusDesc,
			NannyEntity nanny);

	BookingEntity findByBookingIdAndBookingStatusBookingStatusDesc(Integer bookingId,
			String bookingStatusDesc);

	void delete(BookingEntity bookingEntity);
	
	String getFinalHour(String initialTime, Double duration);
	
	Date getDate(Date date, int days);
	
	boolean isValideDate (Date date, String hour);
	
	boolean isValideHour (String hour);
	
	ValidationModel validateBookingForm(BookingEntity booking, User user);

}
