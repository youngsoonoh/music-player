package com.flo.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "playlist")
@NoArgsConstructor
public class Playlist {

  @Builder
  public Playlist(String playlistTitle) {
    this.playlistTitle = playlistTitle;
  }

  @Builder
  public Playlist(String playlistTitle, Boolean publicPlaylist) {
    this.playlistTitle = playlistTitle;
    this.publicPlaylist = publicPlaylist;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "playlist_title")
  private String playlistTitle;

  @Column(name = "public_playlist")
  private Boolean publicPlaylist;

  @OneToMany(mappedBy = "playlist", cascade = CascadeType.REMOVE)
  @OrderBy("id")
  private Set<PlaylistSong> playlistSongs = new HashSet<>();

  @ManyToOne
  @JsonIgnoreProperties("playlists")
  private User user;
}
