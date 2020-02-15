package com.flo.demo.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
  elasticsearch 필요 없이 테스트하기 위한
  mock repository
 */
@Configuration
public class AlbumSearchRepositoryMockConfiguration {

    @MockBean
    private AlbumSearchRepository mockAlbumSearchRepository;

}
