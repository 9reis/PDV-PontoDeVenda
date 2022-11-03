package com.reis.pdv.controller;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
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

import com.reis.pdv.dto.ResponseDTO;
import com.reis.pdv.entity.User;
import com.reis.pdv.exceptions.NoItemException;
import com.reis.pdv.repository.UserRepository;
import com.reis.pdv.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	private UserService userService;
	
	//@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping()
	public ResponseEntity getAll() {
		
		return new ResponseEntity<>(userService.findAll(),HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity post(@RequestBody User user) {
		
		try {
			user.setEnabled(true);
			return new ResponseEntity<>(userService.save(user),HttpStatus.CREATED);
		}catch(Exception error) {
			return new ResponseEntity<>(error.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping()
	public ResponseEntity put( @RequestBody User user) {
		try {
			return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
		}catch(Exception error) {
			return new ResponseEntity<>(new ResponseDTO(error.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity delete(@PathVariable Long id) {
		
		try {
			userService.deleteById(id);
			return new ResponseEntity<>(new ResponseDTO("Usuário removico com sucesso"),HttpStatus.OK);
		}catch(EmptyResultDataAccessException error) {
			return new ResponseEntity<>(new ResponseDTO("Não foi possivel localizar o usuário!"), HttpStatus.BAD_REQUEST);
		}catch(Exception error) {
			return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
