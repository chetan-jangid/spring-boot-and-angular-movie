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
public class MultiplexAllocationResponseDto {
	
	private String id;
	private String multiplexName;
	private String address;
	private String description;
	private List<MovieScreenDto> screens;
	private String responseMessage;

}
