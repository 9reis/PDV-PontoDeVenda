package com.reis.pdv.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 100, nullable = false)
	@NotBlank(message = "Campo nome é obrigatório")
	private String name;
	
	@Column(length = 30, nullable = false)
	@NotBlank(message = "O campo username é obrigatório")
	private String username; 
	
	@Column(length = 60, nullable = false)
	@NotBlank(message = "O campo senha é obrigatório ")
	private String password;
	
	private boolean isEnabled;
	
	@OneToMany(mappedBy = "user")
	private List<Sale> sale; 
	
}
