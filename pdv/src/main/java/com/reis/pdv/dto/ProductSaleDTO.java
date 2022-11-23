package com.reis.pdv.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductSaleDTO {
	
	@NotNull(message = "O item da venda é obrigatório!")
	private long productid;
	@NotNull(message = "O campo quantidade é obrigatório")
	private int quantity;
	
}
