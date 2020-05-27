package com.alevel.library.repository;

import com.alevel.library.model.Book;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface BookRepository extends PagingAndSortingRepository<Book, Integer>, QueryByExampleExecutor<Book> {
    Page<Book> findAll(Example example, Pageable pageable);
}
