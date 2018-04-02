package com.bambinocare.model.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bambinocare.model.entity.PaymentTypeEntity;

@Repository("paymentTypeRepository")
public interface PaymentTypeRepository extends JpaRepository<PaymentTypeEntity, Serializable>{

	List<PaymentTypeEntity> findAll();
	PaymentTypeEntity findByPaymentTypeId(Integer paymentId);
	List<PaymentTypeEntity> findByPaymentTypeIdNotIn(Integer... paymentId);
	
}
