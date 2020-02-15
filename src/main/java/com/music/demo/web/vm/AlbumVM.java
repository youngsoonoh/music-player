package com.music.demo.web.vm;

import com.music.demo.service.dto.AlbumDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AlbumVM {
  private PageVM pages;
  private List<AlbumDTO> albums;
}
