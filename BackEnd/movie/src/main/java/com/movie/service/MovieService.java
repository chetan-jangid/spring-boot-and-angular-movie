package com.movie.service;

import com.movie.dto.MovieDto;
import com.movie.dto.MoviesResponseDto;
import com.movie.exception.MovieNotFoundException;

public interface MovieService {
	
	MoviesResponseDto findAll();
	void save(MovieDto movieDto);
	void update(MovieDto movieDto) throws MovieNotFoundException;
	void delete(String id) throws MovieNotFoundException;
	MovieDto findOne(String id) throws MovieNotFoundException;

}
