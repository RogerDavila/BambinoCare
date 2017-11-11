package com.bambinocare.model.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bambinocare.model.entity.BookingStatusEntity;

@Repository("bookingStatusRepository")
public interface BookingStatusRepository extends JpaRepository<BookingStatusEntity, Serializable>{

	List<BookingStatusEntity> findAll();
	BookingStatusEntity findByBookingStatusDesc(String bookingStatusDesc);
}
