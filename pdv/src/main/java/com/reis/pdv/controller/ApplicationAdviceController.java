package com.reis.pdv.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.reis.pdv.dto.ResponseDTO;
import com.reis.pdv.exceptions.InvalidOperationException;
import com.reis.pdv.exceptions.NoItemException;

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
}
