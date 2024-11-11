package com.hobby.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hobbies")
public class HobbyController {
	//first controller for bc in Maven.
	//make a list of hobbies and do crud using http actions
	
	private static List<String> hobbies = new ArrayList<>();
	
	@GetMapping("/")
	public List<String> getAll()
	{
		return hobbies;
	}
	
	@GetMapping("/{idx}")
	public String get(@PathVariable int idx)
	{
		return hobbies.get(idx);
	}
	
	@PostMapping("")
	public String add(String hobby)
	{
		if(hobbies.indexOf(hobby) >= 0) {return "This hobby already exists";}
		else {hobbies.add(hobby);
		return "Hobby has been added.";
		}
		}
	
	@PutMapping("/{idx}/{hobby}")
	public String update(@PathVariable int idx, @PathVariable String hobby)
	{
		hobbies.set(idx, hobby);
		return "Hobby has been updated.";
	}
	
	@DeleteMapping("/{idx}")
	public String delete (@PathVariable int idx)
	{hobbies.remove(idx);
	return "Hobby has been removed.";
	}
	

}
