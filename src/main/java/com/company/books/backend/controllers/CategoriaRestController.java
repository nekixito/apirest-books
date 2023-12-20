package com.company.books.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.books.backend.response.CategoriaResponseRest;
import com.company.books.backend.services.ICategoriaService;

@RestController
@RequestMapping("/v1")
public class CategoriaRestController {
	
	@Autowired
	private ICategoriaService service;
	
	@GetMapping("/categorias")
	public ResponseEntity<CategoriaResponseRest> consultaCategorias() {
		
		ResponseEntity<CategoriaResponseRest> response = service.buscarCategorias();
		return response;
	}
	
	@GetMapping("/categorias/{id}")
	public ResponseEntity<CategoriaResponseRest> ConsultaPorId(@PathVariable Long id){
		ResponseEntity<CategoriaResponseRest> response = service.buscarPorId(id);
		return response;
	}
	
	
}
