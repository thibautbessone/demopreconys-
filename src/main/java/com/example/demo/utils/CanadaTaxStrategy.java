package com.example.demo.utils;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.example.demo.entity.CountryEnum;
import com.example.demo.entity.Product;

@Component
public class CanadaTaxStrategy implements TaxStrategy {


	@Override
	public BigDecimal calculateTax(Product product) {
		// Canada will have prices halved because they're nice
		return product.getPrice().divide(BigDecimal.TWO);
	}

	@Override
	public CountryEnum handles() {
		return CountryEnum.CANADA;
	}

}
