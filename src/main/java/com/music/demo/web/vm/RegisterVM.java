package com.music.demo.web.vm;

import com.music.demo.service.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class RegisterVM extends UserDTO {
  @Size(min=6, max=120)
  private String password;
}
