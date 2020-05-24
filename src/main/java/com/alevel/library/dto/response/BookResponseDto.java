package com.alevel.library.dto.response;

import com.alevel.library.model.Book;
import com.alevel.library.model.additional.enums.Genre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookResponseDto {

    private int id;
    private String name;
    private String author;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private Integer releaseYear;
    private boolean isAvailable;
    private int popularityIndex;

    public static BookResponseDto toBookResponseDto(Book book) {
        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setId(book.getId());
        bookResponseDto.setName(book.getName());
        bookResponseDto.setAuthor(book.getAuthor());
        bookResponseDto.setGenre(book.getGenre());
        bookResponseDto.setReleaseYear(book.getReleaseYear());
        bookResponseDto.setAvailable(book.isAvailable());
        bookResponseDto.setPopularityIndex(book.getPopularityIndex());

        return bookResponseDto;
    }

    public Book toBook() {
        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setReleaseYear(releaseYear);
        book.setAvailable(isAvailable);
        book.setPopularityIndex(popularityIndex);

        return book;
    }

}
