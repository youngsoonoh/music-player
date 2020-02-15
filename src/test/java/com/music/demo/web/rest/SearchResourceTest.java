package com.music.demo.web.rest;

import com.music.demo.DemoApplication;
import com.music.demo.domain.Album;
import com.music.demo.repository.AlbumRepository;
import com.music.demo.repository.search.AlbumSearchRepository;
import com.music.demo.service.AlbumService;
import com.music.demo.service.mapper.AlbumMapper;
import com.music.demo.web.vm.SearchVM;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = DemoApplication.class)
class SearchResourceTest {
  private static final String DEFAULT_TITLE = "애국가 전집";

  @Autowired
  private AlbumSearchRepository mockAlbumSearchRepository;

  @Autowired
  private AlbumRepository albumRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private AlbumMapper albumMapper;

  @Autowired
  private AlbumService albumService;

  private MockMvc albumMockMvc;

  private Album album;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
    final SearchResource searchResource = new SearchResource(albumService);
    this.albumMockMvc = MockMvcBuilders.standaloneSetup(searchResource)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
            .build();
  }

  public static Album createEntity(EntityManager em) {
    return new Album()
            .albumTitle(DEFAULT_TITLE);
  }

  @BeforeEach
  public void initTest() {
    album = createEntity(em);
  }


  @Test
  @Transactional
  public void searchAlbum() throws Exception {

    albumRepository.saveAndFlush(album);

    SearchVM searchVM = new SearchVM();
    QueryBuilder queryBuilder = QueryBuilders
            .boolQuery()
            .must(QueryBuilders.termsQuery("locales.name", Arrays.asList("en", "all")))
            .must(QueryBuilders.matchPhraseQuery("albumTitle", DEFAULT_TITLE));
    searchVM.setAlbums(albumMapper.toDto(Collections.singletonList(album)));

    when(mockAlbumSearchRepository.search(queryBuilder))
            .thenReturn(Collections.singletonList(album));


    albumMockMvc.perform(get("/api/search/{locale}/albums?term="+DEFAULT_TITLE,"en"))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.albums[*].id").value(hasItem(album.getId().intValue())))
            .andExpect(jsonPath("$.albums[*].album_title").value(hasItem(DEFAULT_TITLE)));
  }
}
