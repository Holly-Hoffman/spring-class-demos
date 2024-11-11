package com.prs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.db.VendorRepo;
import com.prs.model.Vendor;

@CrossOrigin
@RestController
@RequestMapping("/api/vendors")
public class VendorController {
	@Autowired
	private VendorRepo vendorRepo;
	
	@GetMapping ("/")
	public List<Vendor> getAllVendors()
	{
		return vendorRepo.findAll();
	}
	
	@GetMapping ("/{id}")
	public Optional<Vendor> getVendorById(@PathVariable int id)
	{
		Optional<Vendor> v = vendorRepo.findById(id);
		if (v.isPresent())
		{return v;}
		else
		{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor not found for id "+id);}
	}
	
	@PostMapping ("")
	public Vendor addVendor (@RequestBody Vendor vendor)
	{
		return vendorRepo.save(vendor);
	}
	
	@PutMapping ("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putVendor(@PathVariable int id, @RequestBody Vendor vendor)
	{
		if (id != vendor.getId())
			{throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vendor ID mismatch vs URL.");}
		else if (vendorRepo.existsById(vendor.getId()))
			{vendorRepo.save(vendor);}
		else
		{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor not found for id "+id);}
	}
	

	@DeleteMapping ("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteVendor (@PathVariable int id)
	{
		if (vendorRepo.existsById(id))
		{vendorRepo.deleteById(id);}
		else
		{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor not found for id "+id);}
	}

}