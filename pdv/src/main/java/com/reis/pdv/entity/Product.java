package com.reis.pdv.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "product")
@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 100, nullable = false)
	@NotBlank(message = "O compo descrição é obrigatório.")
	private String description;
	
	@Column(length = 20, precision = 20, scale = 2, nullable = false)
	@NotNull(message = "O campo preço é obrigatório.")
	private BigDecimal price;
	
	@Column(nullable = false)
	@NotNull(message = "O campo quantidade é obrigatório")
	@Min(1)
	private int quantity;

}
