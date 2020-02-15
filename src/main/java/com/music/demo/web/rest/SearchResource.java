package com.music.demo.web.rest;

import com.music.demo.service.AlbumService;
import com.music.demo.web.vm.SearchVM;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "검색 API", description = "검색 관련 API")
public class SearchResource {

    private final AlbumService albumService;

    public SearchResource(AlbumService albumService) {
        this.albumService = albumService;
    }

    @Operation(summary = "앨범 노래 검색", description = "앨범 노래 검색 API")
    @Parameter(in = ParameterIn.PATH, name = "locale", description = "국가 코드", required = true,
                content = @Content(schema = @Schema(type = "string", defaultValue = "en")))
    @Parameter(in = ParameterIn.QUERY, name = "term", description = "검색어", required = true,
               content = @Content(schema = @Schema(type = "string", defaultValue = "The Beatles")))
    @GetMapping("/search/{locale}/albums")
    public ResponseEntity<SearchVM> searchAlbums(@PathVariable String locale, @RequestParam String term) {
        return ResponseEntity.ok(albumService.search(locale, term));
    }
}
