package com.bambinocare.model.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import com.bambinocare.model.entity.RolEntity;
import com.bambinocare.model.entity.UserEntity;
import com.bambinocare.model.repository.UserRepository;
import com.bambinocare.model.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserDetailsService, UserService{

	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByEmail(email);
		List<GrantedAuthority> authorities = buildAuthorities(user.getRol());
		return buildUser(user, authorities);
	}
	
	private User buildUser(UserEntity user, List<GrantedAuthority> authorities){
		return new User(user.getEmail(),user.getPassword(), user.isEnabled(),
				true, true, true, authorities);
	}
	
	private List<GrantedAuthority> buildAuthorities(RolEntity rol){
		Set<GrantedAuthority> auths = new HashSet<>();
		
		auths.add(new SimpleGrantedAuthority(rol.getRolDesc()));

		return new ArrayList<GrantedAuthority>(auths);
	}
	
	@Override
	public List<UserEntity> listAllUsers(){
		return userRepository.findAll();
	} 
	
	@Override
	public UserEntity findUserByEmail(String email){
		return userRepository.findByEmail(email);
	}
	
	@Override
	public void removeUser(Integer idUser){
		userRepository.delete(findUserByIdUser(idUser));
	}
	
	@Override
	public UserEntity createUser(UserEntity user){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		UserEntity newUser = userRepository.save(user);
		return newUser;
	}
	
	@Override
	public UserEntity editUser(UserEntity user){
		UserEntity newUser = userRepository.save(user);
		return newUser;
	}

	@Override
	public UserEntity findUserByIdUser(Integer idUser) {
		return userRepository.findByIdUser(idUser);
	}

}
