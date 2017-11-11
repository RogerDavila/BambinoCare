package com.bambinocare.model.service;

import java.util.List;

import com.bambinocare.model.entity.BookingStatusEntity;

public interface BookingStatusService {

	List<BookingStatusEntity> findAllBookingStatus();
	BookingStatusEntity findByBookingStatusDesc(String bookingStatusDesc);
	
}
