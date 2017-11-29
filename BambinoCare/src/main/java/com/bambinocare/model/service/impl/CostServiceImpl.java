package com.bambinocare.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bambinocare.model.entity.CostEntity;
import com.bambinocare.model.repository.CostRepository;
import com.bambinocare.model.service.CostService;

@Service("costService")
public class CostServiceImpl implements CostService{

	@Autowired
	@Qualifier("costRepository")
	private CostRepository costRepository;
	
	@Override
	public List<CostEntity> findByBambinoQuantityOrderByHourQuantity(Integer bambinoQuantity) {
		return costRepository.findByBambinoQuantityOrderByHourQuantity(bambinoQuantity);
	}

	@Override
	public List<CostEntity> findAllByOrderByHourQuantity() {
		return costRepository.findAllByOrderByHourQuantity();
	}

}
