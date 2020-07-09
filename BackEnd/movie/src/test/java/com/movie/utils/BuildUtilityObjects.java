package com.movie.utils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.movie.document.Movie;
import com.movie.dto.MovieDto;

public class BuildUtilityObjects {
	
	private LocalDate localDate;
	
	public BuildUtilityObjects() {
		localDate = LocalDate.now();
	}
	
	public List<Movie> buildMovieList() {
		return Arrays.asList(buildMovie(), buildMovie());
	}
	
	public List<MovieDto> buildMovieDtos() {
		return Arrays.asList(buildMovieDto(), buildMovieDto());
	}
	
	private MovieDto buildMovieDto() {
		return new MovieDto("1", "name", "description", "language", "category", 
				"producer", "director", localDate, null);
	}
	
	private Movie buildMovie() {
		return new Movie("1", "name", "description", 
				"language", "category", "producer", "director", localDate);
	}

}
