package com.movie.restcontroller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movie.dto.MovieSearchResponseDto;
import com.movie.service.MovieSearchService;

@RestController
@RequestMapping("/movie-api")
public class MovieSearchRestController {
	
	@Autowired
	private MovieSearchService movieSearchService;
	
	@GetMapping("/find/{movieName}")
	public ResponseEntity<MovieSearchResponseDto> searchMovies(@PathVariable String movieName) 
			throws InterruptedException, ExecutionException {
		MovieSearchResponseDto dto = new MovieSearchResponseDto();
		dto.setMovieName(movieName);
		CompletableFuture<MovieSearchResponseDto> completableFuture = movieSearchService.searchMovies(dto);
		return new ResponseEntity<MovieSearchResponseDto>(completableFuture.get(), HttpStatus.OK);
	}
	
}
