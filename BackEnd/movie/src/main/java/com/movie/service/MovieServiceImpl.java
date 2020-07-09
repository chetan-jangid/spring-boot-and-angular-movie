package com.movie.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.document.Movie;
import com.movie.dto.MovieAllocationDeleteDto;
import com.movie.dto.MovieDto;
import com.movie.dto.MoviesResponseDto;
import com.movie.exception.MovieNotFoundException;
import com.movie.feignproxy.MultiplexTheatreFeignProxy;
import com.movie.repository.MovieRepository;
import com.movie.util.AppUtils;

@Service
public class MovieServiceImpl implements MovieService {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private AppUtils appUtils;
	
	@Autowired
	private MultiplexTheatreFeignProxy multiplexTheatreFeignProxy;
	
	@Override
	public MoviesResponseDto findAll() {
		MoviesResponseDto response = null;
		List<Movie> movieList = appUtils.toList(movieRepository.findAll());
		if (movieList != null && !movieList.isEmpty()) {
			List<MovieDto> list = new ArrayList<>();
			movieList.forEach(m -> {
				list.add(new MovieDto(m.getId(), m.getName(), m.getDescription(), m.getLanguage(), 
						m.getCategory(), m.getProducer(), m.getDirector(), m.getReleaseDate(), null));
			});
			response = new MoviesResponseDto(list);
		}
		return response;
	}

	@Override
	public void save(MovieDto movieDto) {
		Movie movie = new Movie(null, movieDto.getName(), movieDto.getDescription(), 
				movieDto.getLanguage(), movieDto.getCategory(), 
				movieDto.getProducer(), movieDto.getDirector(), movieDto.getReleaseDate());
		movieRepository.save(movie);
		movieDto.setId(movie.getId());
	}

	@Override
	public void update(MovieDto movieDto) throws MovieNotFoundException {
		Optional<Movie> optionalOfMovie = movieRepository.findById(movieDto.getId());
		if (!optionalOfMovie.isPresent()) {
			throw new MovieNotFoundException("Movie Not Found!");
		}
		Movie movie = optionalOfMovie.get();
		movie.setCategory(movieDto.getCategory());
		movie.setDescription(movieDto.getDescription());
		movie.setDirector(movieDto.getDirector());
		movie.setLanguage(movieDto.getLanguage());
		movie.setName(movieDto.getName());
		movie.setProducer(movieDto.getProducer());
		movie.setReleaseDate(movieDto.getReleaseDate());
		movieRepository.save(movie);
	}

	@Override
	public void delete(String id) throws MovieNotFoundException {
		Optional<Movie> optionalOfMovie = movieRepository.findById(id);
		if (!optionalOfMovie.isPresent()) {
			throw new MovieNotFoundException("Movie Not Found!");
		}
		Movie movie = optionalOfMovie.get();
		if (movie != null) {
			movieRepository.delete(movie);
			multiplexTheatreFeignProxy.deleteAllocations(new MovieAllocationDeleteDto(id));
		}
	}

	@Override
	public MovieDto findOne(String id) throws MovieNotFoundException {
		Optional<Movie> optionalOfMovie = movieRepository.findById(id);
		if (!optionalOfMovie.isPresent()) {
			throw new MovieNotFoundException("Movie Not Found!");
		}
		Movie movie = optionalOfMovie.get();
		return new MovieDto(movie.getId(), movie.getName(), movie.getDescription(), 
				movie.getLanguage(), movie.getCategory(), movie.getProducer(), 
				movie.getDirector(), movie.getReleaseDate(), null);
	}

}
