package com.bambinocare.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bambinocare.model.entity.BookingStatusEntity;
import com.bambinocare.model.repository.BookingStatusRepository;
import com.bambinocare.model.service.BookingStatusService;

@Service("bookingStatusService")
public class BookingStatusServiceImpl implements BookingStatusService{

	@Autowired
	@Qualifier("bookingStatusRepository")
	private BookingStatusRepository bookingStatusRepository;
	
	@Override
	public List<BookingStatusEntity> findAllBookingStatus() {
		return bookingStatusRepository.findAll();
	}

	@Override
	public BookingStatusEntity findByBookingStatusDesc(String bookingStatusDesc) {
		return bookingStatusRepository.findByBookingStatusDesc(bookingStatusDesc);
	}

}
