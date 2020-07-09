package com.multiplex.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multiplex.document.MovieAllocation;
import com.multiplex.document.Multiplex;
import com.multiplex.document.Screen;
import com.multiplex.dto.MultiplexDto;
import com.multiplex.dto.MultiplexResponseDto;
import com.multiplex.dto.ScreenDto;
import com.multiplex.exception.MultiplexNotFoundException;
import com.multiplex.repository.MovieAllocationRepository;
import com.multiplex.repository.MultiplexRepository;
import com.multiplex.repository.ScreenRepository;
import com.multiplex.utils.AppUtils;

@Service
public class MultiplexServiceImpl implements MultiplexService {
	
	@Autowired
	private MultiplexRepository multiplexRepository;
	
	@Autowired
	private ScreenRepository screenRepository;
	
	@Autowired
	private MovieAllocationRepository movieAllocationRepository;
	
	@Autowired
	private AppUtils appUtils;
	
	@Override
	public MultiplexResponseDto findAll() {
		MultiplexResponseDto response = null;
		List<Multiplex> multiplexList = appUtils.toList(multiplexRepository.findAll());
		if (multiplexList != null && !multiplexList.isEmpty()) {
			List<MultiplexDto> list = new ArrayList<>();
			multiplexList.forEach(m -> {
				list.add(new MultiplexDto(m.getId(), m.getMultiplexName(), 
						m.getAddress(), m.getDescription(), null, null));
			});
			response = new MultiplexResponseDto(list);
		}
		return response;
	}

	@Override
	public void save(MultiplexDto multiplexDto) {
		Multiplex multiplex = new Multiplex(null, multiplexDto.getMultiplexName(), 
				multiplexDto.getAddress(), multiplexDto.getDescription());
		multiplexRepository.save(multiplex);
		for (ScreenDto screenDto : multiplexDto.getScreens()) {
			saveOrUpdateScreen(screenDto, multiplex.getId());
		}
		multiplexDto.setId(multiplex.getId());
	}
	
	private void saveOrUpdateScreen(ScreenDto screenDto, String multiplexId) {
		Screen screen = null;
		if (screenDto.getId() != null && screenDto.getId().length() > 0) {
			screen = screenRepository.findById(screenDto.getId()).get();
			screen.setName(screenDto.getName());
		} else {
			screen = new Screen(null, screenDto.getName(), multiplexId);
			screenDto.setId(screen.getId());
		}
		screenRepository.save(screen);
	}

	@Override
	public void update(MultiplexDto multiplexDto) throws MultiplexNotFoundException {
		Optional<Multiplex> multiplexOptional = multiplexRepository.findById(multiplexDto.getId());
		if (!multiplexOptional.isPresent()) {
			throw new MultiplexNotFoundException("Multiplex Not Found!");
		}
		
		Multiplex multiplex = multiplexOptional.get();
		multiplex.setAddress(multiplexDto.getAddress());
		multiplex.setDescription(multiplexDto.getDescription());
		multiplex.setMultiplexName(multiplexDto.getMultiplexName());
		multiplexRepository.save(multiplex);
		updateScreens(multiplexDto, multiplex.getId());
	}

	private void updateScreens(MultiplexDto multiplexDto, String multiplexId) {
		List<Screen> existingScreens = screenRepository.findByMultiplexId(multiplexId);
		List<String> existingScreenIds = existingScreens.stream()
				.map(s -> new String(s.getId()))
				.collect(Collectors.toList());
		List<String> updatedScreenIds = new ArrayList<>();
		
		for (ScreenDto screenDto : multiplexDto.getScreens()) {
			if (screenDto.getId() == null || screenDto.getId().isEmpty()) {
				screenRepository.save(new Screen(null, screenDto.getName(), multiplexId));
			} else if (existingScreenIds.contains(screenDto.getId())) {
				Screen screen = existingScreens.stream()
						.filter(s -> s.getId().equals(screenDto.getId()))
						.findFirst().get();
				screen.setName(screenDto.getName());
				screen.setMultiplexId(multiplexId);
				updatedScreenIds.add(screen.getId());
			}
		}
		
		for (Screen screen : existingScreens) {
			if (!updatedScreenIds.contains(screen.getId())) {
				screenRepository.delete(screen);
				deleteScreenFromAllocation(screen);
			}
		}
	}

	private void deleteScreenFromAllocation(Screen screen) {
		List<MovieAllocation> allocations = movieAllocationRepository.findByScreenId(screen.getId());
		if (allocations != null && !allocations.isEmpty()) {
			for (MovieAllocation allocation : allocations) {
				movieAllocationRepository.delete(allocation);
			}
		}
	}

	@Override
	public void delete(String id) throws MultiplexNotFoundException {
		Optional<Multiplex> multiplexOptional = multiplexRepository.findById(id);
		if (!multiplexOptional.isPresent()) {
			throw new MultiplexNotFoundException("Multiplex Not Found!");
		}
		
		Multiplex multiplex = multiplexOptional.get();
		multiplexRepository.delete(multiplex);
		List<Screen> screens = screenRepository.findByMultiplexId(id);
		for (Screen screen : screens) {
			screenRepository.delete(screen);
			deleteScreenFromAllocation(screen);
		}
	}

	@Override
	public MultiplexDto findOne(String id) throws MultiplexNotFoundException {
		Optional<Multiplex> multiplexOptional = multiplexRepository.findById(id);
		if (!multiplexOptional.isPresent()) {
			throw new MultiplexNotFoundException("Multiplex Not Found!");
		}
		
		Multiplex multiplex = multiplexOptional.get();
		MultiplexDto multiplexDto = new MultiplexDto();
		multiplexDto.setScreens(new ArrayList<>());
		if (multiplex != null) {
			multiplexDto.setAddress(multiplex.getAddress());
			multiplexDto.setDescription(multiplex.getDescription());
			multiplexDto.setId(multiplex.getId());
			multiplexDto.setMultiplexName(multiplex.getMultiplexName());
			
			List<Screen> screens = screenRepository.findByMultiplexId(id);
			screens.forEach(s -> multiplexDto.getScreens()
					.add(new ScreenDto(s.getId(), s.getName(), s.getMultiplexId())));
		}
		return multiplexDto;
	}

}
