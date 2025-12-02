package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.demo.entity.CountryEnum;
import com.example.demo.entity.Product;
import com.example.demo.rest.ProductRepository;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class DemoApplicationTests {

	private static final String API_ROOT = "/api/v0/products";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ObjectMapper objectMapper;

	private String string;

	@BeforeAll
	void setup() {
		productRepository.deleteAll();
		Product sampleProduct = new Product();
		sampleProduct.setCountry(CountryEnum.USA);
		sampleProduct.setName("sampleProduct");
		sampleProduct.setPrice(new BigDecimal(1000));
		productRepository.saveAndFlush(sampleProduct);
	}

	@Test
	void testGetProductByIdNotFound() {
		try {
			// negative ID never exists therefore should return 404
			mockMvc.perform(get(API_ROOT + "/-1")).andExpect(status().isNotFound());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testGetProductByIdIsOk() {
		try {
			MvcResult result = mockMvc.perform(get(API_ROOT + "/1")).andExpect(status().isOk()).andReturn();

			String json = result.getResponse().getContentAsString();
			Product product = objectMapper.readValue(json, Product.class);
			assertNotNull(product);
			assertEquals(1L, product.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testPostProductByIdIsOk() {
		try {
			ObjectNode toCreate = objectMapper.createObjectNode();
			toCreate.put("name", "productToCreate");
			toCreate.put("country", "FRANCE");
			toCreate.put("price", 10);
			// expected object is
			// {"country":"FRANCE","id":2,"name":"productToCreate","price":10} (
			// alphabetical order + generated ID
			String expectedResult = "{\"country\":\"FRANCE\",\"id\":2,\"name\":\"productToCreate\",\"price\":10}";

			MvcResult result = mockMvc
					.perform(post(API_ROOT).contentType(MediaType.APPLICATION_JSON).content(toCreate.toString()))
					.andExpect(status().isOk()).andReturn();

			String resultString = result.getResponse().getContentAsString();
			assertEquals(expectedResult, resultString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
