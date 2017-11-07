package com.bambinocare.model.service;

import java.util.List;

import com.bambinocare.model.entity.BookingEntity;
import com.bambinocare.model.entity.UserEntity;

public interface BookingService {
	
	List<BookingEntity> findAllBookings();
	BookingEntity createBooking(BookingEntity booking);
	List<BookingEntity> findByUser(UserEntity user);
	BookingEntity findBookingByIdBooking(Integer idBooking);
	boolean bookingExist(BookingEntity booking);
	BookingEntity findBookingByIdBookingAndUser(Integer idBooking, UserEntity user);
	BookingEntity findBookingByIdBookingAndUserAndBookingStatusBookingStatusDescNotIn(Integer idBooking, UserEntity user, String bookingStatusDesc);
	BookingEntity findBookingByIdBookingAndBookingStatusBookingStatusDescNotIn(Integer idBooking,String... bookingStatusDesc);
	
}
