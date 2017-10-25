package com.bambinocare.model.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bambinocare.model.entity.ClientEntity;

@Repository("clientRepository")
public interface ClientRepository extends JpaRepository<ClientEntity, Serializable>{

}
