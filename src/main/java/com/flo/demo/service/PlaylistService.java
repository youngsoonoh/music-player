package com.flo.demo.service;

import com.flo.demo.domain.Album;
import com.flo.demo.domain.Playlist;
import com.flo.demo.domain.PlaylistSong;
import com.flo.demo.domain.Song;
import com.flo.demo.repository.*;
import com.flo.demo.security.SecurityUtils;
import com.flo.demo.service.dto.PlaylistDTO;
import com.flo.demo.service.dto.PlaylistSongDTO;
import com.flo.demo.service.mapper.PlaylistMapper;
import com.flo.demo.web.vm.AddTracksVM;
import com.flo.demo.web.vm.PlaylistCreateVM;
import com.flo.demo.web.vm.PlaylistUpdateVM;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    private final UserRepository userRepository;

    private final PlaylistMapper playlistMapper;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final AlbumRepository albumRepository;

    private final PlaylistSongRepository playlistSongRepository;

    private final SongRepository songRepository;

    public PlaylistService(PlaylistRepository playlistRepository,
                           UserRepository userRepository,
                           PlaylistMapper playlistMapper,
                           AuthenticationManagerBuilder authenticationManagerBuilder,
                           AlbumRepository albumRepository,
                           PlaylistSongRepository playlistSongRepository,
                           SongRepository songRepository) {
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
        this.playlistMapper = playlistMapper;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.albumRepository = albumRepository;
        this.playlistSongRepository = playlistSongRepository;
        this.songRepository = songRepository;
    }

    public PlaylistDTO createPlaylist(PlaylistCreateVM playlistCreateVM) {
        Playlist playlist = Playlist.builder().playlistTitle(playlistCreateVM.getPlaylistTitle())
                .publicPlaylist(playlistCreateVM.getPublicPlaylist())
                .build();
        String login = "";
        login = getCurrentUser(login);
        playlist.setUser(userRepository.findOneByLogin(login).get());
        Playlist savePlaylist = playlistRepository.save(playlist);
        return playlistMapper.toDto(savePlaylist);
    }


    public List<PlaylistDTO> getPlaylist() {
        String login = "";
        login = getCurrentUser(login);
        return playlistRepository.findAllByUserLogin(login).stream().map(playlistMapper::toDto).collect(Collectors.toList());
    }

    public void deletePlaylist(Long playlistId) {
        playlistRepository.deleteById(playlistId);
    }

    public PlaylistDTO updatePlaylist(PlaylistUpdateVM playlistUpDateVM) {
        return Optional.of(playlistRepository.findById(playlistUpDateVM.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(x -> {
                    x.setPlaylistTitle(playlistUpDateVM.getPlaylistTitle());
                    x.setPublicPlaylist(playlistUpDateVM.getPublicPlaylist());
                    return x;
                })
                .map(playlistMapper::toDto).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /*
     *
     *
     *
     */

    public PlaylistDTO addTracks(Long playlistId, AddTracksVM addTracksVM) {
        return Optional.of(playlistRepository.findById(playlistId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(playlist -> {
                    if (addTracksVM.getAlbumIds().size() > 0) {
                        List<Album> alBums = albumRepository.findAllById(addTracksVM.getAlbumIds());
                        alBums.forEach(album -> {
                            album.getSongs().forEach(song -> {
                                if (playlist.getPlaylistSongs().stream().noneMatch(playlistSong -> playlistSong.getSong().getId().equals(song.getId()))) {
                                    PlaylistSong playlistSong = new PlaylistSong();
                                    playlistSong.setPlaylist(playlist);
                                    playlistSong.setSong(song);
                                    playlistSongRepository.save(playlistSong);
                                    playlist.getPlaylistSongs().add(playlistSong);
                                }
                            });
                        });
                    }

                    if (addTracksVM.getSongIds().size() > 0) {
                        List<Song> songs = songRepository.findAllById(addTracksVM.getSongIds());
                        songs.forEach(song -> {
                            if (playlist.getPlaylistSongs().stream().noneMatch(playlistSong -> playlistSong.getSong().getId().equals(song.getId()))) {
                                PlaylistSong playlistSong = new PlaylistSong();
                                playlistSong.setPlaylist(playlist);
                                playlistSong.setSong(song);
                                playlistSongRepository.save(playlistSong);
                                playlist.getPlaylistSongs().add(playlistSong);
                            }
                        });
                    }

                    return playlist;
                })
                .map(playlistMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private String getCurrentUser(String login) {
        Optional<String> currentUser = SecurityUtils.getCurrentUser();
        if (currentUser.isPresent()) {
            if (currentUser.get().equals("anonymousUser")) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken("user", "user");

                Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                login = SecurityUtils.getCurrentUser().get();
            } else {
                login = currentUser.get();
            }
        }
        return login;
    }

}
