package com.alevel.library.repository;

import com.alevel.library.model.Book;
import com.alevel.library.model.ClientCardItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BookRepository extends PagingAndSortingRepository<Book, Integer> {
    Page<Book> findByName(Pageable pageable, String name);
}
