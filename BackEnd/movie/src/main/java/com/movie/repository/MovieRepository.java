package com.movie.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.movie.document.Movie;

public interface MovieRepository extends CrudRepository<Movie, String> {
	
	List<Movie> findByNameContainingIgnoreCaseOrderByNameAsc(String name);

}
