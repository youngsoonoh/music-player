package com.music.demo.service;

import com.music.demo.domain.Album;
import com.music.demo.domain.Song;
import com.music.demo.repository.AlbumRepository;
import com.music.demo.repository.search.AlbumSearchRepository;
import com.music.demo.service.dto.AlbumDTO;
import com.music.demo.service.mapper.AlbumMapper;
import com.music.demo.service.mapper.SongMapper;
import com.music.demo.web.util.PageUtils;
import com.music.demo.web.vm.AlbumVM;
import com.music.demo.web.vm.PageVM;
import com.music.demo.web.vm.SearchVM;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class AlbumService {

    private final AlbumRepository albumRepository;

    private final AlbumSearchRepository albumSearchRepository;

    private final AlbumMapper albumMapper;

    private final SongMapper songMapper;

    public AlbumService(AlbumRepository albumRepository,
                        AlbumSearchRepository albumSearchRepository, AlbumMapper albumMapper,
                        SongMapper songMapper) {
        this.albumRepository = albumRepository;
        this.albumSearchRepository = albumSearchRepository;
        this.albumMapper = albumMapper;
        this.songMapper = songMapper;
    }


    public AlbumDTO createAlbum(AlbumDTO albumDTO) {
        Album album = albumRepository.save(albumMapper.toEntity(albumDTO));
        return albumMapper.toDto(album);
    }

    public AlbumVM getAllAlbums(Pageable pageable) {
        Page<Album> albumPage = albumRepository.findAll(pageable);
        Page<AlbumDTO> albumDTOPage = albumPage.map(albumMapper::toDto);
        AlbumVM albumVM = new AlbumVM();
        albumVM.setAlbums(albumDTOPage.getContent());
        PageVM pageVM = PageUtils.generatePage(albumDTOPage, ServletUriComponentsBuilder.fromCurrentRequest());
        albumVM.setPages(pageVM);
        return albumVM;

    }

    public SearchVM search(String locale, String query) {

        SearchVM searchVM = new SearchVM();

        // 앨범 검색
        List<Album> searchAlbum = new ArrayList<>();
        QueryBuilder queryBuilder = QueryBuilders
                .boolQuery()
                .must(QueryBuilders.termsQuery("locales.name", Arrays.asList(locale, "all")))
                .must(QueryBuilders.matchPhraseQuery("albumTitle", query));
        albumSearchRepository.search(queryBuilder).forEach((searchAlbum::add));
        List<AlbumDTO> albums = albumMapper.toDto(searchAlbum);
        searchVM.setAlbums(albums);

        // 곡 검색
        List<Song> searchSong = new ArrayList<>();
        QueryBuilder songQueryBuilder = QueryBuilders
                .boolQuery()
                .must(QueryBuilders.termsQuery("locales.name", Arrays.asList(locale, "all")))
                .must(QueryBuilders.matchPhraseQuery("songs.title", query));
        albumSearchRepository.search(songQueryBuilder).forEach(album-> {
            album.getSongs().forEach(song-> {
                if(song.getTitle().contains(query)) {
                    searchSong.add(song);
                };
            });
        });
        searchVM.setSongs(songMapper.toDto(searchSong));

        return searchVM;
    }
}
