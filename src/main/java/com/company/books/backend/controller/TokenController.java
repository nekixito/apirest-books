package com.company.books.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.books.backend.request.AuthRequest;
import com.company.books.backend.response.TokenResponse;

@RestController
@RequestMapping("/v1")
public class TokenController {
	
	@PostMapping("/autenticate")
	public ResponseEntity<TokenResponse> autenticate(@RequestBody AuthRequest request){
		
		return null;
	}
	
}
