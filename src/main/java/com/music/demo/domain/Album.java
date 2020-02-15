package com.music.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "album")
@Document(indexName = "album")
public class Album implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
//    @Field(type = FieldType.Keyword)
    private String albumTitle;


    @Column(name = "release_date")
    private Instant releaseDate;

    @Column(name = "label")
    private String label;


    @OneToMany(mappedBy = "album", cascade = CascadeType.PERSIST)
    @OrderBy("id")
    private Set<Song> songs = new HashSet<>();

    @OneToMany(mappedBy = "album", cascade = CascadeType.PERSIST)
    @OrderBy("name")
    private Set<Locale> locales = new HashSet<>();

    @OneToMany(mappedBy = "album")
    @OrderBy("id")
    private Set<Genre> genres = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("albums")
    private Artist artist;

    public Album albumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
        return this;
    }

    public Album releaseDate(Instant releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public Album label(String label) {
        this.label = label;
        return this;
    }

}
