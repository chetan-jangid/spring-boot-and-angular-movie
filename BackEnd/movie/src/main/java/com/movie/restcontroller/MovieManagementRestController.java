package com.movie.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movie.dto.MovieDto;
import com.movie.dto.MoviesResponseDto;
import com.movie.exception.MovieNotFoundException;
import com.movie.service.MovieService;

@RestController
@RequestMapping("/movie")
public class MovieManagementRestController {
	
	@Autowired
	private MovieService movieService;
	
	@PostMapping("/save")
	public ResponseEntity<MovieDto> save(@RequestBody MovieDto movieDto) {
		movieService.save(movieDto);
		movieDto.setResponseMessage(movieDto.getName() + " added successfully!");
		return new ResponseEntity<MovieDto>(movieDto, HttpStatus.OK);
	}
	
	@PostMapping("/update")
	public ResponseEntity<MovieDto> update(@RequestBody MovieDto movieDto) throws MovieNotFoundException {
		movieService.update(movieDto);
		movieDto.setResponseMessage(movieDto.getName() + " updated successfully!");
		return new ResponseEntity<MovieDto>(movieDto, HttpStatus.OK);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<Void> delete(@RequestBody MovieDto movieDto) throws MovieNotFoundException {
		movieService.delete(movieDto.getId());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<MovieDto> findById(@PathVariable String id) throws MovieNotFoundException {
		MovieDto dto = movieService.findOne(id);
		return new ResponseEntity<MovieDto>(dto, HttpStatus.OK);
	}
	
	@GetMapping("/all-movies")
	public ResponseEntity<MoviesResponseDto> getAllMovies() {
		MoviesResponseDto movies = movieService.findAll();
		return new ResponseEntity<MoviesResponseDto>(movies, HttpStatus.OK);
	}

}
