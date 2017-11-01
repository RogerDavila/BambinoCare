package com.bambinocare.model.service;

import java.util.List;

import com.bambinocare.model.entity.UserEntity;

public interface UserService {
	
	List<UserEntity> listAllUsers();
	UserEntity findUserByEmail(String email);
	UserEntity findUserByIdUser(Integer idUser);
	void removeUser(Integer idUser);
	UserEntity createUser(UserEntity user);
	UserEntity editUser(UserEntity user);
	boolean userExist(String email);
	UserEntity updatePassword(String email, String password);
	
}
