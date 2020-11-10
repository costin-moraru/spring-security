package com.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.ProductModel;
import com.demo.repository.Productrepository;
import com.demo.service.ProductService;


@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private Productrepository productRepository;

	@Override
	public List<ProductModel> findAll() {
		return productRepository.findAll();
	}

	@Override
	public ProductModel save(ProductModel product) {
		return productRepository.save(product);
	}

	@Override
	public ProductModel getById(Integer id) {
		return productRepository.getById(id).orElseThrow(() -> new IllegalStateException("User not found"));
	}

}
