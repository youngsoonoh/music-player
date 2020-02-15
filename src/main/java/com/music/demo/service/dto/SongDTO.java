package com.music.demo.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SongDTO {

  private Long id;

  private String title;

  private Integer track;

  private Integer length;

  private String lyrics;
}
