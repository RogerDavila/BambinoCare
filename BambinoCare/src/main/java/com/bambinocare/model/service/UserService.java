package com.bambinocare.model.service;

import java.util.List;

import com.bambinocare.model.entity.UserEntity;

public interface UserService {
	
	List<UserEntity> listAllUsers();
	UserEntity findByEmail(String email);
	UserEntity findByUserId(Integer userId);
	void removeUser(Integer userId);
	UserEntity createUser(UserEntity user);
	UserEntity editUser(UserEntity user);
	boolean userExist(String email);
	UserEntity updatePassword(String email, String password);
	
}
