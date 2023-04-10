package com.skillup.moviestream.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Mongo DB document class
 * @author PALLAB
 */
@Document
@Data
@RequiredArgsConstructor
public class Movie {

  private String id;

  @NonNull
  private String title;

}
