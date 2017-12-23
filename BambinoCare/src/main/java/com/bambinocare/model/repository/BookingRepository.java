package com.bambinocare.model.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bambinocare.model.entity.BookingEntity;
import com.bambinocare.model.entity.NannyEntity;
import com.bambinocare.model.entity.UserEntity;

@Repository("bookingRepository")
public interface BookingRepository extends JpaRepository<BookingEntity, Serializable> {

	List<BookingEntity> findAll();

	List<BookingEntity> findByClientUser(UserEntity user);

	BookingEntity findByBookingId(Integer bookingId);

	BookingEntity findByBookingIdAndClientUser(Integer bookingId, UserEntity user);

	BookingEntity findByBookingIdAndClientUserAndBookingStatusBookingStatusDescNotIn(Integer bookingId, UserEntity user,
			String bookingStatusDesc);

	BookingEntity findByBookingIdAndBookingStatusBookingStatusDescNotIn(Integer bookingId, String... bookingStatusDesc);

	List<BookingEntity> findByNannyAndBookingStatusBookingStatusDesc(NannyEntity nanny, String bookingStatusDesc);

	BookingEntity findByBookingIdAndBookingStatusBookingStatusDescAndNanny(Integer bookingId, String bookingStatusDesc,
			NannyEntity nanny);

	BookingEntity findByBookingIdAndBookingStatusBookingStatusDesc(Integer bookingId, String bookingStatusDesc);

	void delete(BookingEntity bookingEntity);

}
