package com.music.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "playlist_song")
public class PlaylistSong {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "track")
  private Integer track;

  @ManyToOne
  @JsonIgnoreProperties("playlistSongs")
  private Playlist playlist;

  @ManyToOne
  @JsonIgnoreProperties("playlistSongs")
  private Song song;

}
