package com.flo.demo.repository.search;

import com.flo.demo.domain.Album;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AlbumSearchRepository extends ElasticsearchRepository<Album, Long> {
}
