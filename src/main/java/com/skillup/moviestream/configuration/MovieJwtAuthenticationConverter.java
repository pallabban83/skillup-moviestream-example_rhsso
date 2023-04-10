package com.skillup.moviestream.configuration;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.CollectionUtils;

public class MovieJwtAuthenticationConverter implements
    Converter<Jwt, AbstractAuthenticationToken> {

  private static final String RESOURCE_ACCESS = "resource_access";

  private static final String ROLES = "roles";

  private final String resourceId;

  public MovieJwtAuthenticationConverter(String resourceId) {
    this.resourceId = resourceId;
  }

  @Override
  public AbstractAuthenticationToken convert(final Jwt source) {
    Collection<GrantedAuthority> authorities = extractResourceRoles(source, resourceId);
    return new JwtAuthenticationToken(source, authorities);
  }

  private static Collection<GrantedAuthority> extractResourceRoles(final Jwt jwt,
      final String resourceId) {
    List<String> resourceRoles = Collections.emptyList();
    Map<String, Object> resource = jwt.getClaimAsMap(RESOURCE_ACCESS);
    if (!CollectionUtils.isEmpty(resource) && resource.containsKey(resourceId)) {
      resourceRoles = ((Map<String, List<String>>) resource.get(resourceId)).get(ROLES);
    }
    return resourceRoles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
  }
}
