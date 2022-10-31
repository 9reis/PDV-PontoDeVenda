package com.reis.pdv.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.reis.pdv.dto.ProductDTO;
import com.reis.pdv.dto.SaleDTO;
import com.reis.pdv.entity.ItemSale;
import com.reis.pdv.entity.Product;
import com.reis.pdv.entity.Sale;
import com.reis.pdv.entity.User;
import com.reis.pdv.repository.ItemSaleRepository;
import com.reis.pdv.repository.ProductRepository;
import com.reis.pdv.repository.SaleRepository;
import com.reis.pdv.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service	
@RequiredArgsConstructor
public class SaleService {
	
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final SaleRepository saleRepository;
	private final ItemSaleRepository itemSaleRepository;
	
	@Transactional
	public long save(SaleDTO sale) {
		
		User user = userRepository.findById(sale.getUserid()).get();
		
		Sale newSale = new Sale();
		newSale.setUser(user);
		newSale.setDate(LocalDate.now());
		
		List<ItemSale> itemSale = getItemSale(sale.getItems());
		
		newSale = saleRepository.save(newSale);
		
		//itemns
		saveItemSale(itemSale, newSale);
		
		return newSale.getId();
		
	}
	
	private void saveItemSale(List<ItemSale> items, Sale newSale) {
		for(ItemSale item: items) {
			item.setSale(newSale);
			itemSaleRepository.save(item);
		}
	};
	
	private List<ItemSale> getItemSale(List<ProductDTO> products){
		
		return products.stream().map(item -> {
			Product product = productRepository.getReferenceById(item.getProductid());
			
			ItemSale itemSale = new ItemSale();
			itemSale.setProduct(product);
			itemSale.setQuantity(item.getQuantity());
			
			return itemSale;
			
		}).collect(Collectors.toList());
	}

}
