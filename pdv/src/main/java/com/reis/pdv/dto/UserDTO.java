package com.reis.pdv.dto;

import javax.validation.constraints.NotBlank;

import com.reis.pdv.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
	
	private long id; 
	@NotBlank(message = "Campo nome é obrigatório")
	private String name; 
	@NotBlank(message = "O campo username é obrigatório")
	private String username; 
	@NotBlank(message = "O campo senha é obrigatório ")
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
