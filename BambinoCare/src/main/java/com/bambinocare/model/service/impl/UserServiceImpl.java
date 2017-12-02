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

import com.bambinocare.model.entity.RoleEntity;
import com.bambinocare.model.entity.UserEntity;
import com.bambinocare.model.entity.VerificationTokenEntity;
import com.bambinocare.model.repository.UserRepository;
import com.bambinocare.model.repository.VerificationTokenRepository;
import com.bambinocare.model.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserDetailsService, UserService{

	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;
	
	@Autowired
	@Qualifier("verificationTokenRepository")
	private VerificationTokenRepository verificationTokenRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByEmail(email);
		List<GrantedAuthority> authorities = buildAuthorities(user.getRole());
		return buildUser(user, authorities);
	}
	
	private User buildUser(UserEntity user, List<GrantedAuthority> authorities){
		return new User(user.getEmail(),user.getPassword(), user.isEnabled(),
				true, true, true, authorities);
	}
	
	private List<GrantedAuthority> buildAuthorities(RoleEntity rol){
		Set<GrantedAuthority> auths = new HashSet<>();
		auths.add(new SimpleGrantedAuthority(rol.getRoleDesc()));

		return new ArrayList<GrantedAuthority>(auths);
	}
	
	@Override
	public List<UserEntity> listAllUsers(){
		return userRepository.findAll();
	} 
	
	@Override
	public UserEntity findByEmail(String email){
		return userRepository.findByEmail(email);
	}
	
	@Override
	public void removeUser(Integer userId){
		userRepository.delete(findByUserId(userId));
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
	public UserEntity findByUserId(Integer userId) {
		return userRepository.findByUserId(userId);
	}

	@Override
	public boolean userExist(String email) {
		return findByEmail(email) != null ? true : false;
	}
	
	@Override
	public UserEntity updatePassword(String email, String password) {
		
		UserEntity user = findByEmail(email);
		user.setPassword(password);
		UserEntity newUser = createUser(user);
		
		return newUser;
	}
	
	@Override
	public void createVerificationToken(UserEntity user, String token) {
		VerificationTokenEntity verificationToken = new VerificationTokenEntity(token, user);
		
		verificationTokenRepository.save(verificationToken);
	}
	
	@Override
	public VerificationTokenEntity getVerificationToken(String verificationToken) {
		return verificationTokenRepository.findByToken(verificationToken);
	}
	
}
