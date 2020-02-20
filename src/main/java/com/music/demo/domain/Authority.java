package com.music.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="music_authority")
@Getter
@Setter
public class Authority {

  @NotNull
  @Size(max = 50)
  @Id
  @Column(length = 50)
  private String name;


}
