package com.reis.pdv.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.reis.pdv.dto.UserDTO;
import com.reis.pdv.dto.UserResponseDTO;
import com.reis.pdv.entity.User;
import com.reis.pdv.exceptions.NoItemException;
import com.reis.pdv.repository.UserRepository;
import com.reis.pdv.security.SecurityConfig;

@Service
public class UserService {
	
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	private ModelMapper mapper = new ModelMapper();
	
	public List<UserResponseDTO> findAll(){
		return userRepository.findAll().stream().map(user ->
			new UserResponseDTO(user.getId(), user.getName(),user.getUsername(), user.isEnabled())
		).collect(Collectors.toList());
	}
	
	public UserDTO save(UserDTO user) {
		user.setPassword(SecurityConfig.passwordEncoder().encode(user.getPassword()));
		User userToSave = mapper.map(user, User.class);
		userRepository.save(userToSave);
		return new UserDTO(userToSave.getId(), userToSave.getName(),userToSave.getUsername(),userToSave.getPassword() ,userToSave.isEnabled());
	}
	
	public UserDTO findById(long id) {
		Optional<User> optional = userRepository.findById(id);
		
		if(!optional.isPresent()) {
			throw new NoItemException("Usuario não encontrado");
		}
		
		User user = optional.get();
		
		return new UserDTO(user.getId(), user.getName(),user.getUsername(),user.getPassword() , user.isEnabled());
	}
	
	public UserDTO update(UserDTO user) {
		user.setPassword(SecurityConfig.passwordEncoder().encode(user.getPassword()));
		User userToSave = mapper.map(user, User.class);
		
		Optional<User> userToEdit = userRepository.findById(userToSave.getId());
		
		if(!userToEdit.isPresent()) {
			throw new NoItemException("Usuario não encontrado");
		}
		userRepository.save(userToSave);
		return new UserDTO(userToSave.getId(), userToSave.getName(),userToSave.getUsername(), userToSave.getPassword() , userToSave.isEnabled());
	}
	
	public void deleteById(long id) {
		userRepository.deleteById(id);
	}
}
