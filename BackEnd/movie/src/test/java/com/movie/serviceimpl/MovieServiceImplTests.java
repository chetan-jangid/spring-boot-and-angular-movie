package com.movie.serviceimpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.movie.document.Movie;
import com.movie.dto.MovieAllocationDeleteDto;
import com.movie.dto.MovieDto;
import com.movie.dto.MoviesResponseDto;
import com.movie.exception.MovieNotFoundException;
import com.movie.feignproxy.MultiplexTheatreFeignProxy;
import com.movie.repository.MovieRepository;
import com.movie.service.MovieServiceImpl;
import com.movie.util.AppUtils;
import com.movie.utils.BuildUtilityObjects;

public class MovieServiceImplTests {
	
	@InjectMocks
	private MovieServiceImpl movieService;
	
	@Mock
	private AppUtils appUtils;
	
	@Mock
	private MovieRepository movieRepository;
	
	@Mock
	private MultiplexTheatreFeignProxy multiplexTheatreFeignProxy;
	
	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.initMocks(this);
	}
	
	BuildUtilityObjects builder;
	
	public MovieServiceImplTests() {
		builder = new BuildUtilityObjects();
	}
	
	@Test
	@DisplayName("testFindAll")
	public void testFindAll() {
		// GIVEN
		List<Movie> movieList = builder.buildMovieList();
		Iterable<Movie> movieIterable = movieList;
		List<MovieDto> movieDtos = builder.buildMovieDtos();
		
		when(movieRepository.findAll()).thenReturn(movieIterable);
		when(appUtils.toList(movieIterable)).thenReturn(movieList);
		
		// WHEN
		MoviesResponseDto moviesResponseDto = movieService.findAll();
		
		// THEN
		assertThat(moviesResponseDto).isNotNull();
		assertThat(moviesResponseDto.getMovies()).containsExactlyInAnyOrderElementsOf(movieDtos);
		verify(movieRepository, times(1)).findAll();
		verify(appUtils, times(1)).toList(movieIterable);
	}
	
	@Test
	@DisplayName("testFindAllWhenMovieListIsNullOrEmpty")
	public void testFindAllWhenMovieListIsNullOrEmpty() {
		// GIVEN
		Iterable<Movie> movieIterable = new ArrayList<>();
		when(movieRepository.findAll()).thenReturn(Collections.emptyList());
		when(appUtils.toList(movieIterable)).thenReturn(Collections.emptyList());
		
		// THEN
		assertThat(movieService.findAll()).isNull();
		verify(movieRepository, times(1)).findAll();
		verify(appUtils, times(1)).toList(movieIterable);
	}
	
	@Test
	@DisplayName("testSave")
	public void testSave() {
		// GIVEN
		MovieDto movieDto = mock(MovieDto.class);
		Movie movie = mock(Movie.class);
		when(movieRepository.save(movie)).thenReturn(movie);
		
		// WHEN
		movieService.save(movieDto);
		
		// THEN
		assertThat(movie).isNotNull();
		assertThat(movie.getId()).isNull();
	}
	
	@Test
	@DisplayName("testUpdate")
	public void testUpdate() throws MovieNotFoundException {
		// GIVEN
		MovieDto movieDto = mock(MovieDto.class);
		Movie movie = mock(Movie.class);
		when(movieRepository.findById(movieDto.getId())).thenReturn(Optional.of(movie));
		
		// WHEN
		movieService.update(movieDto);
		
		// THEN
		verify(movieRepository, times(1)).save(movie);
	}
	
	@Test
	@DisplayName("testUpdateWhenMovieIsNull")
	public void testUpdateWhenMovieIsNull() throws MovieNotFoundException {
		// GIVEN
		MovieDto movieDto = mock(MovieDto.class);
		when(movieRepository.findById(movieDto.getId())).thenReturn(Optional.empty());
		
		// WHEN THEN
		assertThrows(MovieNotFoundException.class, () -> movieService.update(movieDto));
	}
	
	@Test
	@DisplayName("testDelete")
	public void testDelete() throws MovieNotFoundException {
		// GIVEN
		String id = "1";
		Movie movie = mock(Movie.class);
		MovieAllocationDeleteDto movieAllocationDeleteDto = mock(MovieAllocationDeleteDto.class);
		
		when(movieRepository.findById(id)).thenReturn(Optional.of(movie));
		Mockito.doNothing().when(movieRepository).delete(movie);
		when(multiplexTheatreFeignProxy.deleteAllocations(movieAllocationDeleteDto))
		.thenReturn(new ResponseEntity<Void>(HttpStatus.OK));
		
		// WHEN
		movieService.delete(id);
	}
	
	@Test
	@DisplayName("testFindOne")
	public void testFindOne() throws MovieNotFoundException {
		// GIVEN
		String id = "1";
		Movie movie = mock(Movie.class);
		when(movieRepository.findById(id)).thenReturn(Optional.of(movie));
		
		// WHEN
		MovieDto movieDto = movieService.findOne(id);
		
		// THEN
		assertThat(movieDto).isNotNull();
		verify(movieRepository, times(1)).findById(id);
	}
	
	@Test
	@DisplayName("testFindOneWhenMovieIsNull")
	public void testFindOneWhenMovieIsNull() throws MovieNotFoundException {
		// GIVEN
		String id = "1";
		when(movieRepository.findById(id)).thenReturn(Optional.empty());
		
		// THEN
		assertThrows(MovieNotFoundException.class, () -> movieService.findOne(id));
	}

}
