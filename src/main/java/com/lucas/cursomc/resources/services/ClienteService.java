package com.lucas.cursomc.resources.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.cursomc.domain.Cidade;
import com.lucas.cursomc.domain.Cliente;
import com.lucas.cursomc.domain.Endereco;
import com.lucas.cursomc.domain.enums.EnumTipoCliente;
import com.lucas.cursomc.dto.ClienteDTO;
import com.lucas.cursomc.dto.ClienteNewDto;
import com.lucas.cursomc.exceptions.DataIntegrityException;
import com.lucas.cursomc.exceptions.ObjectNotFoundException;
import com.lucas.cursomc.resources.repositories.CidadeRepository;
import com.lucas.cursomc.resources.repositories.ClienteRepository;
import com.lucas.cursomc.resources.repositories.EnderecoRepository;

@Service
public class ClienteService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id){
		Optional<Cliente> obj = repo.findById(id);
		if (!obj.isPresent()) {
			throw new ObjectNotFoundException("Objeto não encontrado - Id: " + id + " - Tipo: " + Cliente.class.getSimpleName());
		}
		return obj.get();
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;

	}
	
	public Cliente update(Cliente obj) {
		Cliente cliente = find(obj.getId());
		updateData(cliente, obj);
		return repo.save(cliente);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir o cliente porque há pedidos relacionados");
		}
 	}
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO dto) {
		return new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteNewDto dto) {
		Cliente cli = new Cliente(null, dto.getNome(), dto.getEmail(), dto.getCpfCnpj(), EnumTipoCliente.getTipoCliente(dto.getTipoCliente()));
		Optional<Cidade> objCidade = cidadeRepository.findById(dto.getCidadeId());
		if (!objCidade.isPresent()) {
			throw new ObjectNotFoundException("Objeto cidade não encontrado - Id: " + dto.getCidadeId() + " - Tipo: " + Cidade.class.getSimpleName());
		}
		Endereco end = new Endereco(dto.getLogradouro(), dto.getNumero(), dto.getComplemento(), dto.getBairro(), dto.getCep(), cli, objCidade.get());
		cli.getEnderecos().add(end);
		cli.getTelefones().add(dto.getTelefone());
		if (dto.getTelefone1() != null) {
			cli.getTelefones().add(dto.getTelefone1());
		}
		if (dto.getTelefone2() != null) {
			cli.getTelefones().add(dto.getTelefone2());
		}
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}


}
