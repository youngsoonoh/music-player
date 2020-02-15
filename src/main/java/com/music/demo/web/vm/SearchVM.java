package com.music.demo.web.vm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.music.demo.service.dto.AlbumDTO;
import com.music.demo.service.dto.SongDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.OrderBy;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchVM {
  @OrderBy("id")
  private List<AlbumDTO> albums;

  @OrderBy("id")
  private List<SongDTO> songs;
}
