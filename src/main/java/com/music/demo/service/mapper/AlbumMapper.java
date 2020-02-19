package com.music.demo.service.mapper;

import com.music.demo.domain.Album;
import com.music.demo.domain.Locale;
import com.music.demo.service.dto.AlbumDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AlbumMapper extends EntityMapper<Album, AlbumDTO> {


    @Mapping(target = "locales", source = "locales")
    AlbumDTO toDto(Album entity);

//    @Mapping(target ="locales", ignore = true)
    Album toEntity(AlbumDTO dto);

    List<String> mapStringToList(List<Locale> locales);

     default String mapLocalesToString(Locale locale) {
        return locale.getName();
    }

    List<Locale> mapLocaleToList(List<String> locales);

     default Locale mapStringToLocale(String locale){
         Locale localeDomain = new Locale();
         localeDomain.setName(locale);
         return localeDomain;
     }


    default Album fromId(Long id) {
        if (id == null) {
            return null;
        }
        Album album = new Album();
        album.setId(id);
        return album;
    }
}
