package com.reis.pdv.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.reis.pdv.dto.ProductSaleDTO;
import com.reis.pdv.dto.ProductInfoDTO;
import com.reis.pdv.dto.SaleDTO;
import com.reis.pdv.dto.SaleInfoDTO;
import com.reis.pdv.entity.ItemSale;
import com.reis.pdv.entity.Product;
import com.reis.pdv.entity.Sale;
import com.reis.pdv.entity.User;
import com.reis.pdv.exceptions.InvalidOperationException;
import com.reis.pdv.exceptions.NoItemException;
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
	
	/*
	 {
	 "user": "teste",
	 "data":03/07/22,
	 "products":[
	 {
	 	"description":"notebook",
	 	"quantity":1
	 }
	 ]
	 }
	 */
	
	public List<SaleInfoDTO> findAll(){
		return saleRepository.findAll().stream().map(sale -> getSaleInfo(sale)).collect(Collectors.toList());
	}
	
	private SaleInfoDTO getSaleInfo(Sale sale){
		return SaleInfoDTO.builder()
				.user(sale.getUser().getName())
				.data(sale.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyy")))
				.products(getProductInfo(sale.getItems()))
				.build();
	}
	
	private List<ProductInfoDTO> getProductInfo(List<ItemSale> items){
		
		if(CollectionUtils.isEmpty(items)) {
			return Collections.emptyList();
		}
		
		return items.stream().map(
				item -> ProductInfoDTO.builder()
						.id(item.getId())
						.description(item.getProduct().getDescription())
						.quantity(item.getQuantity())
						.build()
		).collect(Collectors.toList());
	}
	
	
	
	@Transactional
	public long save(SaleDTO sale) {
		
		User user = userRepository.findById(sale.getUserid())
				.orElseThrow(() -> new NoItemException("Usu??rio n??o encontrado"));

			Sale newSale = new Sale();
			newSale.setUser(user);
			newSale.setDate(LocalDate.now());
			
			List<ItemSale> itemSale = getItemSale(sale.getItems());
			
			newSale = saleRepository.save(newSale);
			
			//items
			saveItemSale(itemSale, newSale);
			
			return newSale.getId();
	}
	
	private void saveItemSale(List<ItemSale> items, Sale newSale) {
		for(ItemSale item: items) {
			item.setSale(newSale);
			itemSaleRepository.save(item);
		}
	};
	
	private List<ItemSale> getItemSale(List<ProductSaleDTO> products) {
		
		if( products.isEmpty() ) {
			throw new InvalidOperationException("N??o ?? possivel adicionar a venda sem itens");
		}
		
		return products.stream().map(item -> {
			Product product = productRepository.findById(item.getProductid())
					.orElseThrow(() -> new NoItemException("Item da venda n??o encontrado"));
			
			ItemSale itemSale = new ItemSale();
			itemSale.setProduct(product);
			itemSale.setQuantity(item.getQuantity());
			
			if(product.getQuantity() == 0) {
				throw new NoItemException("Produto sem estoque!");
			}else if ( product.getQuantity() < item.getQuantity()){
				throw new InvalidOperationException(String.format("A quantidade de itens da venda (%s) " +
						"?? maior que a quantidade dispon??vel no estoque (%s) ", item.getQuantity() , product.getQuantity()));
			}
			
			int total = product.getQuantity() - item.getQuantity();
			product.setQuantity(total);
			productRepository.save(product);
			
			
			return itemSale;
			
		}).collect(Collectors.toList());
	}

	public SaleInfoDTO getById(long id) {
		Sale sale = saleRepository.findById(id)
				.orElseThrow(()-> new NoItemException("venda n??o encontrada"));
		return getSaleInfo(sale);
		
	}

}
