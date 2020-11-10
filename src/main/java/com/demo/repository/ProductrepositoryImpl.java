package com.demo.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.demo.model.ProductModel;

@Repository
public class ProductrepositoryImpl implements Productrepository {
	
	public static Integer currentIdInteger = 3;
	
	private List<ProductModel> products = Arrays.asList(
			new ProductModel(1, "Penna", "aDasdadad"),
			new ProductModel(2, "Tavolo", "aDasdadad"),
			new ProductModel(3, "Monitor", "aDasdadad")
			);

	@Override
	public List<ProductModel> findAll() {
		return products;
	}

	@Override
	public ProductModel save(ProductModel productModel) {
		productModel.setId(++ProductrepositoryImpl.currentIdInteger);
		products.add(productModel);
		return productModel;
	}

	@Override
	public Optional<ProductModel> getById(Integer id) {
		return products.stream().filter(p -> id.equals(p.getId())).findFirst();
	}

}
