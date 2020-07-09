package com.multiplex.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MovieSearchResponseDto {
	
	private String movieName;
	private List<String> movieIds;
	private List<MovieSearchWrapperDto> data;

}
