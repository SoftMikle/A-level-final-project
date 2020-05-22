package com.alevel.library.service.impl;

import com.alevel.library.exceptions.ClientNotFoundException;
import com.alevel.library.model.Book;
import com.alevel.library.model.Client;
import com.alevel.library.model.additional.enums.Status;
import com.alevel.library.repository.BookRepository;
import com.alevel.library.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean existsById(Integer id) {
        boolean result = bookRepository.existsById(id);
        if (result) {
            log.info("In existsById - book with id: {} exists", id);
        }
        log.info("In existsById - book with id: {} not exists", id);
        return result;
    }

    @Override
    public Book save(Book book) {

        book.setStatus(Status.ACTIVE);
        Book savedBook = bookRepository.save(book);
        log.info("In register - book: {} successfully registered", savedBook);

        return savedBook;
    }

    public Book update(Book book) {
        if(existsById(book.getId())){
            Book existingBook = findById(book.getId());
            if(book.getName() != null){
                existingBook.setName(book.getName());
            }
            if(book.getAuthor() != null){
                existingBook.setAuthor(book.getAuthor());
            }
            if(book.getGenre() != null){
                existingBook.setGenre(book.getGenre());
            }
            if(book.getReleaseYear() != null){
                existingBook.setReleaseYear(book.getReleaseYear());
            }
            return bookRepository.save(existingBook);
        }
        throw new ClientNotFoundException("Invalid id for book");
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        Page<Book> result = bookRepository.findAll(pageable);
        log.info("In findAll -  {} books found", result.getContent().size());
        return result;
    }

    public Page<Book> findByName(Pageable pageable, String name) {
        Page<Book> result = bookRepository.findByName(pageable, name);
        log.info("In findByName - {} books with name: {} found", result.getContent().size(), name);
        return result;
    }

    @Override
    public Book findById(Integer id) {
        Book result = bookRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("In findById - no books found by id: {}", id);
            return null;
        }
        log.info("In findById - book: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void delete(Integer id) {
        bookRepository.deleteById(id);
        log.info("In delete - user with id: {} successfully deleted", id);
    }
}
