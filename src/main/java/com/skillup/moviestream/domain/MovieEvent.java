package com.skillup.moviestream.domain;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Event class
 * @author PALLAB
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieEvent {

  private String movieId;

  private Date date;
}
