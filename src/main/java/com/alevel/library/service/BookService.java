package com.alevel.library.service;

import com.alevel.library.model.Book;
import com.alevel.library.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    boolean existsById(Integer id);

    Book save(Book book);

    Book update(Book book);

    Page<Book> findByName(Pageable pageable, String name);

    Book findById(Integer id);

    void delete(Integer id);

    Page<Book> findAll(Pageable pageable);

    void freeBook(int bookId);

    Client findClientByBookId(int bookId);

    Page<Book> search(Pageable pageable, Book book);
}
