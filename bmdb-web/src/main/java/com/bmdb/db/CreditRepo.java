package com.bmdb.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bmdb.model.Credit;

public interface CreditRepo extends JpaRepository<Credit, Integer> {

	public abstract List<Credit> findByMovieId(int movieId);
	
	public abstract List<Credit> findByActorId(int actorId);
	

}
