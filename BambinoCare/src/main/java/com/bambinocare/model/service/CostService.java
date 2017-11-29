package com.bambinocare.model.service;

import java.util.List;

import com.bambinocare.model.entity.CostEntity;

public interface CostService {
	List<CostEntity> findByBambinoQuantityOrderByHourQuantity(Integer bambinoQuantity);

	List<CostEntity> findAllByOrderByHourQuantity();
}
