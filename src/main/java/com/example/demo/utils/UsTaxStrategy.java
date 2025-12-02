package com.example.demo.utils;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.example.demo.entity.CountryEnum;
import com.example.demo.entity.Product;

@Component
public class UsTaxStrategy implements TaxStrategy {

	@Override
	public BigDecimal calculateTax(Product product) {
		// USA prices will be x2 thanks to tariffs
		return product.getPrice().multiply(BigDecimal.TWO);
	}

	@Override
	public CountryEnum handles() {
		return CountryEnum.USA;
	}

}
