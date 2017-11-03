package com.bambinocare.model.service;

import java.util.List;

import com.bambinocare.model.entity.BookingEntity;
import com.bambinocare.model.entity.UserEntity;

public interface BookingService {
	
	List<BookingEntity> findAllBookings();
	BookingEntity createBooking(BookingEntity booking);
	List<BookingEntity> findByUser(UserEntity user);
}
