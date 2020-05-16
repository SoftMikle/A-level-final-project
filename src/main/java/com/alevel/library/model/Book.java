package com.alevel.library.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "books", schema = "library")
@Data
public class Book extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private Genre genre;

    @Column(name = "release_year")
    private int releaseYear;

    @Column(name = "is_available")
    private boolean isAvailable;

    @Column(name = "popularityIndex")
    private int popularityIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    private ClientCardItem clientCardItem;

    @ManyToOne(fetch = FetchType.LAZY)
    private ClientAccountInfo clientAccountInfo;

}
