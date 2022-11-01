package com.reis.pdv.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaleInfoDTO {
	
	private String user;
	private String data;
	private List<ProductInfoDTO> products;
}
