package com.music.demo.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlbumDTO {
  private Long id;

  @JsonProperty("album_title")
  private String albumTitle;

  @JsonProperty("release_date")
  private Instant releaseDate;

  private String label;

  private List<SongDTO> songs;

  private Set<String> locales;
}
