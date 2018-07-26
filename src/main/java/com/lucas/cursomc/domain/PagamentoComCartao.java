package com.lucas.cursomc.domain;

import javax.persistence.Entity;

import com.lucas.cursomc.domain.enums.EnumEstadoPagamento;

@Entity
public class PagamentoComCartao extends Pagamento{
	
	private static final long serialVersionUID = 1L;
	
	private int numeroParcelas;
	
	public PagamentoComCartao() {
	}

	public PagamentoComCartao(Integer id, EnumEstadoPagamento estado, Pedido pedido, int numeroParcelas) {
		super(id, estado, pedido);
		this.numeroParcelas = numeroParcelas;
	}

	public int getNumeroParcelas() {
		return numeroParcelas;
	}

	public void setNumeroParcelas(int numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}
	
	

}
