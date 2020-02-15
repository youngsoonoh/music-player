package com.music.demo.repository.search;

import com.music.demo.domain.Album;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AlbumSearchRepository extends ElasticsearchRepository<Album, Long> {
}
