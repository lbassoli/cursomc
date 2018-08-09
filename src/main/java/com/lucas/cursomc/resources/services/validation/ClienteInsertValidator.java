package com.lucas.cursomc.resources.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.lucas.cursomc.domain.enums.EnumTipoCliente;
import com.lucas.cursomc.dto.ClienteNewDto;
import com.lucas.cursomc.exceptions.FieldMessage;
import com.lucas.cursomc.resources.repositories.ClienteRepository;
import com.lucas.cursomc.resources.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDto> {

	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDto objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		if (objDto.getTipoCliente().equals(EnumTipoCliente.PESSOA_FISICA.getCodigo()) && !BR.isValidCpf(objDto.getCpfCnpj())) {
			list.add(new FieldMessage("cpfCnpj", "CPF inválido"));
		}
		if (objDto.getTipoCliente().equals(EnumTipoCliente.PESSOA_JURIDICA.getCodigo()) && !BR.isValidCnpj(objDto.getCpfCnpj())) {
			list.add(new FieldMessage("cpfCnpj", "CNPJ inválido"));
		}
		
		if (repo.findByEmail(objDto.getEmail()) != null) {
			list.add(new FieldMessage("email", "Email já existente"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}