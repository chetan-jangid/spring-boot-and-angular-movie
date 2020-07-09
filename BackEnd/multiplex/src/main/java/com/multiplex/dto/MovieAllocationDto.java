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
public class MovieAllocationDto {
	
	private String id;
	private String multiplexId;
	private String screenId;
	private String movieId;
	private String responseMessage;

}
