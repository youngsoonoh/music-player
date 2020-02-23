package com.music.demo.service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class UserDTO {

  @NotNull
  @Size(min = 1, max = 30)
  private String login;

  @Size(max=30)
  private String name;

  @Size(max=30)
  private String mobile;

  private Set<String> authorities;
}
