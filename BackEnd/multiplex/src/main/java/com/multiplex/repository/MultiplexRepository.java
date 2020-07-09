package com.multiplex.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.multiplex.document.Multiplex;

public interface MultiplexRepository extends CrudRepository<Multiplex, String> {
	
	List<Multiplex> findByMultiplexNameContainingIgnoreCaseOrderByMultiplexName(String multiplexName);

}
