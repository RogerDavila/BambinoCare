package com.bambinocare.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.bambinocare.event.OnRegistrationCompleteEvent;
import com.bambinocare.model.entity.UserEntity;
import com.bambinocare.model.service.EmailService;
import com.bambinocare.model.service.UserService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("emailService")
	private EmailService emailService;

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}

	private void confirmRegistration(OnRegistrationCompleteEvent event) {

		UserEntity user = event.getUser();
		String token = UUID.randomUUID().toString();

		userService.createVerificationToken(user, token);

		String to = user.getEmail();
		String subject = "BambinoCare - Confirmación de Registro";
		String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + token;

		emailService.sendSimpleMessage(to, subject,
				"Hola!\n" + "Casi terminamos con tu registro en BambinoCare, "
						+ "por favor confirma tu registro en el siguiente link: \n http://localhost:8080"
						+ confirmationUrl + "\n\nSi has recibido este correo por error, por favor haz caso omiso.");
	}

}
