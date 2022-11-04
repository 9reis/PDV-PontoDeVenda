package com.reis.pdv.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data 
public class ProductDTO {
	private Long id;
	private String description;
	private BigDecimal price;
	private int quantity;
}
