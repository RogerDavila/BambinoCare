package com.bambinocare.model.service;

import java.util.List;

import org.springframework.security.core.userdetails.User;

import com.bambinocare.model.ValidationModel;
import com.bambinocare.model.entity.UserEntity;
import com.bambinocare.model.entity.VerificationTokenEntity;

public interface UserService {
	
	List<UserEntity> listAllUsers();
	UserEntity findByEmail(String email);
	UserEntity findByUserId(Integer userId);
	void removeUser(Integer userId);
	UserEntity createUser(UserEntity user);
	UserEntity editUser(UserEntity user);
	boolean userExist(String email);
	UserEntity updatePassword(String email, String password);
	void createVerificationToken(UserEntity user, String token);
	VerificationTokenEntity getVerificationToken(String verificationToken);
	
}
