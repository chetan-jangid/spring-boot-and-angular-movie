package com.movie.serviceimpl;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.movie.document.Movie;
import com.movie.repository.MovieRepository;
import com.movie.service.MovieSearchServiceImpl;
import com.movie.utils.BuildUtilityObjects;

public class MovieSearchServiceImplTests {
	
	@InjectMocks
	private MovieSearchServiceImpl movieSearchService;
	
	@Mock
	private MovieRepository movieRepository;
	
	private BuildUtilityObjects builder;
	
	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.initMocks(this);
	}
	
	public MovieSearchServiceImplTests() {
		builder = new BuildUtilityObjects();
	}
	
	@Test
	@DisplayName("testSearchMovies")
	public void testSearchMovies() {
		// GIVEN
		String movieName = "abcd";
		List<Movie> movies = builder.buildMovieList();
		when(movieRepository.findByNameContainingIgnoreCaseOrderByNameAsc(movieName))
		.thenReturn(movies);
	}

}
