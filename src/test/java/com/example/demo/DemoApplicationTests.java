package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.demo.entity.CountryEnum;
import com.example.demo.entity.Product;
import com.example.demo.rest.ProductRepository;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class DemoApplicationTests {

	private static final String API_ROOT = "/api/v0/products/";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setup() {
		productRepository.deleteAll();
		Product sampleProduct = new Product();
		sampleProduct.setCountry(CountryEnum.USA);
		sampleProduct.setName("sampleProduct");
		sampleProduct.setPrice(new BigDecimal(1000));
		productRepository.saveAndFlush(sampleProduct); // sample product to be edited in tests or ill define it here
	}

	@Test
	void testGetProductByIdNotFound() {
		try {
			// negative ID never exists
			mockMvc.perform(get(API_ROOT + "-1")).andExpect(status().isNotFound());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testGetProductByIdisOk() {
		try {
			MvcResult result = mockMvc.perform(get(API_ROOT + "2")).andExpect(status().isOk()).andReturn();
			String json = result.getResponse().getContentAsString();
			Product product = objectMapper.readValue(json, Product.class);
			assertNotNull(product);
			assertEquals(2L, product.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testPostProductisOk() {
		try {
			MvcResult result = mockMvc.perform(get(API_ROOT + "2")).andExpect(status().isOk()).andReturn();
			String json = result.getResponse().getContentAsString();
			Product product = objectMapper.readValue(json, Product.class);
			assertNotNull(product);
			assertEquals(2L, product.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
