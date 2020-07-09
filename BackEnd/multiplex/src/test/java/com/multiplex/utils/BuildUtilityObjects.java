package com.multiplex.utils;

import java.util.Arrays;
import java.util.List;

import com.multiplex.document.MovieAllocation;
import com.multiplex.document.Multiplex;
import com.multiplex.document.Screen;
import com.multiplex.dto.MultiplexDto;

public class BuildUtilityObjects {
	
	public List<Multiplex> buildMultiplexList() {
		return Arrays.asList(buildMultiplex(), buildMultiplex());
	}
	
	private Multiplex buildMultiplex() {
		return new Multiplex("1", "multiplexName", "address", "description");
	}
	
	public List<MultiplexDto> buildMultiplexDtos() {
		return Arrays.asList(buildMultiplexDto(), buildMultiplexDto());
	}
	
	private MultiplexDto buildMultiplexDto() {
		return new MultiplexDto("1", "multiplexName", "address", "description", null, null);
	}
	
	public List<Screen> buildScreenList() {
		return Arrays.asList(buildScreen(), buildScreen());
	}
	
	private Screen buildScreen() {
		return new Screen("1", "name", "1");
	}
	
	public List<MovieAllocation> buildMovieAllocationList() {
		return Arrays.asList(buildMovieAllocation(), buildMovieAllocation());
	}
	
	private MovieAllocation buildMovieAllocation() {
		return new MovieAllocation("1", "1", "1", "1");
	}
	
}
