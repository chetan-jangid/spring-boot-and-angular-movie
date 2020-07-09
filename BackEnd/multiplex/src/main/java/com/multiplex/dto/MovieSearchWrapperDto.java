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
public class MovieSearchWrapperDto {
	
	private String movieId;
	private String movieName;
	private List<MultiplexDto> multiplexes;

}
