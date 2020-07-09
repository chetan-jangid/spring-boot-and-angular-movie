package com.multiplex.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.multiplex.document.MovieAllocation;

public interface MovieAllocationRepository extends CrudRepository<MovieAllocation, String> {
	
	List<MovieAllocation> findByScreenId(String screenId);
	List<MovieAllocation> findByMovieId(String movieId);
	List<MovieAllocation> findByMovieIdIn(List<String> movieIds);

}
