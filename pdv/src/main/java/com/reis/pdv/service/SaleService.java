package com.reis.pdv.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.reis.pdv.dto.ProductDTO;
import com.reis.pdv.dto.SaleDTO;
import com.reis.pdv.entity.ItemSale;
import com.reis.pdv.entity.Sale;
import com.reis.pdv.entity.User;
import com.reis.pdv.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service	
@RequiredArgsConstructor
public class SaleService {
	
	private final UserRepository userRepository;
	
	public long save(SaleDTO sale) {
		
		User user = userRepository.findById(sale.getUserid()).get();
		
		Sale newSale = new Sale();
		newSale.setUser(user);
		newSale.setDate(LocalDate.now());
		
	}
	
	private List<ItemSale> getItemSale(List<ProductDTO> products){
		
	}

}
