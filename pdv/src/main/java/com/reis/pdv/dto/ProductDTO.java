package com.reis.pdv.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data 
public class ProductDTO {
	
	private Long id;
	@NotBlank(message = "O compo descrição é obrigatório.")
	private String description;
	@NotNull(message = "O campo preço é obrigatório.")
	private BigDecimal price;
	@NotNull(message = "O campo quantidade é obrigatório")
	private int quantity;
}
