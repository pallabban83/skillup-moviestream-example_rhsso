package com.skillup.moviestream.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
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
@EnableReactiveMethodSecurity(useAuthorizationManager = true)
public class MovieWebFluxSecurityContext {

  @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
  private String resourceId;
  private static final String MOVIE_STREAM_ENDPOINT = "/movie/*/events/*";

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
            .pathMatchers(HttpMethod.GET, MOVIE_STREAM_ENDPOINT)
            .permitAll()
            .anyExchange()
            .authenticated()
            .and()
            .exceptionHandling()
            .accessDeniedHandler(new HttpStatusServerAccessDeniedHandler(HttpStatus.FORBIDDEN))
            .and()
            .oauth2ResourceServer()
            .jwt()
            .jwtAuthenticationConverter(movieServiceJwtAuthenticationConverter());
    return http.build();
  }

  @Bean
  Converter<Jwt, Mono<AbstractAuthenticationToken>> movieServiceJwtAuthenticationConverter() {
    MovieJwtAuthenticationConverter movieJwtAuthenticationConverter = new MovieJwtAuthenticationConverter(
        resourceId);
    return new ReactiveJwtAuthenticationConverterAdapter(movieJwtAuthenticationConverter);
  }
}
