package com.music.demo.web.rest;

import com.music.demo.service.PlaylistService;
import com.music.demo.service.dto.PlaylistDTO;
import com.music.demo.web.vm.AddTracksVM;
import com.music.demo.web.vm.PlaylistCreateVM;
import com.music.demo.web.vm.PlaylistUpdateVM;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Tag(name = "Playlist API", description = "Playlist 관련 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlaylistResource {

    private final PlaylistService playlistService;

    @Operation(summary = "Playlist 생성", description = "Playlist 생성 API ")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Playlist 노래 추가 object", required = true,
            content = @Content(schema = @Schema(implementation = PlaylistCreateVM.class)))
    @PostMapping("/playlists")
    public ResponseEntity<PlaylistDTO> createPlaylist(@Valid @RequestBody PlaylistCreateVM playlistCreateVM) throws URISyntaxException {
        PlaylistDTO playlist = playlistService.createPlaylist(playlistCreateVM);
        return ResponseEntity.created(new URI("/api/playlists/"+ playlist.getId())).body(playlist);
    }

    @Transactional(readOnly = true)
    @Operation(summary = "Playlist 목록", description = "Playlist 목록 API")
    @GetMapping("/playlists")
    public ResponseEntity<List<PlaylistDTO>> getPlaylist() {
        return ResponseEntity.ok(playlistService.getPlaylist());
    }

    @Operation(summary = "Playlist 수정", description = "Playlist 수정 API")
    @PutMapping("/playlists")
    public ResponseEntity<PlaylistDTO> updatePlaylist(@RequestBody PlaylistUpdateVM playlistUpdateVM) {
        return ResponseEntity.ok(playlistService.updatePlaylist(playlistUpdateVM));
    }


    @Operation(summary = "Playlist 삭제", description = "Playlist 삭제 API")
    @DeleteMapping("/playlists/{playlistId}")
    public ResponseEntity<Void> deletePlaylist(
            @Parameter(description = "Playlist 일련번호(id)", required = true)
            @PathVariable Long playlistId) {
        playlistService.deletePlaylist(playlistId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Playlist 노래 추가", description = "Playlist 노래 추가")
    @PostMapping("/playlists/{playlistId}/tracks")
    public ResponseEntity<PlaylistDTO> addTracks(
            @Parameter(description = "Playlist 일련번호(id)", required = true)
            @PathVariable Long playlistId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Playlist 노래 추가 object", required = true,
                    content = @Content(schema = @Schema(implementation = AddTracksVM.class)))
            @Valid @RequestBody AddTracksVM addTracksVM) {
        return ResponseEntity.ok(playlistService.addTracks(playlistId, addTracksVM));
    }

}
