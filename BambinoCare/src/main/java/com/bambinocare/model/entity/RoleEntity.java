package com.bambinocare.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="role")
public class RoleEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="role_id",unique=true,nullable = false)
	private Integer roleId;
	
	@Column(name="role_desc", nullable=false,length=45)
	private String roleDesc;

	public RoleEntity(Integer roleId, String roleDesc) {
		super();
		this.roleId = roleId;
		this.roleDesc = roleDesc;
	}
	

	public RoleEntity() {
	}



	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	
}
