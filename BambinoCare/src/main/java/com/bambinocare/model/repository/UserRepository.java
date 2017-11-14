package com.bambinocare.model.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bambinocare.model.entity.UserEntity;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<UserEntity, Serializable> {
	
	UserEntity findByEmail(String email); 
	UserEntity findByUserId(Integer userId);
	
}
