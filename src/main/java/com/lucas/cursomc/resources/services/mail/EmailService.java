package com.lucas.cursomc.resources.services.mail;

import org.springframework.mail.SimpleMailMessage;

import com.lucas.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}
