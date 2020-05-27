package com.alevel.library.model;

import com.alevel.library.model.additional.BaseEntity;
import com.alevel.library.model.additional.enums.Genre;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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
    private Boolean isAvailable;

    @Column(name = "popularity_index")
    private Integer popularityIndex;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    private List<ClientCardItem> clientCardItems;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book that = (Book) o;
        return getIsAvailable() == that.getIsAvailable() &&
                getPopularityIndex().equals(that.getPopularityIndex()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getAuthor(), that.getAuthor()) &&
                getGenre() == that.getGenre() &&
                Objects.equals(getReleaseYear(), that.getReleaseYear());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAuthor(), getGenre(), getReleaseYear(), getIsAvailable(), getPopularityIndex());
    }
}
