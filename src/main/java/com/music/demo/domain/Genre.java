package com.music.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name="genre")
public class Genre {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(name="name", nullable = false)
  private String name;

  @ManyToOne
  @JsonIgnoreProperties("genres")
  private Album album;

  @ManyToOne
  @JsonIgnoreProperties("genres")
  private Artist artist;


}
