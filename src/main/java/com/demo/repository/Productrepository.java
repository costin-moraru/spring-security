package com.demo.repository;

import java.util.List;
import java.util.Optional;

import com.demo.model.ProductModel;

public interface Productrepository {
	
	List<ProductModel> findAll();
	
	ProductModel save(ProductModel productModel);
	
	Optional<ProductModel> getById(Integer id);

}
