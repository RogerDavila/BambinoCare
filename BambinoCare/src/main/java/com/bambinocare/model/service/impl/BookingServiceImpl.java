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
	public BookingEntity findByBookingId(Integer bookingId) {
		return bookingRepository.findByBookingId(bookingId);
	}

	@Override
	public boolean bookingExist(BookingEntity booking) {

		if(findByBookingId(booking.getBookingId()) != null) {
			return true;
		}
		
		return false;
	}

	@Override
	public BookingEntity findByBookingIdAndUser(Integer bookingId, UserEntity user) {
		return bookingRepository.findByBookingIdAndClientUser(bookingId, user);
	}

	@Override
	public BookingEntity findByBookingIdAndUserAndBookingStatusBookingStatusDescNotIn(Integer bookingId,
			UserEntity user, String bookingStatusDesc) {
		return bookingRepository.findByBookingIdAndClientUserAndBookingStatusBookingStatusDescNotIn(bookingId, user, bookingStatusDesc);
	}

	@Override
	public BookingEntity findByBookingIdAndBookingStatusBookingStatusDescNotIn(Integer bookingId,
			String... bookingStatusDesc) {
		return bookingRepository.findByBookingIdAndBookingStatusBookingStatusDescNotIn(bookingId, bookingStatusDesc);
	}
	
}
