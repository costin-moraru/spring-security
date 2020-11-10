package com.demo.service;

import java.util.List;

import com.demo.model.ProductModel;

public interface ProductService {

	List<ProductModel> findAll();
	
	ProductModel save(ProductModel product);
	
	ProductModel getById(Integer id);
}
