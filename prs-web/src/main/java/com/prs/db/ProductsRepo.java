package com.prs.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.model.Products;

public interface ProductsRepo extends JpaRepository<Products, Integer> {

}
