package com.bambinocare.model.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bambinocare.model.entity.BookingEntity;
import com.bambinocare.model.entity.UserEntity;

@Repository("bookingRepository")
public interface BookingRepository extends JpaRepository<BookingEntity, Serializable>{

	List<BookingEntity> findAll();
	List<BookingEntity> findByUser(UserEntity user);
	
}
