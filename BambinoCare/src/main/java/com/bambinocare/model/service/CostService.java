package com.bambinocare.model.service;

import java.util.List;

import com.bambinocare.model.entity.BookingEntity;
import com.bambinocare.model.entity.BookingTypeEntity;
import com.bambinocare.model.entity.CostEntity;

public interface CostService {
	List<CostEntity> findByBambinoQuantityAndBookingTypeOrderByHourQuantity(Integer bambinoQuantity,
			BookingTypeEntity bookingType);

	List<CostEntity> findAllByOrderByHourQuantity();

	Double calculateTotalCost(Double duration, Integer bambinoQuantity, BookingTypeEntity bookingType);

	Double calculateTotalCostByBooking(BookingEntity booking);

	Double calculateTotalCostBambinoEvents(Double duration, Integer bambinoQuantity, BookingTypeEntity bookingType);

	List<CostEntity> findByBookingTypeOrderByHourQuantity(BookingTypeEntity bookingType);
}
