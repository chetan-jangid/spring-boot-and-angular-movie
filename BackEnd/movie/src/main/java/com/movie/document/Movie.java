package com.movie.document;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Movie {
	
	@Id
	private String id;
	private String name;
	private String description;
	private String language;
	private String category;
	private String producer;
	private String director;
	private LocalDate releaseDate;

}
