package com.bmdb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.bmdb.db.CreditRepo;
import com.bmdb.model.Credit;

@CrossOrigin
@RestController
@RequestMapping("/api/credits")
public class CreditController {
	
	@Autowired
	private CreditRepo creditRepo;
	
	@GetMapping("/")
	public List<Credit> getAllCredits()
	{
		return creditRepo.findAll();
	}
	
	@GetMapping ("/{id}")
	public Optional<Credit> getCreditById(@PathVariable int id)
	{
		Optional<Credit> c = creditRepo.findById(id);
		if (c.isPresent())
		{return c;}
		else
		{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit not found for id "+id);}
	}
	
	//returning credits for one movie
	@GetMapping ("/movie/{movieId}")
	public List<Credit> getCreditsForMovie(@PathVariable int movieId)
	{
		return creditRepo.findByMovieId(movieId);
	}
	
	//returning all credits for one actor
	@GetMapping ("/actor/{actorId}")
	public List<Credit> getCreditsForActor(@PathVariable int actorId)
	{
		return creditRepo.findByActorId(actorId);
	}
	
	
	@PostMapping ("")
	public Credit addCredit (@RequestBody Credit credit)
	{
		return creditRepo.save(credit);
	}
	
	@PutMapping ("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putCredit (@PathVariable int id, @RequestBody Credit credit)
	{
		if (id != credit.getId())
		{throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Creit ID mismatch vs URL.");}
		else if (creditRepo.existsById(credit.getId()))
		{creditRepo.save(credit);}
		else
		{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit not found for id "+id);}
	}
	
	@DeleteMapping ("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCredit(@PathVariable int id)
	{
		if (creditRepo.existsById(id))
		{creditRepo.deleteById(id);}
		else
		{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit not found for id "+id);}	
	}

}
