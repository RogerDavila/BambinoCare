package com.bambinocare.model.service;

import java.util.List;

import com.bambinocare.model.entity.BookingTypeEntity;

public interface BookingTypeService {

	List<BookingTypeEntity> findAllBookingTypes();
	
}
