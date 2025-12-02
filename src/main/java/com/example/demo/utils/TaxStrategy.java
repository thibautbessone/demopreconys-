package com.example.demo.utils;

import java.math.BigDecimal;

import com.example.demo.entity.CountryEnum;
import com.example.demo.entity.Product;

public interface TaxStrategy {

	public CountryEnum handles();

	public BigDecimal calculateTax(Product product);

}
