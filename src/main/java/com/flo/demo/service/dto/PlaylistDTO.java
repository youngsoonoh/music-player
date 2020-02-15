package com.flo.demo.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistDTO {

  private Long id;

  @JsonProperty("playlist_title")
  private String playlistTitle;

  @JsonProperty("public_playlist")
  private Boolean publicPlaylist;

  @JsonProperty("playlist_songs")
  private List<PlaylistSongDTO> playlistSongs;

}
