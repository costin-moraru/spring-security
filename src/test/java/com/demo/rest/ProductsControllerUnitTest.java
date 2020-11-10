package com.demo.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.demo.model.ProductModel;
import com.demo.service.ProductService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductsControllerUnitTest {
	
	@MockBean
	private ProductService productService;
	
	@Test
	public void getUserById() throws Exception {
		// prepariamo il mock dei dati
		ProductModel product = new ProductModel();
		product.setId(1);
		product.setName("Monitor");
		product.setDescription("hp");
		
		// Enables stubbing methods. Use it when you want the mock to return particular value when particular method is called.
		// "When the x method is called then return y".
		when(productService.getById(ArgumentMatchers.anyInt())).thenReturn(product);
		
		ProductModel response = productService.getById(1);
		
		assertThat(response).isNotNull();
		
		assertEquals(1, response.getId());
	}
	
	@Test
	public void saveProduct() throws Exception {
		// prepariamo il mock dei dati
		ProductModel product = new ProductModel();
		product.setId(10);
		product.setName("Monitor");
		product.setDescription("hp");
		when(productService.save(ArgumentMatchers.any(ProductModel.class))).thenReturn(product);
		
		ProductModel response = productService.save(product);
		
		assertThat(response).isNotNull();
		
		assertEquals(10, response.getId());
	}

}
