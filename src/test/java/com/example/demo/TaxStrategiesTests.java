package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.example.demo.entity.CountryEnum;
import com.example.demo.entity.Product;
import com.example.demo.utils.CanadaTaxStrategy;
import com.example.demo.utils.FranceTaxStrategy;
import com.example.demo.utils.UsTaxStrategy;

@TestInstance(Lifecycle.PER_CLASS)
public class TaxStrategiesTests {

	private UsTaxStrategy usTaxStrategy = new UsTaxStrategy();
	private CanadaTaxStrategy canadaTaxStrategy = new CanadaTaxStrategy();
	private FranceTaxStrategy franceTaxStrategy = new FranceTaxStrategy();


	@Test
	void testUsTaxStrategyHandling() {
		assertTrue(CountryEnum.USA.equals(usTaxStrategy.handles()));
	}

	@Test
	void testCanadaTaxStrategyHandling() {
		assertTrue(CountryEnum.CANADA.equals(canadaTaxStrategy.handles()));
	}

	@Test
	void testFranceTaxStrategyHandling() {
		assertTrue(CountryEnum.FRANCE.equals(franceTaxStrategy.handles()));
	}

	@Test
	void testUsTaxStrategy() {
		Product testProduct = new Product();
		testProduct.setPrice(new BigDecimal(1000));
		// price of 1000
		// us tax is x2
		// expecting 20000
		assertEquals(new BigDecimal(2000), usTaxStrategy.calculateTax(testProduct));
	}

	@Test
	void testCanadaTaxStrategy() {
		Product testProduct = new Product();
		testProduct.setPrice(new BigDecimal(1000));
		// price of 1000
		// can tax is /2
		// expecting 500
		assertEquals(new BigDecimal(500), canadaTaxStrategy.calculateTax(testProduct));
	}

	@Test
	void testFranceTaxStrategy() {
		Product testProduct = new Product();
		testProduct.setPrice(new BigDecimal(1000));
		// price of 1000
		// france tax is +20
		// expecting 1020
		assertEquals(new BigDecimal(1020), franceTaxStrategy.calculateTax(testProduct));
	}
}
