package com.multiplex.dto;

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
public class MovieScreenDto {
	
	private String screenId;
	private String screenName;
	private String multiplexId;
	private String movieId;
	private String movieName;
	private boolean hasMovieRunning;

}
