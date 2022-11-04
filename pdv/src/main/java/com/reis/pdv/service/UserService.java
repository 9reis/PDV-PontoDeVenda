package com.reis.pdv.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.reis.pdv.dto.UserDTO;
import com.reis.pdv.entity.User;
import com.reis.pdv.exceptions.NoItemException;
import com.reis.pdv.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public List<UserDTO> findAll(){
		return userRepository.findAll().stream().map(user ->
			new UserDTO(user.getId(), user.getName(), user.isEnabled())
		).collect(Collectors.toList());
	}
	
	public UserDTO save(UserDTO user) {
		User userToSave = new User();
		userToSave.setEnabled(user.isEnabled());
		userToSave.setName(user.getName());
		userRepository.save(userToSave);
		return new UserDTO(userToSave.getId(), userToSave.getName(), userToSave.isEnabled());
	}
	
	public UserDTO findById(long id) {
		Optional<User> optional = userRepository.findById(id);
		
		if(!optional.isPresent()) {
			throw new NoItemException("Usuario não encontrado");
		}
		
		User user = optional.get();
		
		return new UserDTO(user.getId(), user.getName(), user.isEnabled());
	}
	
	public UserDTO update(UserDTO user) {
		User userToSave = new User();
		userToSave.setEnabled(user.isEnabled());
		userToSave.setName(user.getName());
		userToSave.setId(user.getId());
		
		Optional<User> userToEdit = userRepository.findById(userToSave.getId());
		
		if(!userToEdit.isPresent()) {
			throw new NoItemException("Usuario não encontrado");
		}
		userRepository.save(userToSave);
		return new UserDTO(userToSave.getId(), userToSave.getName(), userToSave.isEnabled());
	}
	
	public void deleteById(long id) {
		userRepository.deleteById(id);
	}
}
