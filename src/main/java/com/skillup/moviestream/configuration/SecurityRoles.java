package com.skillup.moviestream.configuration;

import lombok.Getter;

public enum SecurityRoles {

  GET_ALL_MOVIES("Get All Movies"),
  GET_MOVIE_BY_ID("Get Movie By Id");

  @Getter
  private String roleName;

  SecurityRoles(String roleName) {
    this.roleName = roleName;
  }

  @Override
  public String toString() {
    return this.getRoleName();
  }
}
