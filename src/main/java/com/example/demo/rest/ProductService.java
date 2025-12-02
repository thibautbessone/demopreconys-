package com.example.demo.rest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CountryEnum;
import com.example.demo.entity.Product;
import com.example.demo.utils.TaxStrategy;

@Service
public class ProductService {

	private static Logger LOG = LoggerFactory.getLogger(ProductService.class);

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

	public Optional<BigDecimal> getFinalPrice(Long productId) {
		Optional<BigDecimal> finalPrice = Optional.empty();
		Optional<Product> desiredProduct = productRepo.findById(productId);

		// Validate product exists & is valid

		if (desiredProduct.isEmpty()) {
			LOG.trace("Cannot find product with ID", productId);
			return Optional.empty();
		}
		if (strategies.containsKey(desiredProduct.get().getCountry())) {
			finalPrice = Optional
					.of(strategies.get(desiredProduct.get().getCountry()).calculateTax(desiredProduct.get()));
		} else {
			LOG.trace("Unsupported Country", desiredProduct.get().getCountry());
			return Optional.empty();
		}
		return finalPrice;
	}

}
