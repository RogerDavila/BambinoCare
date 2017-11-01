package com.bambinocare.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bambinocare.model.entity.BookingTypeEntity;
import com.bambinocare.model.repository.BookingTypeRepository;
import com.bambinocare.model.service.BookingTypeService;

@Service("bookingTypeService")
public class BookingTypeServiceImpl implements BookingTypeService{

	@Autowired
	@Qualifier("bookingTypeRepository")
	private BookingTypeRepository bookingTypeRepository;
	
	@Override
	public List<BookingTypeEntity> findAllBookingTypes() {
		return bookingTypeRepository.findAll();
	}

}
