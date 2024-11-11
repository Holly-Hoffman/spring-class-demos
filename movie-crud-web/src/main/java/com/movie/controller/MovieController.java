package com.movie.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.movie.model.Movie;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
private static List<Movie> movies = new ArrayList<>();
	
	@GetMapping("/")
	public List<Movie> getAll()
	{
		return movies;
	}
	
	@GetMapping("/{id}")
	public String getMovie (@PathVariable int id)
	{
		for (Movie movie : movies) 
		{if (movie.getId() == id) 
		{return movie.toString();}
		}
		 return "Movie not found.";
	}
	
	@PostMapping("/")
	public String add(@RequestBody Movie movie)
	{
		for (Movie mov : movies)
		{if (mov.getId() == movie.getId())
			{return "Movie already exists";}
		else {
			int maxId = 0;
			for (Movie m: movies)		
		{maxId = Math.max(maxId, m.getId());}
			maxId +=1;
			movie.setId(maxId);
			movies.add(movie);
		}
		}
		return movie.toString();
	}

	
	@PutMapping("/{id}")
	public String update(@PathVariable int id, @RequestBody Movie movie)
	{
		for (Movie mov : movies)
		{if (mov.getId() == id)
		{
		movies.set(id, movie);
		return "Movie has been updated.";}
		}
		return "Movie does not exist.";
	}
	
	@DeleteMapping("/{id}")
	public String delete (@PathVariable int id)
	{
		for (Movie movie : movies)
		{if (movie.getId() == id)
			{movies.remove(id);
			return "Movie has been removed";}
			}
		return "Movie does not exist";
		}
		

}
