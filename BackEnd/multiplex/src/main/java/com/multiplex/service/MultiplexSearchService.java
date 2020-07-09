package com.multiplex.service;

import java.util.concurrent.CompletableFuture;

import com.multiplex.dto.MultiplexSearchResponseDto;

public interface MultiplexSearchService {
	
	CompletableFuture<MultiplexSearchResponseDto> searchByMultiplexName(MultiplexSearchResponseDto dto);

}
