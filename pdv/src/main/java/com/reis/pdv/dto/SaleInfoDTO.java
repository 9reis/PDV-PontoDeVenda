package com.reis.pdv.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SaleInfoDTO {
	
	private String user;
	private String data;
	private List<ProductInfoDTO> products;
}
