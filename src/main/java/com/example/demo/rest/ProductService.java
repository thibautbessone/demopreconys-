package com.example.demo.rest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CountryEnum;
import com.example.demo.entity.Product;
import com.example.demo.utils.TaxStrategy;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepo;

	private Map<CountryEnum, TaxStrategy> strategies;

	@Autowired
	public ProductService(List<TaxStrategy> taxStrategies) {
		this.strategies = taxStrategies.stream().collect(Collectors.toMap(TaxStrategy::handles, Function.identity()));
	}

	public Optional<Product> getProductById(Long productId) {
		return productRepo.findById(productId);
	}

	public Product createProduct(Product newProduct) {
		return productRepo.save(newProduct);
	}

	public BigDecimal getFinalPrice(Long productId) {
		Product desiredProduct = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
		BigDecimal finalPrice;
		if (strategies.containsKey(desiredProduct.getCountry())) {
			finalPrice = strategies.get(desiredProduct.getCountry()).calculateTax(desiredProduct);
		} else
			throw new RuntimeException("Unsupported Country"); // ResponseEntity avec 422
		return finalPrice; // nothing if exception thrown
	}

}
