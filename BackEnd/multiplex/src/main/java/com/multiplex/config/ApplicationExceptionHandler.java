package com.multiplex.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.multiplex.dto.ErrorDto;
import com.multiplex.exception.MovieAllocationException;
import com.multiplex.exception.MultiplexNotFoundException;

@ControllerAdvice
public class ApplicationExceptionHandler {
	
	@ExceptionHandler(MultiplexNotFoundException.class)
	public ResponseEntity<ErrorDto> multiplexNotFoundExceptionHandler(MultiplexNotFoundException ex) {
		return new ResponseEntity<ErrorDto>(
				new ErrorDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MovieAllocationException.class)
	public ResponseEntity<ErrorDto> movieAllocationExceptionHandler(MovieAllocationException ex) {
		return new ResponseEntity<ErrorDto>(
				new ErrorDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
	}

}
