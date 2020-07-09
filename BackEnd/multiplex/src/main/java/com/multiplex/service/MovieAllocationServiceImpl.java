package com.multiplex.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multiplex.document.MovieAllocation;
import com.multiplex.document.Multiplex;
import com.multiplex.document.Screen;
import com.multiplex.dto.MovieAllocationDeleteDto;
import com.multiplex.dto.MovieAllocationDto;
import com.multiplex.dto.MovieDto;
import com.multiplex.dto.MovieScreenDto;
import com.multiplex.dto.MovieSearchResponseDto;
import com.multiplex.dto.MovieSearchWrapperDto;
import com.multiplex.dto.MultiplexAllocationResponseDto;
import com.multiplex.dto.MultiplexDto;
import com.multiplex.exception.MovieAllocationException;
import com.multiplex.exception.MultiplexNotFoundException;
import com.multiplex.feignproxy.MoviesFeignProxy;
import com.multiplex.repository.MovieAllocationRepository;
import com.multiplex.repository.MultiplexRepository;
import com.multiplex.repository.ScreenRepository;

@Service
public class MovieAllocationServiceImpl implements MovieAllocationService {
	
	@Autowired
	private MovieAllocationRepository movieAllocationRepository;
	
	@Autowired
	private MultiplexRepository multiplexRepository;
	
	@Autowired
	private ScreenRepository screenRepository;
	
	@Autowired
	private MoviesFeignProxy moviesFeignProxy;

	@Override
	public void allocate(MovieAllocationDto dto) throws MovieAllocationException {
		List<MovieAllocation> allocations = movieAllocationRepository.findByScreenId(dto.getScreenId());
		if (allocations != null && !allocations.isEmpty()) {
			throw new MovieAllocationException("Movie already allocated to this screen.");
		}
		MovieAllocation allocation = new MovieAllocation(null, dto.getMultiplexId(), 
				dto.getScreenId(), dto.getMovieId());
		movieAllocationRepository.save(allocation);
	}

	@Override
	public MultiplexAllocationResponseDto findByIdForAllocation(String id) throws MultiplexNotFoundException {
		Optional<Multiplex> multiplexOptional = multiplexRepository.findById(id);
		Multiplex multiplex = multiplexOptional.get();
		if (!multiplexOptional.isPresent()) {
			throw new MultiplexNotFoundException("Multiplex Not Found!");
		}
		
		List<Screen> screens = screenRepository.findByMultiplexId(id);
		MultiplexAllocationResponseDto dto = new MultiplexAllocationResponseDto(
				multiplex.getId(), multiplex.getMultiplexName(), multiplex.getAddress(), 
				multiplex.getDescription(), new ArrayList<>(), null);
		
		String movieName = null;
		String movieId = null;
		for (Screen screen : screens) {
			List<MovieAllocation> allocations = movieAllocationRepository.findByScreenId(screen.getId());
			boolean hasMovieRunning = false;
			movieName = null;
			movieId = null;
			if (allocations != null && !allocations.isEmpty()) {
				MovieDto movieDto = moviesFeignProxy.findById(allocations.get(0).getMovieId()).getBody();
				movieName = movieDto.getName();
				movieId  = movieDto.getId();
				hasMovieRunning = true;
			}
			dto.getScreens().add(new MovieScreenDto(screen.getId(), screen.getName(), 
					multiplex.getId(), movieId, movieName, hasMovieRunning));
		}
		return dto;
	}

	@Override
	public void deleteAllocations(MovieAllocationDeleteDto dto) {
		List<MovieAllocation> allocations = movieAllocationRepository.findByMovieId(dto.getMovieId());
		if (allocations != null && !allocations.isEmpty()) {
			for (MovieAllocation allocation : allocations) {
				movieAllocationRepository.delete(allocation);
			}
		}
	}

	@Override
	public MovieSearchResponseDto mapMultiplexesToMovies(MovieSearchResponseDto dto) {
		List<MovieAllocation> allocations = movieAllocationRepository.findByMovieIdIn(dto.getMovieIds());
		if (allocations != null && !allocations.isEmpty()) {
			Set<String> multiplexIds = allocations.stream()
					.map(a -> a.getMultiplexId())
					.collect(Collectors.toSet());
			List<Multiplex> multiplexes = StreamSupport
					.stream(multiplexRepository.findAllById(multiplexIds).spliterator(), false)
					.collect(Collectors.toList());
			
			dto.setData(new ArrayList<>());
			for (String movieId : dto.getMovieIds()) {
				List<String> ids = allocations.stream()
						.filter(a -> a.getMovieId().equals(movieId))
						.map(a -> a.getMultiplexId())
						.collect(Collectors.toList());
				List<MultiplexDto> movieMultiplexes = multiplexes.stream()
						.filter(m -> ids.contains(m.getId()))
						.map(m -> new MultiplexDto(m.getId(), m.getMultiplexName(), m.getAddress(), 
								m.getDescription(), null, null))
						.collect(Collectors.toList());
				dto.getData().add(new MovieSearchWrapperDto(movieId, null, movieMultiplexes));
			}
		}
		return dto;
	}

}
