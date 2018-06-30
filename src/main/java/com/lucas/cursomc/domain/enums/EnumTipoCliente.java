package com.lucas.cursomc.domain.enums;

import java.util.Arrays;

public enum EnumTipoCliente {
	
	PESSOA_FISICA(1, "Pessoa Física"),
	PESSOA_JURIDICA(2, "Pessoa Jurídica"),
	;
	
	private int codigo;
	private String descricao;
	
	private EnumTipoCliente(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static EnumTipoCliente getTipoCliente(int codigo) {
		return Arrays.asList(EnumTipoCliente.values()).stream().filter(a -> a.getCodigo() == codigo).findFirst().orElse(null);
	}
	
}
