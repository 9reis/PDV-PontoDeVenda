package com.reis.pdv.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
	
	@NotBlank(message = "O campo Login é obrigatório")
	private String username;
	@NotBlank(message = "O campo Password é obrigatório")
	private String password;
	
}
