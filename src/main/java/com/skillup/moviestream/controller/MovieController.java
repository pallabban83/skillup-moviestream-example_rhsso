package com.skillup.moviestream.controller;


import com.skillup.moviestream.domain.Movie;
import com.skillup.moviestream.domain.MovieEvent;
import com.skillup.moviestream.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller for Movie Service
 * @author PALLAB
 */
@RestController
@RequiredArgsConstructor
public class MovieController {

  private final MovieService movieService;

  @GetMapping(value = "/movie/{id}/events/{seconds}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  Flux<MovieEvent> streamAllMovieEvents(@PathVariable String id, @PathVariable Long seconds) {
    return movieService.events(id, seconds);
  }

  @GetMapping(value = "/movie/{id}")
  @PreAuthorize("hasAuthority('Get Movie By Id')")
  Mono<Movie> getMovieById(@PathVariable String id) {
    return movieService.getMovieById(id);
  }

  @GetMapping(value = "/movies")
  @PreAuthorize("hasAuthority('Get All Movies')")
  Flux<Movie> getAllMovies() {
    return movieService.getAllMovies();
  }

}
