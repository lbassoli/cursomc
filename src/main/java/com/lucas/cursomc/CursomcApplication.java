package com.lucas.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.lucas.cursomc.domain.Categoria;
import com.lucas.cursomc.domain.Cidade;
import com.lucas.cursomc.domain.Cliente;
import com.lucas.cursomc.domain.Endereco;
import com.lucas.cursomc.domain.Estado;
import com.lucas.cursomc.domain.ItemPedido;
import com.lucas.cursomc.domain.Pagamento;
import com.lucas.cursomc.domain.PagamentoComBoleto;
import com.lucas.cursomc.domain.PagamentoComCartao;
import com.lucas.cursomc.domain.Pedido;
import com.lucas.cursomc.domain.Produto;
import com.lucas.cursomc.domain.enums.EnumEstadoPagamento;
import com.lucas.cursomc.domain.enums.EnumPerfil;
import com.lucas.cursomc.domain.enums.EnumTipoCliente;
import com.lucas.cursomc.resources.repositories.CategoriaRepository;
import com.lucas.cursomc.resources.repositories.CidadeRepository;
import com.lucas.cursomc.resources.repositories.ClienteRepository;
import com.lucas.cursomc.resources.repositories.EnderecoRepository;
import com.lucas.cursomc.resources.repositories.EstadoRepository;
import com.lucas.cursomc.resources.repositories.ItemPedidoRepository;
import com.lucas.cursomc.resources.repositories.PagamentoRepository;
import com.lucas.cursomc.resources.repositories.PedidoRepository;
import com.lucas.cursomc.resources.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepositoy;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria("Informática");
		Categoria cat2 = new Categoria("Escritório");
		
		Produto p1 = new Produto("Computador", 2000);
		Produto p2 = new Produto("Impressora", 800);
		Produto p3 = new Produto("Mouse", 80);
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado("Minas Gerais");
		Estado est2 = new Estado("São Paulo");
		
		Cidade c1 = new Cidade("Uberlandia", est1);
		Cidade c2 = new Cidade("São Paulo", est2);
		Cidade c3 = new Cidade("Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente("Lucas", "lbassoli@gmail.com", "36378912377", EnumTipoCliente.PESSOA_FISICA, pe.encode("123456"));
		cli1.getTelefones().addAll(Arrays.asList("27363323","93838393"));
		
		Cliente admin = new Cliente("ADMIN", "admin@gmail.com", "191", EnumTipoCliente.PESSOA_FISICA, pe.encode("123456"));
		admin.addPerfil(EnumPerfil.ADMIN);
		admin.getTelefones().addAll(Arrays.asList("27363323","93838393"));
		
		Endereco e1 = new Endereco("Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco("Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		Endereco e3 = new Endereco("Rua João Perone", "175", "Ap 38", "Centro", "38777012", admin, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		admin.getEnderecos().addAll(Arrays.asList(e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1, admin));
		enderecoRepository.saveAll(Arrays.asList(e1, e2, e3));
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, format.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, format.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pag1 = new PagamentoComCartao(null, EnumEstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pag1);
		Pagamento pag2 = new PagamentoComBoleto(null, EnumEstadoPagamento.PENDENTE, ped2, format.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pag2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepositoy.saveAll(Arrays.asList(pag1, pag2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 100.00, 1, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		
		
	}
}
