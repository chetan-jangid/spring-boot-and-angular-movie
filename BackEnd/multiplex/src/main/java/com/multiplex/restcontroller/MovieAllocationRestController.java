package com.multiplex.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multiplex.dto.MovieAllocationDeleteDto;
import com.multiplex.dto.MovieAllocationDto;
import com.multiplex.dto.MovieSearchResponseDto;
import com.multiplex.dto.MultiplexAllocationResponseDto;
import com.multiplex.exception.MovieAllocationException;
import com.multiplex.exception.MultiplexNotFoundException;
import com.multiplex.service.MovieAllocationService;

@RestController
@RequestMapping("/movie-allocation")
public class MovieAllocationRestController {
	
	@Autowired
	private MovieAllocationService movieAllocationService;
	
	@GetMapping("/find-allocation/{id}")
	public ResponseEntity<MultiplexAllocationResponseDto> findByIdForAllocation(@PathVariable String id)
			throws MultiplexNotFoundException {
		MultiplexAllocationResponseDto dto = movieAllocationService.findByIdForAllocation(id);
		return new ResponseEntity<MultiplexAllocationResponseDto>(dto, HttpStatus.OK);
	}
	
	@PostMapping("/allocate")
	public ResponseEntity<MovieAllocationDto> allocate(
			@RequestBody MovieAllocationDto dto) throws MovieAllocationException {
		movieAllocationService.allocate(dto);
		dto.setResponseMessage("Movie alloted successfully!");
		return new ResponseEntity<MovieAllocationDto>(dto, HttpStatus.OK);
	}
	
	@PostMapping("/delete-movie-allocations")
	public ResponseEntity<Void> deleteAllocations(@RequestBody MovieAllocationDeleteDto dto) {
		movieAllocationService.deleteAllocations(dto);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PostMapping("/find-multiplexes-by-movie-ids")
	public ResponseEntity<MovieSearchResponseDto> mapMultiplexesToMovies(
			@RequestBody MovieSearchResponseDto dto) {
		movieAllocationService.mapMultiplexesToMovies(dto);
		return new ResponseEntity<MovieSearchResponseDto>(dto, HttpStatus.OK);
	}

}
