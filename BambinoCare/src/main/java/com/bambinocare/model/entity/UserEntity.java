package com.bambinocare.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="user")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="email", nullable=false, unique=true)
	private String email;
	
	@Column(name="password",nullable=false,length=200)
	private String password;
	
	@Transient
	private String passwordConfirm;
	
	@JoinColumn(name="role_id", nullable=false)
	@OneToOne(fetch = FetchType.EAGER)
	private RoleEntity role;
	
	@Column(name="enabled",nullable=false)
	private boolean enabled;
	
	@Column(name="firstname", nullable=false)
	private String firstname;

	@Column(name="lastname", nullable=false)
	private String lastname;
	
	@Column(name="phone", nullable=false)
	private String phone;

	public UserEntity(Integer userId, String email, String password, String passwordConfirm, RoleEntity role,
			boolean enabled, String firstname, String lastname, String phone) {
		super();
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.role = role;
		this.enabled = enabled;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phone = phone;
	}

	public UserEntity() {
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
