package com.alevel.library.service.impl;

import com.alevel.library.exceptions.BookNotFoundException;
import com.alevel.library.exceptions.BookStatusException;
import com.alevel.library.exceptions.ClientCardItemNotFoundException;
import com.alevel.library.model.Book;
import com.alevel.library.model.Client;
import com.alevel.library.model.ClientCardItem;
import com.alevel.library.model.additional.enums.Status;
import com.alevel.library.repository.BookRepository;
import com.alevel.library.service.BookService;
import com.alevel.library.service.ClientCardItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    @Autowired
    private ClientCardItemService clientCardItemService;

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
        log.warn("In existsById - book with id: {} not exists", id);
        return result;
    }

    @Override
    public Book save(Book book) {

        book.setStatus(Status.ACTIVE);
        Book savedBook = bookRepository.save(book);
        log.info("In register - book: {} successfully registered", savedBook.getName());

        return savedBook;
    }

    public Book update(Book book) {
        if (existsById(book.getId())) {
            Book existingBook = findById(book.getId());
            if (book.getName() != null) {
                existingBook.setName(book.getName());
            }
            if (book.getAuthor() != null) {
                existingBook.setAuthor(book.getAuthor());
            }
            if (book.getGenre() != null) {
                existingBook.setGenre(book.getGenre());
            }
            if (book.getReleaseYear() != null) {
                existingBook.setReleaseYear(book.getReleaseYear());
            }
            return bookRepository.save(existingBook);
        }
        throw new BookNotFoundException("Invalid id for book");
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
        log.info("In findById - book: {} found by id: {}", result.getName(), id);
        return result;
    }

    @Override
    public void delete(Integer id) {
        bookRepository.deleteById(id);
        log.info("In delete - user with id: {} successfully deleted", id);
    }

    @Override
    public void freeBook(int bookId) {
        if (bookRepository.existsById(bookId)) {
            Book book = findById(bookId);
            if (!book.getIsAvailable()) {
                book.setIsAvailable(true);
                bookRepository.save(book);
                ClientCardItem clientCardItem = getClientCardItem(bookId, book);
                ;
                if (clientCardItem != null) {
                    clientCardItem.setStatus(Status.INACTIVE);
                    clientCardItemService.updateStatus(clientCardItem);
                }
            } else {
                throw new BookStatusException("Book is already free");
            }
        } else {
            throw new BookNotFoundException("Invalid id for book");
        }
    }

    @Override
    public Client findClientByBookId(int bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Invalid id for book"));
        if (book.getIsAvailable()) {
            throw new BookStatusException("Book is free");
        }
        ClientCardItem clientCardItem = getClientCardItem(bookId, book);
        Client result = clientCardItem.getClientCard().getClient();
        return result;
    }

    @Override
    public Page<Book> search(Pageable pageable, Book book) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withMatcher("name", contains())
                .withMatcher("author", contains())
                .withMatcher("releaseYear", exact())
                .withMatcher("genre", contains())
                .withMatcher("isAvailable", exact());
        Example<Book> example = Example.of(book, matcher);
        Page<Book> result = bookRepository.findAll(example, pageable);
        return result;
    }

    private ClientCardItem getClientCardItem(int bookId, Book book) {
        return book.getClientCardItems().stream()
                .filter(item -> item.getStatus().equals(Status.RESERVED))
                .findFirst()
                .orElseThrow(() -> new ClientCardItemNotFoundException("ClientCardItems for book with id: " +
                        bookId + " not found"));
    }
}
