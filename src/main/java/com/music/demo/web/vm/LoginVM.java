package com.music.demo.web.vm;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginVM {
  @NotNull
  @Size(min = 1, max = 30)
  private String username;

  @NotNull
  @Size(min = 4, max = 150)
  private String password;

  private Boolean rememberMe;

}
