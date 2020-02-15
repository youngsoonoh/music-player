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


@Entity
@Table(name="locale")
@Getter
@Setter
@Document(indexName = "locale")
public class Locale {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Field(type= FieldType.Keyword)
  private Long id;

  @NotNull
  @Column(name="name", nullable = false)
  private String name;

  @ManyToOne
  @JsonIgnoreProperties("locales")
  @JsonIgnore
  private Album album;

}
