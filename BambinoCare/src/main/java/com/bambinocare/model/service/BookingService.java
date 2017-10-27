package com.bambinocare.model.service;

import java.util.List;

import com.bambinocare.model.entity.BookingEntity;

public interface BookingService {
	
	List<BookingEntity> findAllBookings();
	BookingEntity createBooking(BookingEntity booking);
	
}
