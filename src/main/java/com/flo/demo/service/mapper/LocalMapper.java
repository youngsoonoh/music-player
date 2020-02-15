package com.flo.demo.service.mapper;

import com.flo.demo.domain.Locale;
import com.flo.demo.service.dto.LocaleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AlbumMapper.class})
public interface LocalMapper  extends EntityMapper<Locale, LocaleDTO>{

    @Mapping(source="album.id", target="albumId")
    LocaleDTO toDto(Locale locale);

    @Mapping(target="album", source="albumId")
    Locale toEntity(LocaleDTO localeDTO);
}
