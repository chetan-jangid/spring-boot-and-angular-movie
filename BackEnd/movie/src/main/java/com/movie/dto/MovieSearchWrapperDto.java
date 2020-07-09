package com.movie.dto;

import java.time.LocalDate;
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
	private LocalDate releaseDate;
	private List<MultiplexDto> multiplexes;

}
