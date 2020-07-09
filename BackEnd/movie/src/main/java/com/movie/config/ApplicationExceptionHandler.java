package com.movie.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.movie.dto.ErrorDto;
import com.movie.exception.MovieNotFoundException;

@ControllerAdvice
public class ApplicationExceptionHandler {
	
	@ExceptionHandler(MovieNotFoundException.class)
	public ResponseEntity<ErrorDto> movieNotFoundExceptionHandler(MovieNotFoundException ex) {
		return new ResponseEntity<ErrorDto>(
				new ErrorDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
	}

}
