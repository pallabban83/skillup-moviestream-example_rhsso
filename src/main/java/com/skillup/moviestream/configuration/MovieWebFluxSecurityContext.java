package com.skillup.moviestream.configuration;

import static com.skillup.moviestream.configuration.SecurityRoles.GET_ALL_MOVIES;
import static com.skillup.moviestream.configuration.SecurityRoles.GET_MOVIE_BY_ID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.HttpStatusServerAccessDeniedHandler;
import reactor.core.publisher.Mono;


/**
 * @author PALLAB
 */
@EnableWebFluxSecurity
@Configuration
public class MovieWebFluxSecurityContext {

  @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
  private String resourceId;

  private static final String GET_ALL_MOVIES_PATH_MATCHER_PATTERN = "/movies/**";
  private static final String GET_MOVIE_BY_ID_PATH_MATCHER_PATTERN = "/movie/**";

  @Bean
  SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .authorizeExchange()
        .matchers(EndpointRequest.to("health"))
        .permitAll()
        .matchers(EndpointRequest.to("info"))
        .permitAll()
        .matchers(EndpointRequest.to("metrics"))
        .permitAll()
        .matchers(EndpointRequest.to("prometheus"))
        .permitAll()
        .pathMatchers(HttpMethod.GET, GET_ALL_MOVIES_PATH_MATCHER_PATTERN)
        .hasAnyAuthority(GET_ALL_MOVIES.getRoleName())
        .pathMatchers(HttpMethod.GET, GET_MOVIE_BY_ID_PATH_MATCHER_PATTERN)
        .hasAnyAuthority(GET_MOVIE_BY_ID.getRoleName())
        .and()
        .exceptionHandling()
        .accessDeniedHandler(new HttpStatusServerAccessDeniedHandler(HttpStatus.FORBIDDEN))
        .and()
        .oauth2ResourceServer()
        .jwt()
        .jwtAuthenticationConverter(questionnaireJwtAuthenticationConverter());
    return http.build();
  }

  @Bean
  Converter<Jwt, Mono<AbstractAuthenticationToken>> questionnaireJwtAuthenticationConverter() {
    MovieJwtAuthenticationConverter movieJwtAuthenticationConverter = new MovieJwtAuthenticationConverter(
        resourceId);
    return new ReactiveJwtAuthenticationConverterAdapter(movieJwtAuthenticationConverter);
  }
}
