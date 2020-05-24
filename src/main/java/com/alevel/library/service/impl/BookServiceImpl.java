package com.alevel.library.service.impl;

import com.alevel.library.exceptions.BookNotFoundException;
import com.alevel.library.exceptions.BookStatusException;
import com.alevel.library.model.Book;
import com.alevel.library.model.ClientCard;
import com.alevel.library.model.ClientCardItem;
import com.alevel.library.model.additional.enums.Status;
import com.alevel.library.repository.BookRepository;
import com.alevel.library.service.BookService;
import com.alevel.library.service.ClientCardItemService;
import com.alevel.library.service.ClientCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ClientCardService clientCardService;
    private final ClientCardItemService clientCardItemService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, ClientCardService clientCardService,
                           ClientCardItemService clientCardItemService) {
        this.bookRepository = bookRepository;
        this.clientCardService = clientCardService;
        this.clientCardItemService = clientCardItemService;
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
    public Page<Book> findAllBooksByClientId(Integer clientId, Pageable pageable) {
        ClientCard clientCard = clientCardService.findByClientId(clientId);
        List<ClientCardItem> clientCardItems = clientCardItemService.findByClientCardId(clientCard.getId());
        List<Book> books = clientCardItems.stream()
                .filter(clientCardItem -> clientCardItem.getStatus().equals(Status.RESERVED))
                .map(ClientCardItem::getBook)
                .collect(Collectors.toList());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), books.size());
        Page<Book> result = new PageImpl(books.subList(start, end), pageable, books.size());
        return result;
    }

    @Override
    public void freeBook(int bookId) {
        if (bookRepository.existsById(bookId)) {
            Book book = findById(bookId);
            if (!book.isAvailable()) {
                book.setAvailable(true);
                bookRepository.save(book);
                ClientCardItem clientCardItem = book.getClientCardItem();
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
}
