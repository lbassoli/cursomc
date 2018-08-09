package com.lucas.cursomc.resources.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.cursomc.domain.ItemPedido;
import com.lucas.cursomc.domain.PagamentoComBoleto;
import com.lucas.cursomc.domain.Pedido;
import com.lucas.cursomc.domain.enums.EnumEstadoPagamento;
import com.lucas.cursomc.exceptions.ObjectNotFoundException;
import com.lucas.cursomc.resources.repositories.ItemPedidoRepository;
import com.lucas.cursomc.resources.repositories.PagamentoRepository;
import com.lucas.cursomc.resources.repositories.PedidoRepository;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public Pedido find(Integer id){
		Optional<Pedido> obj = repo.findById(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado - Id: " + id + " - Tipo: " + Pedido.class.getSimpleName());
		}
		return obj.get();
	}
	
	//INSERT TRANSACTIONAL
	//produto service

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EnumEstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pgto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pgto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido item : obj.getItens()) {
			item.setDesconto(0.00);
			item.setProduto(produtoService.find(item.getProduto().getId()));
			item.setPreco(item.getProduto().getPreco());
			item.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}

}
