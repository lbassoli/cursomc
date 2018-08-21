package com.lucas.cursomc.resources.services.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;


public class MockMailService extends AbstractEmailService {

	private static final Logger logger = LoggerFactory.getLogger(MockMailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		logger.info("Simulando envio de email...");
		logger.info(msg.toString());
		logger.info("Email enviado");
		
	}
	
	

}
