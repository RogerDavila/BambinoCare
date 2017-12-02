package com.bambinocare.model.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bambinocare.model.entity.NannyEntity;


@Repository("nannyRepository")
public interface NannyRepository extends JpaRepository<NannyEntity, Serializable>{

	List<NannyEntity> findAll();
	NannyEntity findByNannyId(Integer nannyId);
	NannyEntity findByUserEmail(String email);
	
}
