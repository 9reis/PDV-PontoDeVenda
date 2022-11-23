package com.reis.pdv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor	
public class UserResponseDTO {
	
	private long id; 
	private String name; 
	private String username; 
	private boolean isEnabled; 
}
