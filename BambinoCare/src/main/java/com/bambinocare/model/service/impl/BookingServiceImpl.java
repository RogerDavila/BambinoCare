package com.bambinocare.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bambinocare.model.entity.BookingEntity;
import com.bambinocare.model.entity.UserEntity;
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

	@Override
	public List<BookingEntity> findByUser(UserEntity user) {
		return bookingRepository.findByClientUser(user);
	}

	@Override
	public BookingEntity findBookingByIdBooking(Integer idBooking) {
		return bookingRepository.findByIdBooking(idBooking);
	}

	@Override
	public boolean bookingExist(BookingEntity booking) {

		if(findBookingByIdBooking(booking.getIdBooking()) != null) {
			return true;
		}
		
		return false;
	}

	@Override
	public BookingEntity findBookingByIdBookingAndUser(Integer idBooking, UserEntity user) {
		return bookingRepository.findByIdBookingAndClientUser(idBooking, user);
	}

	@Override
	public BookingEntity findBookingByIdBookingAndUserAndBookingStatusBookingStatusDescNotIn(Integer idBooking,
			UserEntity user, String bookingStatusDesc) {
		return bookingRepository.findByIdBookingAndClientUserAndBookingStatusBookingStatusDescNotIn(idBooking, user, bookingStatusDesc);
	}

	@Override
	public BookingEntity findBookingByIdBookingAndBookingStatusBookingStatusDescNotIn(Integer idBooking,
			String... bookingStatusDesc) {
		return bookingRepository.findByIdBookingAndBookingStatusBookingStatusDescNotIn(idBooking, bookingStatusDesc);
	}
	
}
