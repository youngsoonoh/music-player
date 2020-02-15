package com.music.demo.service.dto;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Hidden
public class LocaleDTO {

    private String name;

    private Long albumId;
}
