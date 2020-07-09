package com.gateway.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gateway.exception.BadCredentialsException;
import com.gateway.dto.ErrorDto;

@ControllerAdvice
public class ApplicationExceptionHandler {
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorDto> movieNotFoundExceptionHandler(BadCredentialsException ex) {
		return new ResponseEntity<ErrorDto>(
				new ErrorDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
	}

}
