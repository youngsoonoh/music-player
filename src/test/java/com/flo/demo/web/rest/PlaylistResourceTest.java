package com.flo.demo.web.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.flo.demo.DemoApplication;
import com.flo.demo.domain.Playlist;
import com.flo.demo.domain.User;
import com.flo.demo.repository.PlaylistRepository;
import com.flo.demo.repository.UserRepository;
import com.flo.demo.service.PlaylistService;
import com.flo.demo.service.mapper.PlaylistMapper;
import com.flo.demo.web.rest.erros.ExceptionHandler;
import com.flo.demo.web.vm.AddTracksVM;
import com.flo.demo.web.vm.PlaylistCreateVM;
import com.flo.demo.web.vm.PlaylistUpdateVM;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = DemoApplication.class)
@Sql(scripts = {"classpath:sql/album.sql", "classpath:sql/song.sql", "classpath:sql/locale.sql"})
public class PlaylistResourceTest {

    private static final String DEFAULT_TITLE = "자기 전에 듣는 곡";
    private static final String UPDATE_TITLE = "비가 오는 날 곡";
    private static final Boolean DEFAULT_PUBLIC_PLAYLIST = false;

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private ExceptionHandler exceptionHandler;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaylistMapper playlistMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private EntityManager em;

    private MockMvc playlistMockMvc;

    private Playlist playlist;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlaylistResource playlistResource = new PlaylistResource(playlistService);
        this.playlistMockMvc = MockMvcBuilders.standaloneSetup(playlistResource)
                .setControllerAdvice(exceptionHandler)
                .setMessageConverters(mappingJackson2HttpMessageConverter)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .build();
    }

    @BeforeEach
    public void initTest() {
        playlist = createEntity(em);
    }

    private Playlist createEntity(EntityManager em) {
        return Playlist.builder()
                .playlistTitle(DEFAULT_TITLE)
                .build();
    }

    @Test
    @Transactional
    @WithMockUser("user")
    public void createPlayList() throws Exception {
        User user = new User();
        user.setLogin("user");
        user.setPassword(RandomStringUtils.random(60));
        user.setCreatedBy("user");

        userRepository.saveAndFlush(user);

        int dbSizeBeforeCreate = playlistRepository.findAll().size();
        PlaylistCreateVM playlistCreateVM = new PlaylistCreateVM();
        playlistCreateVM.setPlaylistTitle(playlist.getPlaylistTitle());
        playlistCreateVM.setPublicPlaylist(playlist.getPublicPlaylist());
        ObjectMapper mapper = getObjectMapper();
        playlistMockMvc.perform(post("/api/playlists")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsBytes(playlistCreateVM)))
                .andExpect(status().isCreated());

        List<Playlist> playlists = playlistRepository.findAll();
        assertThat(playlists).hasSize(dbSizeBeforeCreate + 1);

    }

    @Test
    @Transactional
    public void deletePlaylist() throws Exception {
        playlistRepository.saveAndFlush(playlist);
        int dbSizeBeforeDelete = playlistRepository.findAll().size();
        playlistMockMvc.perform(delete("/api/playlists/{playlistId}", playlist.getId()))
                .andExpect(status().isNoContent());
        List<Playlist> playlists = playlistRepository.findAll();
        assertThat(playlists).hasSize(dbSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void updatePlaylist() throws Exception {
        playlistRepository.saveAndFlush(playlist);

        ObjectMapper mapper = getObjectMapper();
        PlaylistUpdateVM playlistUpdateVM = new PlaylistUpdateVM();
        playlistUpdateVM.setId(playlist.getId());
        playlistUpdateVM.setPlaylistTitle(UPDATE_TITLE);
        playlistMockMvc.perform(put("/api/playlists")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsBytes(playlistUpdateVM)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playlist_title").value(UPDATE_TITLE));
    }

    @Test
    @Transactional
    public void addTracks() throws Exception {
        playlistRepository.saveAndFlush(playlist);
        ObjectMapper mapper = getObjectMapper();

        AddTracksVM addTracksVM = new AddTracksVM();
        addTracksVM.setAlbumIds(Collections.singletonList(2L));
        addTracksVM.setSongIds(Arrays.asList(1L, 2L, 3L));
        playlistMockMvc.perform(post("/api/playlists/{playlistId}/tracks", playlist.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsBytes(addTracksVM)))
                .andDo(print())
                .andExpect(status().isOk())
        .andExpect(jsonPath("$.playlist_songs[*].song.id").value(hasItem(1)))
        .andExpect(jsonPath("$.playlist_songs[*].song.title").value(hasItem("With The Beatles (1963.11) song-3")));
    }

    @Test
    @Transactional
    public void addTracksNotValid() throws Exception {
        playlistRepository.saveAndFlush(playlist);
        ObjectMapper mapper = getObjectMapper();
        AddTracksVM addTracksVM = new AddTracksVM();
        for(int i=1; i<=101; i++) {
            addTracksVM.getAlbumIds().add((long) i);
        }
        playlistMockMvc.perform(post("/api/playlists/{playlistId}/tracks", playlist.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsBytes(addTracksVM)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.message").value("error.validation"));
    }


    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
