package com.skillup.moviestream.service;


import com.skillup.moviestream.domain.Movie;
import com.skillup.moviestream.domain.MovieEvent;
import com.skillup.moviestream.repositories.MovieRepository;
import java.time.Duration;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Movie Service implementation
 * @author PALLAB
 */
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

  private final MovieRepository movieRepository;

  @Override
  public Flux<MovieEvent> events(String movieId, Long seconds) {
    return Flux.<MovieEvent>generate(movieEventSynchronousSink -> movieEventSynchronousSink.next(new MovieEvent(movieId, new Date())))
                .delayElements(Duration.ofSeconds(seconds));
  }

  @Override
  public Mono<Movie> getMovieById(String movieId) {
    return movieRepository.findById(movieId);
  }

  @Override
  public Flux<Movie> getAllMovies() {
    return movieRepository.findAll();
  }
}
