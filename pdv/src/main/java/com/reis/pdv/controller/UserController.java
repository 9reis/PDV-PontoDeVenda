package com.reis.pdv.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reis.pdv.entity.User;
import com.reis.pdv.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {

	private UserRepository userRepository;
	
	//@Autowired
	public UserController(UserRepository useRepository) {
		this.userRepository = useRepository;
	}
	
	@GetMapping()
	public ResponseEntity getAll() {
		return new ResponseEntity<>(userRepository.findAll(),HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity post(@RequestBody User user) {
		
		try {
			user.setEnabled(true);
			return new ResponseEntity<>(userRepository.save(user),HttpStatus.CREATED);
		}catch(Exception error) {
			return new ResponseEntity<>(error.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping()
	public ResponseEntity put( @RequestBody User user) {
		Optional<User> userToEdit = userRepository.findById(user.getId());
	
		if(userToEdit.isPresent()) {
			userRepository.save(user);
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		
		return ResponseEntity.notFound().build();
	}
	
}
