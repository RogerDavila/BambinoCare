package com.bambinocare.model.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.bambinocare.model.service.EmailService;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

	@Autowired
	public MailSender emailSender;

	@Override
	public void sendSimpleMessage(String to, String subject, String text) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(to);
			message.setSubject(subject);
			message.setText(text);

			emailSender.send(message);
		} catch (MailException exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public void sendSimpleMessageUsingTemplate(String to, String subject, SimpleMailMessage template,
			String... templateArgs) {

		String text = String.format(template.getText(), templateArgs);
		sendSimpleMessage(to, subject, text);
	}

	@Override
	public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendSimpleMessage(String to, String cc, String subject, String text) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(to);
			message.setCc(cc);
			message.setSubject(subject);
			message.setText(text);

			emailSender.send(message);
		} catch (MailException exception) {
			exception.printStackTrace();
		}
	}

}
