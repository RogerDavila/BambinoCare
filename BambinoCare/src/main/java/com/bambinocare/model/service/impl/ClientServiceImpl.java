package com.bambinocare.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bambinocare.model.ValidationModel;
import com.bambinocare.model.entity.ClientEntity;
import com.bambinocare.model.entity.RoleEntity;
import com.bambinocare.model.entity.UserEntity;
import com.bambinocare.model.repository.ClientRepository;
import com.bambinocare.model.service.ClientService;

@Service("clientService")
public class ClientServiceImpl implements ClientService {

	@Autowired
	@Qualifier("clientRepository")
	private ClientRepository clientRepository;

	@Override
	public ClientEntity createClient(ClientEntity client) {

		if (client == null)
			return null;

		RoleEntity roleEntity = new RoleEntity(3, "Cliente");

		client.getUser().setRole(roleEntity);
		client.getUser().setEnabled(false);

		UserEntity user = client.getUser();

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		return clientRepository.save(client);
	}

	@Override
	public ClientEntity findByUser(UserEntity user) {

		if (user == null)
			return null;

		return clientRepository.findByUser(user);
	}

	@Override
	public ClientEntity findByUserEmail(String email) {

		if (email == "" || email == null)
			return null;

		return clientRepository.findByUserEmail(email);
	}

	@Override
	public ClientEntity saveClient(ClientEntity client) {
		return clientRepository.save(client);
	}

	@Override
	public ValidationModel validateClientForm(ClientEntity client, User user) {
		
		String result = null;
		boolean requireOtherView = false;
		String otherView = null;
		
		String empty = "";
		
		if (client.getUser().getEmail() == null || client.getUser().getEmail().equals(empty)) {
			result = "Favor de verificar el email";
		}else if (client.getUser().getFirstname() == null || client.getUser().getFirstname().equals(empty)) {
			result = "Favor de verificar el Nombre";
		}else if (client.getUser().getLastname() == null || client.getUser().getLastname().equals(empty)) {
			result = "Favor de verificar el Apellido";
		} else if (client.getUser().getPhone() == null || client.getUser().getPhone().equals(empty)) {
			result = "Favor de verificar el email";
		} else if (client.getStreet() == null || client.getStreet().equals(empty)) {
			result = "Favor de verificar el campo Fecha";
		} else if (client.getNeighborhood() == null || client.getNeighborhood().equals(empty)) {
			result = "Favor de verificar el campo Fecha";
		} else if (client.getCity() == null || client.getCity().equals(empty)) {
			result = "Favor de verificar el campo Fecha";
		} else if (client.getState() == null || client.getState().equals(empty)) {
			result = "Favor de verificar el campo Fecha";
		} else if (client.getJob() == null || client.getJob().equals(empty)) {
			result = "Favor de verificar el campo Fecha";
		} else if (!client.getUser().getPasswordConfirm().equals(client.getUser().getPassword())) {
			result = "La contraseña y la confirmación de contraseña no coínciden";
		}
		
		return new ValidationModel(result, requireOtherView, otherView);
	}

}
