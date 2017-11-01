package com.bambinocare.model.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bambinocare.model.entity.BookingTypeEntity;

@Repository("bookingTypeRepository")
public interface BookingTypeRepository extends JpaRepository<BookingTypeEntity, Serializable>{

	List<BookingTypeEntity> findAll();
	
}
