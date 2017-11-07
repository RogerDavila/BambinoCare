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
	@Column(name="id_user")
	private Integer idUser;
	
	@Column(name="email", nullable=false, unique=true)
	private String email;
	
	@Column(name="password",nullable=false,length=200)
	private String password;
	
	@Transient
	private String passwordConfirm;
	
	@JoinColumn(name="id_rol", nullable=false)
	@OneToOne(fetch = FetchType.EAGER)
	private RolEntity rol;
	
	@Column(name="enabled",nullable=false)
	private boolean enabled;
	
	@Column(name="name", nullable=false)
	private String name;

	@Column(name="lastname", nullable=false)
	private String lastname;
	
	@Column(name="telephone", nullable=false)
	private String telephone;
	
	public UserEntity(Integer idUser, String email, String password, RolEntity rol, boolean enabled, String name,
			String lastname, String telephone) {
		this.idUser = idUser;
		this.email = email;
		this.password = password;
		this.rol = rol;
		this.enabled = enabled;
		this.name = name;
		this.lastname = lastname;
		this.telephone = telephone;
	}

	public UserEntity() {
	}



	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
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

	public RolEntity getRol() {
		return rol;
	}

	public void setRol(RolEntity rol) {
		this.rol = rol;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	
}
