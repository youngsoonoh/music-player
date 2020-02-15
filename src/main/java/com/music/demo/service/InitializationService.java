package com.music.demo.service;

import com.music.demo.repository.AlbumRepository;
import com.music.demo.repository.search.AlbumSearchRepository;
import com.music.demo.service.mapper.AlbumMapper;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InitializationService implements ApplicationListener<ContextRefreshedEvent> {

    private final AlbumMapper albumMapper;

    private final AlbumRepository albumRepository;

    private final AlbumSearchRepository albumSearchRepository;

    public InitializationService(AlbumMapper albumMapper,
                                 AlbumRepository albumRepository,
                                 AlbumSearchRepository albumSearchRepository) {
        this.albumMapper = albumMapper;
        this.albumRepository = albumRepository;
        this.albumSearchRepository = albumSearchRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
/*
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        TypeReference<List<AlbumDTO>> typeReference = new TypeReference<List<AlbumDTO>>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/album.json");

        List<AlbumDTO> albumDTOs = null;
        try {
            albumDTOs = mapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Album> albums = albumMapper.toEntity(albumDTOs);
        List<Song> songs = new ArrayList <>();
        albums.forEach(album -> {
            album.getSongs().forEach(song -> {
                song.setAlbum(album);
                songs.add(song);
            });
            album.getLocales().forEach(locale -> {
                locale.setAlbum(album);
            });
        });
        albumRepository.saveAll(albums);
        albumSearchRepository.saveAll(albums);*/
    }
}
