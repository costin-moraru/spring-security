package com.demo.rest;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.ProductModel;
import com.demo.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
	
	@Autowired
	private ProductService productService;
	
	
	@GetMapping
	public ResponseEntity<List<ProductModel>> getProducts() {
		List<ProductModel> products = productService.findAll();
		//return ResponseEntity.ok(products);
		return ResponseEntity.ok(Arrays.asList(new ProductModel(1, "Notebook", "HP")));
	}
	
	@PostMapping
	public ResponseEntity<ProductModel> save(@RequestBody() ProductModel product) {
		return new ResponseEntity<ProductModel>(productService.save(product), HttpStatus.CREATED);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<ProductModel> getOneById(@PathVariable(name = "productId") Integer id) {
		return new ResponseEntity<ProductModel>(productService.getById(id), HttpStatus.OK);
	}

}
