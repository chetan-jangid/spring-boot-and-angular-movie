package com.movie.service;

import java.util.concurrent.CompletableFuture;

import com.movie.dto.MovieSearchResponseDto;

public interface MovieSearchService {
	
	CompletableFuture<MovieSearchResponseDto> searchMovies(MovieSearchResponseDto dto);

}
