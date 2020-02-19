package com.music.demo.service.mapper;

import com.music.demo.domain.Locale;
import com.music.demo.service.dto.LocaleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {AlbumMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocalMapper  extends EntityMapper<Locale, LocaleDTO>{

    @Mapping(source="album.id", target="albumId")
    LocaleDTO toDto(Locale locale);

    @Mapping(target="album", source="albumId")
    Locale toEntity(LocaleDTO localeDTO);
}
