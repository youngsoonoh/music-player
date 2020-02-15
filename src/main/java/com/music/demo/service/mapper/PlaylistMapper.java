package com.music.demo.service.mapper;

import com.music.demo.domain.Playlist;
import com.music.demo.service.dto.PlaylistDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlaylistMapper extends EntityMapper<Playlist, PlaylistDTO> {

}
