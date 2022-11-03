package com.reis.pdv.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
	
	private long id; 
	private String name; 
	private boolean isEnabled; 
	
}
