package com.example.demo.rest;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Product;

@RestController
@RequestMapping("/api/v0/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
		return productService.getProductById(productId).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public Product createProduct(@RequestBody Product product) {
		// TODO input sanitization beforehand
		// if wrong inputs types
		Product newProduct = new Product();
		newProduct.setName(product.getName());
		newProduct.setCountry(product.getCountry());
		newProduct.setPrice(product.getPrice());

		return productService.createProduct(newProduct);
	}

	@GetMapping("/{productId}/priceWithTax")
	public BigDecimal getPriceWithTaxById(@PathVariable Long productId) {
		return productService.getFinalPrice(productId);

	}

}
