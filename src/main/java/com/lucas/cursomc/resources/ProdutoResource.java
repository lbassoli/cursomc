package com.lucas.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.cursomc.domain.Produto;
import com.lucas.cursomc.dto.ProdutoDTO;
import com.lucas.cursomc.resources.services.ProdutoService;
import com.lucas.cursomc.resources.utils.URL;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) throws ObjectNotFoundException {
		Produto obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(name="nome", defaultValue="0") String nome,
			@RequestParam(name="categorias", defaultValue="0") String categorias,
			@RequestParam(name="page", defaultValue="0") Integer page,
			@RequestParam(name="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(name="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(name="direction", defaultValue="ASC") String direction
			) throws ObjectNotFoundException {
		Page<Produto> lista = service.search(URL.decodeParam(nome), URL.decodeIntList(categorias), page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(lista.map(a -> new ProdutoDTO(a)));
	}
}
