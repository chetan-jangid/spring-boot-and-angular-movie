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

import com.multiplex.dto.MultiplexDto;
import com.multiplex.dto.MultiplexResponseDto;
import com.multiplex.exception.MultiplexNotFoundException;
import com.multiplex.service.MultiplexService;

@RestController
@RequestMapping("/manage")
public class MultiplexRestController {
	
	@Autowired
	private MultiplexService multiplexService;
	
	@PostMapping("/save")
	public ResponseEntity<MultiplexDto> save(@RequestBody MultiplexDto movieDto) {
		multiplexService.save(movieDto);
		movieDto.setResponseMessage(movieDto.getMultiplexName() + " added successfully!");
		return new ResponseEntity<MultiplexDto>(movieDto, HttpStatus.OK);
	}
	
	@PostMapping("/update")
	public ResponseEntity<MultiplexDto> update(@RequestBody MultiplexDto movieDto)
			throws MultiplexNotFoundException {
		multiplexService.update(movieDto);
		movieDto.setResponseMessage(movieDto.getMultiplexName() + " updated successfully!");
		return new ResponseEntity<MultiplexDto>(movieDto, HttpStatus.OK);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<Void> delete(@RequestBody MultiplexDto movieDto)
			throws MultiplexNotFoundException {
		multiplexService.delete(movieDto.getId());
		movieDto.setResponseMessage(movieDto.getMultiplexName() + " deleted successfully!");
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<MultiplexDto> findById(@PathVariable String id)
			throws MultiplexNotFoundException {
		MultiplexDto dto = multiplexService.findOne(id);
		return new ResponseEntity<MultiplexDto>(dto, HttpStatus.OK);
	}
	
	@GetMapping("/all-multiplex")
	public ResponseEntity<MultiplexResponseDto> getAllMovies() {
		MultiplexResponseDto movies = multiplexService.findAll();
		return new ResponseEntity<MultiplexResponseDto>(movies, HttpStatus.OK);
	}
	
}
