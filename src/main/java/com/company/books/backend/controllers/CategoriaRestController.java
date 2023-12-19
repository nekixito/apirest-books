package com.company.books.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
	public CategoriaResponseRest consultaCategorias() {
		CategoriaResponseRest response = service.buscarCategorias();
		return response;
	}
	
}
