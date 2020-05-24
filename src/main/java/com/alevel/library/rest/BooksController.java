package com.alevel.library.rest;

import com.alevel.library.dto.request.BookRequestDto;
import com.alevel.library.model.Book;
import com.alevel.library.service.BookService;
import com.alevel.library.service.ClientCardService;
import com.alevel.library.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

}
