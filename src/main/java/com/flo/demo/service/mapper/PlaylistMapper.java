package com.flo.demo.service.mapper;

import com.flo.demo.domain.Playlist;
import com.flo.demo.service.dto.PlaylistDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlaylistMapper extends EntityMapper<Playlist, PlaylistDTO> {

}
