package com.movie.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.movie.document.Movie;
import com.movie.dto.MovieSearchResponseDto;
import com.movie.dto.MovieSearchWrapperDto;
import com.movie.repository.MovieRepository;

import reactor.core.publisher.Mono;

@Service
public class MovieSearchServiceImpl implements MovieSearchService {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Override
	@Async("threadPoolTaskExecutor")
	public CompletableFuture<MovieSearchResponseDto> searchMovies(MovieSearchResponseDto dto) {
		List<Movie> movies = movieRepository.findByNameContainingIgnoreCaseOrderByNameAsc(dto.getMovieName());
		dto.setData(new ArrayList<>());
		if (movies != null && !movies.isEmpty()) {
			MovieSearchResponseDto requestDto = new MovieSearchResponseDto();
			requestDto.setMovieName(dto.getMovieName());
			requestDto.setMovieIds(movies.stream().map(m -> m.getId()).collect(Collectors.toList()));
			call(requestDto, dto, movies);
		}
		return CompletableFuture.completedFuture(dto);
	}
	
	private void call(MovieSearchResponseDto requestDto, MovieSearchResponseDto dto, List<Movie> movies) {
		MovieSearchResponseDto response = webClientBuilder.build()
		.post()
		.uri("http://multiplex/movie-allocation/find-multiplexes-by-movie-ids")
		.contentType(MediaType.APPLICATION_JSON)
		.body(BodyInserters.fromPublisher(Mono.just(requestDto), 
				MovieSearchResponseDto.class))
		.retrieve()
		.onStatus(HttpStatus::is4xxClientError, clientResponse -> {
			return Mono.error(new RuntimeException(clientResponse.toString()));
		})
		.onStatus(HttpStatus::is5xxServerError, clientResponse -> {
			return Mono.error(new RuntimeException(clientResponse.toString()));
		})
		.bodyToMono(MovieSearchResponseDto.class)
		.block();
		handleResponse(response, dto, movies);
//		.subscribe(response -> handleResponse(response, dto, movies));
	}
	
	private void handleResponse(MovieSearchResponseDto response, 
			MovieSearchResponseDto dto, List<Movie> movies) {
		if (response.getData() != null && !response.getData().isEmpty()) {
			dto.setData(response.getData());
			dto.getData().forEach(m -> {
				Movie movie = movies.stream()
						.filter(mo -> mo.getId().equals(m.getMovieId()))
						.findAny()
						.get();
				m.setMovieName(movie.getName());
				m.setReleaseDate(movie.getReleaseDate());
			});
		} else {
			dto.setData(new ArrayList<>());
			movies.stream().forEach(m -> 
			dto.getData().add(new MovieSearchWrapperDto(m.getId(), m.getName(), m.getReleaseDate(), null)));
		}
	}

}
