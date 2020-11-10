package com.demo.rest;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.demo.model.ProductModel;
import com.demo.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductsControllerIntegrationTest {
	
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    
    @BeforeAll
    public void setup() {
    	mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity()) // enable security for the mock set up
                .build();
    }
	
	@MockBean
	private ProductService productService;
	
	@Test
	public void getUserById() throws Exception {
		// prepariamo il mock dei dati
		ProductModel product = new ProductModel();
		product.setName("Monitor");
		product.setDescription("hp");
		
		// Enables stubbing methods. Use it when you want the mock to return particular value when particular method is called.
		// "When the x method is called then return y".
		when(productService.getById(ArgumentMatchers.anyInt())).thenReturn(product);
		
		// Eseguo il test con mockmvc
		this.mockMvc.perform(get("/api/products/10").with(httpBasic("user", "user")))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("Monitor"))
			.andExpect(jsonPath("$.description").value("hp"));
		
	}
	
	@Test
	public void saveProduct() throws Exception {
		// prepariamo il mock dei dati
		ProductModel product = new ProductModel();
		product.setId(10);
		product.setName("Monitor");
		product.setDescription("hp");
		when(productService.save(ArgumentMatchers.any(ProductModel.class))).thenReturn(product);
		
		// Eseguo il test con mockmvc
		this.mockMvc.perform(post("/api/products").with(httpBasic("user", "user"))
			.content(new ObjectMapper().writeValueAsBytes(product))
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").exists())
			.andExpect(jsonPath("$.name").value("Monitor"))
			.andExpect(jsonPath("$.description").value("hp"));
	}

}
