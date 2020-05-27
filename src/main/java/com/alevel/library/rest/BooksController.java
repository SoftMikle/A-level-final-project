package com.alevel.library.rest;

import com.alevel.library.dto.request.BookRequestDto;
import com.alevel.library.dto.response.BookResponseDto;
import com.alevel.library.model.Book;
import com.alevel.library.model.additional.enums.Genre;
import com.alevel.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    Page<BookResponseDto> getAll(@PageableDefault(page = 0, size = 20)
                      @SortDefault.SortDefaults({
                              @SortDefault(sort = "name", direction = Sort.Direction.DESC),
                              @SortDefault(sort = "id", direction = Sort.Direction.ASC)
                      })
                              Pageable pageable) {
        Page<Book> books = bookService.findAll(pageable);
        Page<BookResponseDto> result = books.map(BookResponseDto::toDto);
        return result;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    HttpStatus postBook(@RequestBody BookRequestDto bookRequestDto) {
        Book book = bookRequestDto.toBook();
        bookService.save(book);

        return HttpStatus.CREATED;
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("/{bookId}")
    HttpStatus updateBook(@PathVariable int bookId, @RequestBody BookRequestDto bookRequestDto) {
        Book book = bookRequestDto.toBook();
        book.setId(bookId);
        bookService.update(book);

        return HttpStatus.OK;
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{bookId}")
    HttpStatus deleteBook(@PathVariable int bookId) {
        bookService.delete(bookId);

        return HttpStatus.OK;
    }

    @Secured("ROLE_ADMIN")
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
        Page<BookResponseDto> result = books.map(BookResponseDto::toDto);
        return ResponseEntity.ok(result);
    }

}
