package com.flo.demo.service.mapper;

import com.flo.demo.domain.Album;
import com.flo.demo.domain.Locale;
import com.flo.demo.service.dto.AlbumDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
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
