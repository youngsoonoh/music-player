package com.flo.demo.service.mapper;

import com.flo.demo.domain.Song;
import com.flo.demo.service.dto.SongDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SongMapper extends EntityMapper<Song, SongDTO> {

}
