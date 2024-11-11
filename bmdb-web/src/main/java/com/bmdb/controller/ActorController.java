package com.bmdb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.bmdb.db.ActorRepo;
import com.bmdb.model.Actor;

@CrossOrigin
@RestController
@RequestMapping("/api/actors")
public class ActorController {
	
	@Autowired
	private ActorRepo actorRepo;
	
	@GetMapping("/")
	public List<Actor> getAllActors()
	{
		return actorRepo.findAll();
	}
	
	@GetMapping ("/{id}")
	public Optional<Actor> getActorById(@PathVariable int id)
	{
		Optional<Actor> a = actorRepo.findById(id);
		if (a.isPresent())
		{return a;}
		else
		{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor not found for id "+id);}
	}
	
	@PostMapping ("")
	public Actor addActor (@RequestBody Actor actor)
	{
		return actorRepo.save(actor);
	}
	
	@PutMapping ("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putMovie(@PathVariable int id, @RequestBody Actor actor)
	{
		if (id != actor.getId())
		{throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Actor ID mismatch vs URL.");}
		else if (actorRepo.existsById(actor.getId()))
		{actorRepo.save(actor);}
		else
		{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor not found for id "+id);}	
			
	}
	
	@DeleteMapping ("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteActor (@PathVariable int id)
	{
		if (actorRepo.existsById(id))
		{actorRepo.deleteById(id);}
		else
		{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor not found for id "+id);}
	}

}
