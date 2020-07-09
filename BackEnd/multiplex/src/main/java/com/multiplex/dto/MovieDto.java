package com.multiplex.dto;

import java.time.LocalDate;

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
public class MovieDto {
	
	private String id;
	private String name;
	private String description;
	private String language;
	private String category;
	private String producer;
	private String director;
	private LocalDate releaseDate;
	private String responseMessage;

}
