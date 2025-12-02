package com.example.demo.utils;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.example.demo.entity.CountryEnum;
import com.example.demo.entity.Product;

@Component
public class FranceTaxStrategy implements TaxStrategy {

	@Override
	public BigDecimal calculateTax(Product product) {
		// France will have prices up +20
		return product.getPrice().add(BigDecimal.valueOf(20));
	}

	@Override
	public CountryEnum handles() {
		return CountryEnum.FRANCE;
	}

}
