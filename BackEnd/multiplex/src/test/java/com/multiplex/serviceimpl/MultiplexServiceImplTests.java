package com.multiplex.serviceimpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.multiplex.document.Multiplex;
import com.multiplex.document.Screen;
import com.multiplex.dto.MultiplexDto;
import com.multiplex.dto.MultiplexResponseDto;
import com.multiplex.exception.MultiplexNotFoundException;
import com.multiplex.repository.MovieAllocationRepository;
import com.multiplex.repository.MultiplexRepository;
import com.multiplex.repository.ScreenRepository;
import com.multiplex.service.MultiplexServiceImpl;
import com.multiplex.utils.AppUtils;
import com.multiplex.utils.BuildUtilityObjects;

public class MultiplexServiceImplTests {
	
	@InjectMocks
	private MultiplexServiceImpl multiplexService;
	
	@Mock
	private MultiplexRepository multiplexRepository;
	
	@Mock
	private ScreenRepository screenRepository;
	
	@Mock
	private MovieAllocationRepository movieAllocationRepository;
	
	@Mock
	private AppUtils appUtils;
	
	private BuildUtilityObjects builder;
	
	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.initMocks(this);
	}
	
	public MultiplexServiceImplTests() {
		builder = new BuildUtilityObjects();
	}
	
	@Test
	@DisplayName("testFindAll")
	public void testFindAll() {
		// GIVEN
		List<Multiplex> multiplexes = builder.buildMultiplexList();
		Iterable<Multiplex> multiplexIterable = multiplexes;
		List<MultiplexDto> multiplexDtos = builder.buildMultiplexDtos();
		
		when(multiplexRepository.findAll()).thenReturn(multiplexIterable);
		when(appUtils.toList(multiplexIterable)).thenReturn(multiplexes);
		
		// WHEN
		MultiplexResponseDto dto = multiplexService.findAll();
		
		// THEN
		assertThat(dto).isNotNull();
		assertThat(dto.getMultiplexes()).containsExactlyInAnyOrderElementsOf(multiplexDtos);
		verify(multiplexRepository, times(1)).findAll();
		verify(appUtils, times(1)).toList(multiplexIterable);
	}
	
	@Test
	@DisplayName("testFindAllWhenMultiplexListIsNullOrEmpty")
	public void testFindAllWhenMultiplexListIsNullOrEmpty() {
		// GIVEN
		Iterable<Multiplex> multiplexIterable = new ArrayList<>();
		when(multiplexRepository.findAll()).thenReturn(Collections.emptyList());
		when(appUtils.toList(multiplexIterable)).thenReturn(Collections.emptyList());
		
		// THEN
		assertThat(multiplexService.findAll()).isNull();
		verify(multiplexRepository, times(1)).findAll();
		verify(appUtils, times(1)).toList(multiplexIterable);
	}
	
	@Test
	@DisplayName("testSave")
	public void testSave() {
		// GIVEN
		MultiplexDto multiplexDto = mock(MultiplexDto.class);
		Multiplex multiplex = mock(Multiplex.class);
		when(multiplexRepository.save(multiplex)).thenReturn(multiplex);
		
		// WHEN
		multiplexService.save(multiplexDto);
		
		// THEN
		assertThat(multiplex).isNotNull();
		assertThat(multiplex.getId()).isNull();
	}
	
	@Test
	@DisplayName("testUpdate")
	public void testUpdate() throws MultiplexNotFoundException {
		// GIVEN
		MultiplexDto multiplexDto = mock(MultiplexDto.class);
		Multiplex multiplex = mock(Multiplex.class);
		when(multiplexRepository.findById(multiplexDto.getId())).thenReturn(Optional.of(multiplex));
		
		// WHEN
		multiplexService.update(multiplexDto);
		
		// THEN
		verify(multiplexRepository, times(1)).save(multiplex);
		verify(screenRepository, times(1)).findByMultiplexId(multiplex.getId());
	}
	
	@Test
	@DisplayName("testUpdateWhenMultiplexIsNull")
	public void testUpdateWhenMultiplexIsNull() throws MultiplexNotFoundException {
		// GIVEN
		MultiplexDto multiplexDto = mock(MultiplexDto.class);
		when(multiplexRepository.findById(multiplexDto.getId())).thenReturn(Optional.empty());
		
		// WHEN THEN
		assertThrows(MultiplexNotFoundException.class, () -> multiplexService.update(multiplexDto));
	}
	
	@Test
	@DisplayName("testDelete")
	public void testDelete() throws MultiplexNotFoundException {
		// GIVEN
		String id = "1";
		Multiplex multiplex = mock(Multiplex.class);
		List<Screen> screens = builder.buildScreenList();
		
		when(multiplexRepository.findById(id)).thenReturn(Optional.of(multiplex));
		when(screenRepository.findByMultiplexId(id)).thenReturn(screens);
		Mockito.doNothing().when(multiplexRepository).delete(multiplex);
		
		// WHEN
		multiplexService.delete(id);
		
		// THEN
		verify(screenRepository, times(1)).findByMultiplexId(id);
		verify(movieAllocationRepository, times(screens.size())).findByScreenId("1");
	}
	
	@Test
	@DisplayName("testFindOne")
	public void testFindOne() throws MultiplexNotFoundException {
		// GIVEN
		String id = "1";
		Multiplex multiplex = mock(Multiplex.class);
		when(multiplexRepository.findById(id)).thenReturn(Optional.of(multiplex));
		
		// WHEN
		MultiplexDto multiplexDto = multiplexService.findOne(id);
		
		// THEN
		assertThat(multiplexDto).isNotNull();
//		assertThat(multiplexDto.getScreens()).isNotEmpty();
		verify(multiplexRepository, times(1)).findById(id);
	}
	
	@Test
	@DisplayName("testFindOneWhenMovieIsNull")
	public void testFindOneWhenMovieIsNull() throws MultiplexNotFoundException {
		// GIVEN
		String id = "1";
		when(multiplexRepository.findById(id)).thenReturn(Optional.empty());
		
		// THEN
		assertThrows(MultiplexNotFoundException.class, () -> multiplexService.findOne(id));
	}

}
