package com.reis.pdv.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reis.pdv.dto.SaleDTO;
import com.reis.pdv.service.SaleService;

@Controller
@RequestMapping("/sale")
public class SaleController {
	
	private SaleService saleService;
	
	public SaleController(SaleService saleService) {
		this.saleService = saleService; 
	}
	
	@GetMapping()
	public ResponseEntity getAll() {
		return new ResponseEntity<>(saleService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity getById(@PathVariable long id) {
		return new ResponseEntity<>(saleService.getById(id),HttpStatus.OK);
		
	}
	
	@PostMapping()
	public ResponseEntity post(@RequestBody SaleDTO saleDTO) {
		long id = saleService.save(saleDTO);
		try {
			return new ResponseEntity<>("Venda realizada com sucesso " + id, HttpStatus.CREATED);
		}catch(Exception error) {
			return new ResponseEntity<>(error.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
}
