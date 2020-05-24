package com.alevel.library.rest;

import com.alevel.library.dto.request.BookRequestDto;
import com.alevel.library.dto.response.BookResponseDto;
import com.alevel.library.dto.response.ClientResponseDto;
import com.alevel.library.model.Book;
import com.alevel.library.model.Client;
import com.alevel.library.model.additional.enums.Genre;
import com.alevel.library.service.BookService;
import com.alevel.library.service.ClientCardService;
import com.alevel.library.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;
    private final ClientService clientService;
    private final ClientCardService clientCardService;

    @Autowired
    public BooksController(BookService bookService, ClientService clientService, ClientCardService clientCardService) {
        this.bookService = bookService;
        this.clientService = clientService;
        this.clientCardService = clientCardService;
    }

    @GetMapping
    Page<Book> getAll(@PageableDefault(page = 0, size = 20)
                      @SortDefault.SortDefaults({
                              @SortDefault(sort = "name", direction = Sort.Direction.DESC),
                              @SortDefault(sort = "id", direction = Sort.Direction.ASC)
                      })
                              Pageable pageable) {
        Page<Book> result = bookService.findAll(pageable);
        return result;
    }

    @PostMapping
    HttpStatus postBook(@RequestBody BookRequestDto bookRequestDto) {
        Book book = bookRequestDto.toBook();
        bookService.save(book);

        return HttpStatus.CREATED;
    }

    @PatchMapping("/{bookId}")
    HttpStatus updateBook(@PathVariable int bookId, @RequestBody BookRequestDto bookRequestDto) {
        Book book = bookRequestDto.toBook();
        book.setId(bookId);

        bookService.update(book);

        return HttpStatus.OK;
    }

    @PatchMapping("/{bookId}/free")
    HttpStatus freeBook(@PathVariable int bookId) {

        bookService.freeBook(bookId);

        return HttpStatus.OK;
    }

    @GetMapping("/search")
    ResponseEntity<Page<BookResponseDto>> getAllByExample(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer releaseYear,
            @RequestParam(required = false) Genre genre,
            @RequestParam(required = false) Boolean isAvailable,
            Pageable pageable) {
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setReleaseYear(releaseYear);
        book.setGenre(genre);
        book.setIsAvailable(isAvailable);
        Page<Book> books = bookService.search(pageable, book);
        Page<BookResponseDto> result = books.map(BookResponseDto::toBookResponseDto);
        return ResponseEntity.ok(result);
    }

}
