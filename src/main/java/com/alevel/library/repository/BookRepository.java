package com.alevel.library.repository;

import com.alevel.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<Book, Integer> {
    Page<Book> findByName(Pageable pageable, String name);
}
