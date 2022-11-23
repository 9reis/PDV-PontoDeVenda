package com.reis.pdv.dto;

import java.util.List;

import com.reis.pdv.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
	
	private long id; 
	private String name; 
	private String username; 
	private String password;
	private boolean isEnabled; 
	
	public User toEntity() {
		User userToSave = new User();
		userToSave.setEnabled(isEnabled());
		userToSave.setName(getName());
		userToSave.setId(getId());
		return userToSave;
		
	}
}
