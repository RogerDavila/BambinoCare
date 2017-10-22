package com.bambinocare.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rol")
public class RolEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_rol",unique=true,nullable = false)
	private Integer idRol;
	
	@Column(name="rol_desc", nullable=false,length=45)
	private String rolDesc;
	
	public RolEntity(Integer idRol, String rolDesc) {
		super();
		this.idRol = idRol;
		this.rolDesc = rolDesc;
	}

	public RolEntity() {
	}

	public Integer getIdRol() {
		return idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}

	public String getRolDesc() {
		return rolDesc;
	}

	public void setRolDesc(String rolDesc) {
		this.rolDesc = rolDesc;
	}
	
}
