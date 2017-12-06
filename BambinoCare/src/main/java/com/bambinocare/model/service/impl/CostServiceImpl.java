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

	@Override
	public Double calculateTotalCost(Double duration, Integer bambinoQuantity) {
		Double totalCost = 0.0;
		
		if(duration == null || duration == 0) return totalCost;
		
		
		//bambinoQuantity = bambinoQuantity > 3 ? 3 : bambinoQuantity;
		
		if(bambinoQuantity > 3) {
			bambinoQuantity -= 3;
			totalCost += calculateTotalCost(duration, bambinoQuantity);
			bambinoQuantity = 3;
		}
		
		List<CostEntity> costs = findByBambinoQuantityOrderByHourQuantity(bambinoQuantity);
		
		for (int i = 0; i < costs.size(); i++) {
			if(duration > costs.get(i).getHourQuantity()) {
				continue;
			}else {
				if(duration.equals(costs.get(i).getHourQuantity())) {
					totalCost += costs.get(i).getCost() * duration;
				}else {
					totalCost += costs.get(i-1).getCost() * duration;
				}
				break;
			}
		}
		
		return totalCost;
	}
	
	

}
