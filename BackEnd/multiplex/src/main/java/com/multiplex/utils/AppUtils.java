package com.multiplex.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AppUtils {
	
	public <T> List<T> toList(Iterable<T> iterable) {
		List<T> list = new ArrayList<>();
		if (iterable != null) {
			iterable.forEach(list::add);
		}
		return list;
	}

}
