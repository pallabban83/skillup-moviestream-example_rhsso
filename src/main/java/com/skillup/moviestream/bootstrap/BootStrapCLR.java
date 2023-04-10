package com.skillup.moviestream.bootstrap;


import com.skillup.moviestream.domain.Movie;
import com.skillup.moviestream.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * @author PALLAB
 */
@Component
@RequiredArgsConstructor
public class BootStrapCLR implements CommandLineRunner {

  private final MovieRepository movieRepository;

  @Override
  public void run(String... args) throws Exception {

    //clear the old data
    movieRepository.deleteAll().thenMany(
      Flux.just("Silence of the lambdas", "Aeon Flux", "Lord of Fluxes", "Enter the Mono<Void>",
          "Back to the future", "Meet the Fluxes", "The Fluxxinator")
          .map(title -> new Movie(title))
          .flatMap(movieRepository::save))
          .subscribe(null, null, () -> {
            movieRepository.findAll().subscribe(System.out::println);
          });

  }
}
