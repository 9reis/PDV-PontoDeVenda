package com.reis.pdv.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reis.pdv.entity.Product;
import com.reis.pdv.repository.ProductRepository;

@Controller
@RequestMapping("/product")
public class ProductController {

	private ProductRepository productRepository;
	
	public ProductController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	
	@GetMapping()
	public ResponseEntity getAll() { //public ResponseEntity<List<Product>> getAll() 
		//public List<Product> getAll()	
		//return productRepository.findAll()
		return new ResponseEntity<>(productRepository.findAll(),HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity post(@RequestBody Product product) {
		try {
			return new ResponseEntity<>(productRepository.save(product), HttpStatus.CREATED);
		}catch(Exception error) {
			return new ResponseEntity<>(error.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping()
	public ResponseEntity put(@RequestBody Product product) {
		try {
			return new ResponseEntity<>(productRepository.save(product),HttpStatus.OK);
		}catch(Exception error) {
			return new ResponseEntity<>(error.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity delete(@PathVariable Long id) {
		try {
			productRepository.deleteById(id);
			return new ResponseEntity<>("Produto removido com sucesso!",HttpStatus.OK);
		}catch(Exception error) {
			return new ResponseEntity<>(error.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}