package com.bambinocare.model.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bambinocare.model.entity.RoleEntity;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<RoleEntity, Serializable>{

	List<RoleEntity> findAll();
}
