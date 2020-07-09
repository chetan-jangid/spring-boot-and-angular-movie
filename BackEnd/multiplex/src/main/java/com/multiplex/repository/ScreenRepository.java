package com.multiplex.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.multiplex.document.Screen;

public interface ScreenRepository extends CrudRepository<Screen, String> {
	
	List<Screen> findByMultiplexId(String multiplexId);

}
