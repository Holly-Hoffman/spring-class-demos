package com.bmdb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.bmdb.db.MovieRepo;
import com.bmdb.model.Movie;

//findbyid vs existsbyid: find returns the movie, exists is a boolean
@CrossOrigin
@RestController
@RequestMapping("/api/movies")
public class MovieController {

	//instance variable for movie repo
	@Autowired
	private MovieRepo movieRepo; 
	
	@GetMapping("/")
	public List<Movie> getAllMovies()
	{
		return movieRepo.findAll();
	}
	
	//getById - "/api/movies/{id}"
	//returns movie
	@GetMapping ("/{id}")
	public Optional<Movie> getMovieById(@PathVariable int id) {
		//does it exist?  yes = movie, no = not found.
		Optional<Movie> m = movieRepo.findById(id);
		if (m.isPresent()) 
		{return m;}
		else
		{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found for id "+id);}
	}
	
	//get by year and rating
	@GetMapping ("/year-rating/{year}/{rating}")
	public List<Movie> findByYearAndRating(@PathVariable int year, @PathVariable String rating)
	{
		return movieRepo.findByYearAndRating(year, rating);
	}
	
	//get from year and rating
	@GetMapping ("/from-year-rating/{rating}/{year}")
	public List<Movie> findByRatingAndYearGreaterThan(@PathVariable String rating, @PathVariable int year)
	{
		return movieRepo.findByRatingAndYearGreaterThanEqual(rating, year);
	}
	
	//find movies that aren't a specific rating
	@GetMapping ("/not-rated/{rating}")
	public List<Movie> findByRatingNot(@PathVariable String rating)
	{
		return movieRepo.findByRatingNot(rating);
	}
	
	//post - "/api/movies/" (movie will be in the RequestBody)
	//returns movie
	@PostMapping ("")
	public Movie addMovie (@RequestBody Movie movie)
	{
		return movieRepo.save(movie);
	}
	
	//put - "/api/movies/{id}" (movie will be in the RequestBody)
	//returns NoContent()
	@PutMapping ("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putMovie(@PathVariable int id, @RequestBody Movie movie)
	{
		if (id != movie.getId())
			{throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Movie ID mismatch vs URL.");}
		else if (movieRepo.existsById(movie.getId()))
			{movieRepo.save(movie);}
		else
		{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found for id "+id);}
	}
	
	//delete - "/api/movies/{id}"
	//returns NoContent()
	@DeleteMapping ("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteMovie (@PathVariable int id)
	{
		if (movieRepo.existsById(id))
		{movieRepo.deleteById(id);}
		else
		{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found for id "+id);}
	}
}
