package com.bambinocare.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bambinocare.model.entity.BookingEntity;
import com.bambinocare.model.repository.BookingRepository;
import com.bambinocare.model.service.BookingService;

@Service("bookingService")
public class BookingServiceImpl implements BookingService{

	@Autowired
	@Qualifier("bookingRepository")
	private BookingRepository bookingRepository;

	@Override
	public List<BookingEntity> findAllBookings() {
		return bookingRepository.findAll();
	}
	
	@Override
	public BookingEntity createBooking(BookingEntity booking){
		return bookingRepository.save(booking);
	}
	
}
