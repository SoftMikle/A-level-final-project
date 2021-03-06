package com.alevel.library.dto.request;

import com.alevel.library.model.Book;
import com.alevel.library.model.additional.enums.Genre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookRequestDto {

    @Size(min = 1, max = 100)
    String name;

    @Size(min = 3, max = 100)
    String author;

    @Enumerated(EnumType.STRING)
    Genre genre;

    Integer releaseDate;

    public BookRequestDto() {
    }

    public Book toBook() {
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setReleaseYear(releaseDate);

        return book;
    }
}
