package com.reis.pdv.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.reis.pdv.dto.ProductDTO;
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
		SaleInfoDTO saleInfoDTO = new SaleInfoDTO();
		saleInfoDTO.setUser(sale.getUser().getName());
		saleInfoDTO.setData(sale.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyy")));
		saleInfoDTO.setProducts(getProductInfo(sale.getItems()));
		
		return saleInfoDTO;
	}
	
	private List<ProductInfoDTO> getProductInfo(List<ItemSale> items){
		return items.stream().map(item -> {
			ProductInfoDTO productInfoDTO = new ProductInfoDTO();
			productInfoDTO.setId(item.getId());
			productInfoDTO.setDescription(item.getProduct().getDescription());
			productInfoDTO.setQuantity(item.getQuantity());
			return productInfoDTO;
		}).collect(Collectors.toList());
	}
	
	
	
	@Transactional
	public long save(SaleDTO sale) {
		
		User user = userRepository.findById(sale.getUserid())
				.orElseThrow(() -> new NoItemException("Usuário não encontrado"));

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
	
	private List<ItemSale> getItemSale(List<ProductDTO> products) {
		
		if( products.isEmpty() ) {
			throw new InvalidOperationException("Não é possivel adicionar a venda sem itens");
		}
		
		return products.stream().map(item -> {
			Product product = productRepository.findById(item.getProductid())
					.orElseThrow(() -> new NoItemException("Item da venda não encontrado"));
			
			ItemSale itemSale = new ItemSale();
			itemSale.setProduct(product);
			itemSale.setQuantity(item.getQuantity());
			
			if(product.getQuantity() == 0) {
				throw new NoItemException("Produto sem estoque!");
			}else if ( product.getQuantity() < item.getQuantity()){
				throw new InvalidOperationException(String.format("A quantidade de itens da venda (%s) " +
						"é maior que a quantidade disponível no estoque (%s) ", item.getQuantity() , product.getQuantity()));
			}
			
			int total = product.getQuantity() - item.getQuantity();
			product.setQuantity(total);
			productRepository.save(product);
			
			
			return itemSale;
			
		}).collect(Collectors.toList());
	}

	public SaleInfoDTO getById(long id) {
		Sale sale = saleRepository.findById(id)
				.orElseThrow(()-> new NoItemException("venda não encontrada"));
		return getSaleInfo(sale);
		
	}

}
