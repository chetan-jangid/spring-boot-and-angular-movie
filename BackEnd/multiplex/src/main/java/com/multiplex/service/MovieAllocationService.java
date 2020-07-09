package com.multiplex.service;

import com.multiplex.dto.MovieAllocationDeleteDto;
import com.multiplex.dto.MovieAllocationDto;
import com.multiplex.dto.MovieSearchResponseDto;
import com.multiplex.dto.MultiplexAllocationResponseDto;
import com.multiplex.exception.MovieAllocationException;
import com.multiplex.exception.MultiplexNotFoundException;

public interface MovieAllocationService {

	void allocate(MovieAllocationDto dto) throws MovieAllocationException;
	MultiplexAllocationResponseDto findByIdForAllocation(String id) throws MultiplexNotFoundException;
	void deleteAllocations(MovieAllocationDeleteDto dto);
	MovieSearchResponseDto mapMultiplexesToMovies(MovieSearchResponseDto dto);

}
