package com.bambinocare.model.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bambinocare.model.entity.RolEntity;

@Repository("rolRepository")
public interface RolRepository extends JpaRepository<RolEntity, Serializable>{

	List<RolEntity> findAll();
}
