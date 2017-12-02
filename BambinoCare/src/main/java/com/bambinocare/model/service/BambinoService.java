package com.bambinocare.model.service;

import java.util.List;

import com.bambinocare.model.entity.BambinoEntity;
import com.bambinocare.model.entity.ClientEntity;
import com.bambinocare.model.entity.UserEntity;

public interface BambinoService {
	
	List<BambinoEntity> findByClient(ClientEntity client);
	BambinoEntity createBambino(BambinoEntity bambino);
	List<BambinoEntity> findByUser(UserEntity user);
	BambinoEntity findByBambinoIdAndUser(Integer bambinoId, UserEntity user);
	void removeBambino(Integer bambinoId);
	BambinoEntity findByBambinoId(Integer bambinoId);
}
