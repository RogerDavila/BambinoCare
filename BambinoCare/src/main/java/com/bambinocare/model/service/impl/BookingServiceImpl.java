package com.bambinocare.model.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bambinocare.model.entity.BookingEntity;
import com.bambinocare.model.entity.NannyEntity;
import com.bambinocare.model.entity.UserEntity;
import com.bambinocare.model.repository.BookingRepository;
import com.bambinocare.model.service.BookingService;

@Service("bookingService")
public class BookingServiceImpl implements BookingService {

	@Autowired
	@Qualifier("bookingRepository")
	private BookingRepository bookingRepository;

	@Override
	public List<BookingEntity> findAllBookings() {
		return bookingRepository.findAll();
	}

	@Override
	public BookingEntity createBooking(BookingEntity booking) {
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

		if (findByBookingId(booking.getBookingId()) != null) {
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
		return bookingRepository.findByBookingIdAndClientUserAndBookingStatusBookingStatusDescNotIn(bookingId, user,
				bookingStatusDesc);
	}

	@Override
	public BookingEntity findByBookingIdAndBookingStatusBookingStatusDescNotIn(Integer bookingId,
			String... bookingStatusDesc) {
		return bookingRepository.findByBookingIdAndBookingStatusBookingStatusDescNotIn(bookingId, bookingStatusDesc);
	}

	@Override
	public List<BookingEntity> findByNannyAndBookingStatusBookingStatusDesc(NannyEntity nanny,
			String bookingStatusDesc) {
		return bookingRepository.findByNannyAndBookingStatusBookingStatusDesc(nanny, bookingStatusDesc);
	}

	@Override
	public BookingEntity findByBookingIdAndBookingStatusBookingStatusDescAndNanny(Integer bookingId,
			String bookingStatusDesc, NannyEntity nanny) {
		return bookingRepository.findByBookingIdAndBookingStatusBookingStatusDescAndNanny(bookingId, bookingStatusDesc,
				nanny);
	}

	@Override
	public BookingEntity findByBookingIdAndBookingStatusBookingStatusDesc(Integer bookingId, String bookingStatusDesc) {
		return bookingRepository.findByBookingIdAndBookingStatusBookingStatusDesc(bookingId, bookingStatusDesc);
	}

	@Override
	public void delete(BookingEntity bookingEntity) {
		bookingRepository.delete(bookingEntity);
	}

	@Override
	public String getFinalHour(String initialTime, Double duration) {
		// Calcular horario
		String[] initialHourAux = initialTime.split(":");

		Integer hoursToAdd = duration.intValue();
		Integer minutesToAdd = (int) ((duration - hoursToAdd) * 60);

		Calendar calendar = Calendar.getInstance();
		calendar.set(2017, 12, 12, Integer.parseInt(initialHourAux[0]), Integer.parseInt(initialHourAux[1]));
		calendar.add(Calendar.HOUR, hoursToAdd);
		calendar.add(Calendar.MINUTE, minutesToAdd);

		String finalHour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
		String finalMinute = String.valueOf(calendar.get(Calendar.MINUTE));

		if (finalMinute.equals("0")) {
			finalMinute = "00";
		}

		String finalTime = finalHour + ":" + finalMinute;
		return finalTime;
	}

	@Override
	public Date getDate(Date date, int days) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, days);

		return calendar.getTime();
	}
	
	@Override
	public boolean isValideDate (Date date, String hour) {
		boolean isValideDate = false;
		
		String[] initialHourAux = hour.split(":");
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getDate(date, 1));
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(initialHourAux[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(initialHourAux[1]));

		Calendar newCalendar = Calendar.getInstance();
		newCalendar.add(Calendar.HOUR_OF_DAY, 24);
		
		if(calendar.after(newCalendar)) {
			isValideDate = true;
		}
		
		return isValideDate;
		
	}
	
}
