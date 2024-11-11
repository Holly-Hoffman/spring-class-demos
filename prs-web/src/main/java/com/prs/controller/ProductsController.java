package com.prs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.db.ProductsRepo;
import com.prs.model.Products;

@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductsController {
	@Autowired
	private ProductsRepo productsRepo;
	
	@GetMapping ("/")
	public List<Products> getAllProducts()
	{
		return productsRepo.findAll();
	}
	
	@GetMapping ("/{id}")
	public Optional<Products> getProductById(@PathVariable int id)
	{
		Optional<Products> p = productsRepo.findById(id);
		if (p.isPresent())
		{return p;}
		else
		{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found for id "+id);}
	}
	
	@PostMapping ("")
	public Products addProduct (@RequestBody Products products)
	{
		return productsRepo.save(products);
	}
	
	@PutMapping ("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putProduct(@PathVariable int id, @RequestBody Products products)
	{
		if (id != products.getId())
			{throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product ID mismatch vs URL.");}
		else if (productsRepo.existsById(products.getId()))
			{productsRepo.save(products);}
		else
		{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found for id "+id);}
	}
	

	@DeleteMapping ("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProduct (@PathVariable int id)
	{
		if (productsRepo.existsById(id))
		{productsRepo.deleteById(id);}
		else
		{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found for id "+id);}
	}

}
