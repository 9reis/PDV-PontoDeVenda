package com.reis.pdv.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reis.pdv.dto.LoginDTO;
import com.reis.pdv.dto.ResponseDTO;
import com.reis.pdv.security.CustomUserDetailService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
	
	private final CustomUserDetailService userDetailService;	
	
	@PostMapping
	public ResponseEntity post(@Valid @RequestBody LoginDTO loginData) {
		try {
			
			userDetailService.verifyUserCredentials(loginData);
			// Gerar o Token 
			
			
		}catch(Exception error) {
			return new ResponseEntity<>(new ResponseDTO(error.getMessage()), HttpStatus.UNAUTHORIZED);
		}
	}
}
