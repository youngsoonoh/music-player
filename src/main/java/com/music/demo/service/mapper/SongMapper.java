package com.music.demo.service.mapper;

import com.music.demo.domain.Song;
import com.music.demo.service.dto.SongDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SongMapper extends EntityMapper<Song, SongDTO> {

}
