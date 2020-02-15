package com.music.demo.web.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.music.demo.DemoApplication;
import com.music.demo.domain.Album;
import com.music.demo.repository.AlbumRepository;
import com.music.demo.repository.search.AlbumSearchRepository;
import com.music.demo.service.AlbumService;
import com.music.demo.service.dto.AlbumDTO;
import com.music.demo.service.mapper.AlbumMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.time.Instant;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = DemoApplication.class)
class AlbumResourceTest {
  private static final String DEFAULT_TITLE = "애국가 전집";

  private static final Instant DEFAULT_RELEASE_DATE = Instant.now();

  private static final String DEFAULT_LABEL = "EMI";

  @Autowired
  private AlbumService albumService;

  @Autowired
  private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

  @Autowired
  private AlbumRepository albumRepository;

  @Autowired
  private AlbumSearchRepository albumSearchRepository;

  @Autowired
  private AlbumMapper albumMapper;

  @Autowired
  private EntityManager em;

  private MockMvc albumMockMvc;

  private Album album;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
    final AlbumResource albumResource = new AlbumResource(albumService);
    this.albumMockMvc = MockMvcBuilders.standaloneSetup(albumResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
            .build();
  }


  public static Album createEntity(EntityManager em) {
    Album album = new Album()
            .albumTitle(DEFAULT_TITLE)
            .releaseDate(DEFAULT_RELEASE_DATE)
            .label(DEFAULT_LABEL);
    return album;
  }

  @BeforeEach
  public void initTest() {
    album = createEntity(em);
  }


  @Test
  @Transactional
  public void createAlbum() throws Exception {
    int dbSizeBeforeCreate = albumRepository.findAll().size();
    AlbumDTO albumDTO = albumMapper.toDto(album);
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    mapper.registerModule(new JavaTimeModule());
    albumMockMvc.perform(post("/api/albums")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsBytes(albumDTO)))
            .andExpect(status().isCreated());

    List<Album> albumList = albumRepository.findAll();
    assertThat(albumList).hasSize(dbSizeBeforeCreate + 1);
  }

  @Test
  @Transactional
  public void getAllAlbums() throws Exception {
    albumRepository.saveAndFlush(album);

    albumMockMvc.perform(get("/api/albums?sort=id,desc&size=10"))
            .andExpect(status().isOk())
     //       .andExpect(jsonPath("$.pages.total").value(hasItem(album.getId().intValue())))
            .andExpect(jsonPath("$.albums[*].id").value(hasItem(album.getId().intValue())))
            .andExpect(jsonPath("$.albums[*].album_title").value(hasItem(DEFAULT_TITLE)));
  }
}
