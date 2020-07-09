package com.movie.feignproxy;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.movie.dto.MovieAllocationDeleteDto;

@FeignClient(name = "multiplex")
@RibbonClient(name = "multiplex")
public interface MultiplexTheatreFeignProxy {
	
	@PostMapping("/movie-allocation/delete-movie-allocations")
	ResponseEntity<Void> deleteAllocations(@RequestBody MovieAllocationDeleteDto dto);
	
}
