package com.multiplex.restcontroller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multiplex.dto.MultiplexSearchResponseDto;
import com.multiplex.service.MultiplexSearchService;

@RestController
@RequestMapping("/search")
public class MultiplexSearchRestController {
	
	@Autowired
	private MultiplexSearchService multiplexSearchService;
	
	@GetMapping("/by-name/{multiplexName}")
	public ResponseEntity<MultiplexSearchResponseDto> searchByName(@PathVariable String multiplexName) 
			throws InterruptedException, ExecutionException {
		MultiplexSearchResponseDto dto = new MultiplexSearchResponseDto();
		dto.setMultiplexName(multiplexName);
		CompletableFuture<MultiplexSearchResponseDto> futureObject = multiplexSearchService
				.searchByMultiplexName(dto);
		return new ResponseEntity<MultiplexSearchResponseDto>(futureObject.get(), HttpStatus.OK);
	}

}
