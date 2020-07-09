package com.multiplex.service;

import com.multiplex.dto.MultiplexDto;
import com.multiplex.dto.MultiplexResponseDto;
import com.multiplex.exception.MultiplexNotFoundException;

public interface MultiplexService {
	
	MultiplexResponseDto findAll();
	void save(MultiplexDto movieDto);
	void update(MultiplexDto movieDto) throws MultiplexNotFoundException;
	void delete(String id) throws MultiplexNotFoundException;
	MultiplexDto findOne(String id) throws MultiplexNotFoundException;

}
