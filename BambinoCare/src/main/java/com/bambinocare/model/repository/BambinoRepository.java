package com.bambinocare.model.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bambinocare.model.entity.BambinoEntity;
import com.bambinocare.model.entity.ClientEntity;
import com.bambinocare.model.entity.UserEntity;

@Repository("bambinoRepository")
public interface BambinoRepository extends JpaRepository<BambinoEntity, Serializable> {

	List<BambinoEntity> findByClient(ClientEntity client);
	List<BambinoEntity> findByClientUser(UserEntity user);
	BambinoEntity findByBambinoIdAndClientUser(Integer bookingId, UserEntity user);
	
}
