package com.alevel.library.service.impl;

import com.alevel.library.exceptions.BookNotFoundException;
import com.alevel.library.exceptions.BookStatusException;
import com.alevel.library.model.Book;
import com.alevel.library.model.Client;
import com.alevel.library.model.ClientCard;
import com.alevel.library.model.ClientCardItem;
import com.alevel.library.model.additional.enums.Genre;
import com.alevel.library.model.additional.enums.Status;
import com.alevel.library.repository.BookRepository;
import com.alevel.library.service.ClientCardItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
class BookServiceImplTest {

  Book book;
  Page<Book> page;
  Pageable pageable;
  ClientCardItem cardItem;
  ClientCard clientCard;
  Client client;

  @Mock
  private BookRepository bookRepository;
  @Mock
  private ClientCardItemService clientCardItemService;
  @InjectMocks
  private BookServiceImpl service; // = new BookServiceImpl(bookRepository);

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    book = new Book();
    book.setName("best");
    book.setAuthor("Author");
    book.setPopularityIndex(99);
    book.setGenre(Genre.FANTASY);
    book.setId(111);
    book.setReleaseYear(1999);
    book.setIsAvailable(false);
    List<Book> books = new ArrayList<>();
    books.add(book);
    page = new PageImpl(books);
    List<ClientCardItem> cardItems = new ArrayList<>();
    pageable = PageRequest.of(0, 10);
    cardItem = new ClientCardItem();
    cardItem.setStatus(Status.RESERVED);
    cardItem.setBook(book);
    clientCard = new ClientCard();
    cardItems.add(cardItem);
    Set<ClientCardItem> items = new HashSet<>();
    items.add(cardItems.get(0));
    clientCard.setClientCardItems(items);
    cardItem.setClientCard(clientCard);
    client = new Client();
    client.setClientCard(clientCard);
    client.setFirstName("Tester");
    clientCard.setClient(client);
    book.setClientCardItems(cardItems);
  }

  @Test
  void save() {
    doReturn(book).when(bookRepository).save(nullable(Book.class));
    ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
    service.save(book);
    verify(bookRepository).save(captor.capture());
    Book value = captor.getValue();
    assertEquals("best", value.getName());
  }

  @Test
  void update() {
    doReturn(book).when(bookRepository).save(nullable(Book.class));
    doReturn(Optional.of(book)).when(bookRepository).findById(nullable(Integer.class));
    doReturn(true).when(bookRepository).existsById(nullable(Integer.class));
    ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
    service.update(book);
    verify(bookRepository).save(captor.capture());
    Book value = captor.getValue();
    assertEquals("best", value.getName());
    assertEquals(Genre.FANTASY, value.getGenre());

    doReturn(false).when(bookRepository).existsById(nullable(Integer.class));
    assertThrows(BookNotFoundException.class, () -> service.update(book));
    assertThrows(BookNotFoundException.class, () -> service.findById(book.getId()));
  }

  @Test
  void findAll() {
    doReturn(page).when(bookRepository).findAll(pageable);
    ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
    service.findAll(pageable);
    verify(bookRepository).findAll(captor.capture());
    Pageable value = captor.getValue();
    assertEquals(pageable, value);

    doReturn(Page.empty(pageable)).when(bookRepository).findAll(pageable);
    assertThrows(BookNotFoundException.class, () -> service.findAll(pageable));
  }

  @Test
  void delete() {
    doReturn(true).when(bookRepository).existsById(nullable(Integer.class));
    ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
    service.delete(book.getId());
    verify(bookRepository).existsById(captor.capture());
    assertEquals(book.getId(), captor.getValue());

    doReturn(false).when(bookRepository).existsById(nullable(Integer.class));
    assertThrows(BookNotFoundException.class, () -> service.delete(book.getId()));
  }

  @Test
  void freeBook() {
    doReturn(book).when(bookRepository).save(nullable(Book.class));
    doReturn(Optional.of(book)).when(bookRepository).findById(nullable(Integer.class));
    doReturn(true).when(bookRepository).existsById(nullable(Integer.class));
    doReturn(cardItem).when(clientCardItemService).save(nullable(ClientCardItem.class));
    ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
    assertFalse(book.getIsAvailable());
    service.freeBook(book.getId());
    verify(bookRepository).save(captor.capture());
    Book value = captor.getValue();
    assertEquals("best", value.getName());
    assertEquals(Genre.FANTASY, value.getGenre());
    assertEquals(Status.INACTIVE, value.getClientCardItems().get(0).getStatus());
    assertTrue(value.getIsAvailable());

    book.setIsAvailable(true);
    assertThrows(BookStatusException.class, () -> service.freeBook(book.getId()));
    doReturn(false).when(bookRepository).existsById(nullable(Integer.class));
    assertThrows(BookNotFoundException.class, () -> service.freeBook(book.getId()));

  }

  @Test
  void search() {
    doReturn(page).when(bookRepository).findAll(nullable(Example.class), nullable(Pageable.class));
    ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
    service.search(pageable, book);
    verify(bookRepository).findAll(nullable(Example.class), captor.capture());
    Pageable value = captor.getValue();
    assertEquals(pageable, value);

    doReturn(Page.empty(pageable)).when(bookRepository).findAll(nullable(Example.class), nullable(Pageable.class));
    assertThrows(BookNotFoundException.class, () -> service.search(pageable, book));
  }

  @Test
  void findClientByBookId() {
    doReturn(Optional.of(book)).when(bookRepository).findById(nullable(Integer.class));
    doReturn(true).when(bookRepository).existsById(nullable(Integer.class));
    ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
    assertFalse(book.getIsAvailable());
    Client result = service.findClientByBookId(book.getId());
    assertEquals(client, result);

    book.setIsAvailable(true);
    assertThrows(BookStatusException.class, () -> service.findClientByBookId(book.getId()));
  }
}
