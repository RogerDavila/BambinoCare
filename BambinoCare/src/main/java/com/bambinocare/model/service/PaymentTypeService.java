package com.bambinocare.model.service;

import java.util.List;

import com.bambinocare.model.entity.PaymentTypeEntity;

public interface PaymentTypeService {

	List<PaymentTypeEntity> findAll();
	PaymentTypeEntity findByPaymentTypeId(Integer paymentId);
	List<PaymentTypeEntity> findByPaymentTypeIdNotIn(Integer... paymentId);
	
}
