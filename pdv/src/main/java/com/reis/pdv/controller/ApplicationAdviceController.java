package com.reis.pdv.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.reis.pdv.dto.ResponseDTO;
import com.reis.pdv.exceptions.InvalidOperationException;
import com.reis.pdv.exceptions.NoItemException;
import com.reis.pdv.exceptions.PasswordNotFoundException;

@RestControllerAdvice
public class ApplicationAdviceController {
	
	@ExceptionHandler(NoItemException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseDTO handleNoItemException(NoItemException ex) {
		String messageError = ex.getMessage();
		return new ResponseDTO(messageError);
	}
	
	@ExceptionHandler(InvalidOperationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseDTO handleInvalidOperationException(InvalidOperationException ex) {
		String messageError = ex.getMessage();
		return new ResponseDTO(messageError);
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseDTO handleUsernameNotFoundException(UsernameNotFoundException ex) {
		return new ResponseDTO(ex.getMessage());
	}

	@ExceptionHandler(PasswordNotFoundException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseDTO handlePasswordNotFoundException(PasswordNotFoundException ex) {
		return new ResponseDTO(ex.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseDTO handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException ex) {
		List<String> erros = new ArrayList<>();
		
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String errorMessage = error.getDefaultMessage();
			
			erros.add(errorMessage);
		});
		
		return new ResponseDTO(erros);
	}
	
	
	
}
