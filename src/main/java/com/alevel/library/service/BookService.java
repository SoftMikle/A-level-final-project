package com.alevel.library.service;

import com.alevel.library.model.Book;
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
}
