package com.lucas.cursomc.domain.enums;

import java.util.Arrays;

public enum EnumPerfil {
	
	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE"),
	;
	
	private int codigo;
	private String descricao;
	
	private EnumPerfil(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static EnumPerfil getEnum(int codigo) {
		return Arrays.asList(EnumPerfil.values()).stream().filter(a -> a.getCodigo() == codigo).findFirst().orElse(null);
	}
	

}
