package com.skillup.moviestream.service;


import com.skillup.moviestream.domain.Movie;
import com.skillup.moviestream.domain.MovieEvent;
import java.time.Duration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author PALLAB
 */
public interface MovieService {

  /**
   * This method will generate a stream of movie events
   * @param movieId
   * @return
   */
  Flux<MovieEvent> events(String movieId, Long seconds);

  /**
   * Get movie by Id
   * @param movieId
   * @return
   */
  Mono<Movie> getMovieById(String movieId);

  /**
   * Return list of all movies
   * @return
   */
  Flux<Movie> getAllMovies();
}
