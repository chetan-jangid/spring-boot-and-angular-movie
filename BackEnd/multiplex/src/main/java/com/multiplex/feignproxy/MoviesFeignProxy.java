package com.multiplex.feignproxy;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.multiplex.dto.MovieDto;

@FeignClient(name = "movies")
@RibbonClient(name = "movies")
public interface MoviesFeignProxy {
	
	@GetMapping("movie/find/{id}")
	public ResponseEntity<MovieDto> findById(@PathVariable String id);

}
