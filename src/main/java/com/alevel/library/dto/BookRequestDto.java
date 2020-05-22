package com.alevel.library.dto;

import com.alevel.library.model.Book;
import com.alevel.library.model.Client;
import com.alevel.library.model.additional.enums.Genre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;
import java.util.Date;

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

    public BookRequestDto(String name, String author, Genre genre, Integer releaseDate) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.releaseDate = releaseDate;
    }

    public Book toBook() {
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setReleaseYear(releaseDate);

        return book;
    }

    public static BookRequestDto toBookRequestDto(Book book) {
        BookRequestDto bookRequestDto = new BookRequestDto();
        bookRequestDto.setName(book.getName());
        bookRequestDto.setAuthor(book.getAuthor());
        bookRequestDto.setGenre(book.getGenre());
        bookRequestDto.setReleaseDate(book.getReleaseYear());

        return bookRequestDto;
    }
}
