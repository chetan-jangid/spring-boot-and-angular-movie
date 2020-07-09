package com.multiplex.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.multiplex.document.Multiplex;
import com.multiplex.dto.MultiplexDto;
import com.multiplex.dto.MultiplexSearchResponseDto;
import com.multiplex.repository.MultiplexRepository;

@Service
public class MultiplexSearchServiceImpl implements MultiplexSearchService {
	
	@Autowired
	private MultiplexRepository multiplexRepository;
	
	@Override
	@Async("threadPoolTaskExecutor")
	public CompletableFuture<MultiplexSearchResponseDto> searchByMultiplexName(MultiplexSearchResponseDto dto) {
		List<Multiplex> multiplexes = multiplexRepository
				.findByMultiplexNameContainingIgnoreCaseOrderByMultiplexName(dto.getMultiplexName());
		if (multiplexes != null && !multiplexes.isEmpty()) {
			List<MultiplexDto> dtos = multiplexes
					.stream()
					.map(m -> new MultiplexDto(m.getId(), m.getMultiplexName(), 
							m.getAddress(), m.getDescription(), null, null))
					.collect(Collectors.toList());
			dto.setMultiplexes(dtos);
		}
		return CompletableFuture.completedFuture(dto);
	}

}
