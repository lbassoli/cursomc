package com.lucas.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.lucas.cursomc.domain.enums.EnumTipoCliente;

@Entity
public class Cliente implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String nome;
	private String email;
	private String cpfCnpj;
	private Integer tipoCliente;
	
	@ElementCollection
	@CollectionTable(name="telefone")
	private Set<String> telefones = new HashSet<>();
	
	@OneToMany(mappedBy="cliente")
	private List<Endereco> enderecos =  new ArrayList<>();
	
	public Cliente() {
	}

	public Cliente(String nome, String email, String cpfCnpj, EnumTipoCliente tipoCliente) {
		super();
		this.nome = nome;
		this.email = email;
		this.cpfCnpj = cpfCnpj;
		this.tipoCliente = tipoCliente.getCodigo();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public EnumTipoCliente getTipoCliente() {
		return EnumTipoCliente.getTipoCliente(tipoCliente);
	}

	public void setTipoCliente(EnumTipoCliente tipoCliente) {
		this.tipoCliente = tipoCliente.getCodigo();
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
