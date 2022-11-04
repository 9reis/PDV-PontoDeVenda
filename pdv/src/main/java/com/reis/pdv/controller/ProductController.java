package com.reis.pdv.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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

import com.reis.pdv.dto.ProductDTO;
import com.reis.pdv.dto.ResponseDTO;
import com.reis.pdv.entity.Product;
import com.reis.pdv.repository.ProductRepository;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	private ModelMapper mapper;
	private ProductRepository productRepository;
	
	public ProductController(ProductRepository productRepository) {
		this.productRepository = productRepository;
		this.mapper = new ModelMapper();
	}
	
	
	@GetMapping()
	public ResponseEntity getAll() { //public ResponseEntity<List<Product>> getAll() 
		//public List<Product> getAll()	
		//return productRepository.findAll()
		return new ResponseEntity<>(productRepository.findAll(),HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity post(@Valid @RequestBody ProductDTO product) {
		try {
			return new ResponseEntity<>(productRepository.save(mapper.map(product, Product.class)), HttpStatus.CREATED);
		}catch(Exception error) {
			return new ResponseEntity<>(new ResponseDTO(error.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping()
	public ResponseEntity put(@Valid @RequestBody ProductDTO product) {
		try {
			return new ResponseEntity<>(productRepository.save(mapper.map(product, Product.class)),HttpStatus.OK);
		}catch(Exception error) {
			return new ResponseEntity<>(new ResponseDTO(error.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity delete(@PathVariable Long id) {
		try {
			productRepository.deleteById(id);
			return new ResponseEntity<>(new ResponseDTO("Produto removido com sucesso!"),HttpStatus.OK);
		}catch(Exception error) {
			return new ResponseEntity<>(new ResponseDTO(error.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
