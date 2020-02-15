package com.flo.demo.web.rest;

import com.flo.demo.service.AlbumService;
import com.flo.demo.service.dto.AlbumDTO;
import com.flo.demo.web.vm.AlbumVM;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 앨범 관리를 위한 REST controller
 */

@RestController
@RequestMapping("/api")
@Tag(name = "Album API", description = "Album 관련 API")
public class AlbumResource {

    private final AlbumService albumService;

    public AlbumResource(AlbumService albumService) {
        this.albumService = albumService;
    }

    @Operation(summary = "Album 목록", description = "Album 목록 API")
    @Parameter(in = ParameterIn.QUERY,
               description = "Zero-based page 번호",
               name = "page",
               content = @Content(schema = @Schema(type = "integer", defaultValue = "0")))
    @Parameter(in = ParameterIn.QUERY,
               description = "페이지당 사이즈",
               name = "size",
               content = @Content(schema = @Schema(type = "integer", defaultValue = "5")))
    @Parameter(in = ParameterIn.QUERY,
               description = "정렬 기본은 asc 테스트시에는 add item 클릭 후 " +
               "생성된 input 창에 <b>id,desc</b> 입력해 주세요",
               name = "sort", content = @Content(array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "id,desc"))))
    @GetMapping("/albums")
    public ResponseEntity<AlbumVM> getAllAlbums(@Parameter(hidden = true) Pageable pageable) {
        return ResponseEntity.ok(albumService.getAllAlbums(pageable));
    }


    /**
     * {@code POST /albums} : 새로운 앨범 생성
     *
     * @param albumDTO albumDTO 생성
     * @return ResponseEntity 에 status  {@code 201 (Created)} 새로 생성된  album 전송
     * @throws URISyntaxException 만약 URI 주소가 잘못되면
     */
    @PostMapping("/albums")
    @Hidden
    public ResponseEntity<AlbumDTO> createAlbum(@RequestBody AlbumDTO albumDTO) throws URISyntaxException {
        AlbumDTO albumDTOResult = albumService.createAlbum(albumDTO);
        return ResponseEntity.created(new URI("/api/albums" + albumDTOResult.getId()))
                .body(albumDTOResult);
    }
}
