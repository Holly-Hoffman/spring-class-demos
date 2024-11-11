package com.bmdb.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bmdb.model.Movie;

public interface MovieRepo extends JpaRepository<Movie, Integer> {

	public abstract List<Movie> findByYearAndRating (int year, String rating);
	
	public abstract List<Movie> findByRatingAndYearGreaterThanEqual(String rating, int year);
	
	public abstract List<Movie> findByRatingNot(String rating);
}
