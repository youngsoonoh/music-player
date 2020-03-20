package com.music.demo.repository;

import com.music.demo.DemoApplication;
import com.music.demo.domain.Playlist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DemoApplication.class)
class PlaylistRepositoryTest {

    @Autowired
    PlaylistRepository playlistRepository;

    @Test
    @Transactional
    public void testPlayList() throws Exception {

        Playlist playlist = new Playlist();
        playlist.setPlaylistTitle("test");
        Playlist savePlaylist = playlistRepository.save(playlist);

        Optional<Playlist> findPlaylist = playlistRepository.findById(savePlaylist.getId());

        assertThat(findPlaylist.get().getId()).isEqualTo(savePlaylist.getId());
    }

}
