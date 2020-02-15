package com.flo.demo.web.vm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistCreateVM {

    @JsonProperty("playlist_title")
    @Size(min = 1, max = 255)
    @NotBlank
    private String playlistTitle;

    @JsonProperty("public_playlist")
    private Boolean publicPlaylist;

}
