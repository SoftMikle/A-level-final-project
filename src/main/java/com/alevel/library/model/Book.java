package com.alevel.library.model;

import com.alevel.library.model.additional.BaseEntity;
import com.alevel.library.model.additional.enums.Genre;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "books", schema = "library")
@Data
public class Book extends BaseEntity implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private Genre genre;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "is_available")
    private boolean isAvailable;

    @Column(name = "popularity_index")
    private int popularityIndex;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "pk.book")
    private ClientCardItem clientCardItem;

}
