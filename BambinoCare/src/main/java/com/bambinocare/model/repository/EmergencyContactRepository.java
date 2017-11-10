package com.bambinocare.model.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bambinocare.model.entity.ClientEntity;
import com.bambinocare.model.entity.EmergencyContactEntity;

@Repository("emergencyContactRepository")
public interface EmergencyContactRepository extends JpaRepository<EmergencyContactEntity, Serializable>{

	List<EmergencyContactEntity> findByClient(ClientEntity client);
	
}
