package com.bambinocare.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bambinocare.model.entity.PaymentTypeEntity;
import com.bambinocare.model.repository.PaymentTypeRepository;
import com.bambinocare.model.service.PaymentTypeService;

@Service("paymentTypeService")
public class PaymentTypeServiceImpl implements PaymentTypeService{

	@Autowired
	@Qualifier("paymentTypeRepository")
	private PaymentTypeRepository paymentTypeRepository;
	
	@Override
	public List<PaymentTypeEntity> findAll() {
		return paymentTypeRepository.findAll();
	}

	@Override
	public PaymentTypeEntity findByPaymentTypeId(Integer paymentId) {
		return paymentTypeRepository.findByPaymentTypeId(paymentId);
	}

}
