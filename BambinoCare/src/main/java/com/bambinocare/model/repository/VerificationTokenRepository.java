package com.bambinocare.model.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bambinocare.model.entity.UserEntity;
import com.bambinocare.model.entity.VerificationTokenEntity;

@Repository("verificationTokenRepository")
public interface VerificationTokenRepository extends JpaRepository<VerificationTokenEntity, Serializable>{
	
	VerificationTokenEntity findByToken(String token);
	
	VerificationTokenEntity findByUser(UserEntity user);
	
	@Transactional
	Long deleteByToken(String token);
	
}
