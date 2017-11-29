package com.bambinocare.model.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bambinocare.model.entity.CostEntity;

@Repository("costRepository")
public interface CostRepository extends JpaRepository<CostEntity, Serializable> {

	List<CostEntity> findByBambinoQuantityOrderByHourQuantity(Integer bambinoQuantity);

	List<CostEntity> findAllByOrderByHourQuantity();

}
