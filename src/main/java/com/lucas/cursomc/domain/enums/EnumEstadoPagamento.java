package com.lucas.cursomc.domain.enums;

import java.util.Arrays;

public enum EnumEstadoPagamento {
	
	PENDENTE(1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado"),
	;
	
	private int codigo;
	private String descricao;
	
	private EnumEstadoPagamento(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static EnumEstadoPagamento getEnum(int codigo) {
		return Arrays.asList(EnumEstadoPagamento.values()).stream().filter(a -> a.getCodigo() == codigo).findFirst().orElse(null);
	}
	

}
