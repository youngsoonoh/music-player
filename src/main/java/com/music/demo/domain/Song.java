package com.music.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name="song")
@Document(indexName = "song")
public class Song {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Field(type= FieldType.Keyword)
  private Long id;


  @NotNull
  @Column(name = "title", nullable = false)
  private String title;

  @NotNull
  @Column(name = "track", nullable = false)
  private Integer track;

  @NotNull
  @Column(name = "length", nullable = false)
  private Integer length;

  @Column(columnDefinition="TEXT")
  private String lyrics;

  @ManyToOne
  @JsonIgnoreProperties("songs")
  @JsonIgnore
  private Album album;


}
