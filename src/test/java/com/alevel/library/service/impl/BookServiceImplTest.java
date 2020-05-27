package com.alevel.library.service.impl;

import com.alevel.library.model.Book;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
class BookServiceImplTest {

  @Mock private BookRepository bookRepository;

  @Mock private ClientCardItemService client;

  @InjectMocks private BookServiceImpl service; // = new BookServiceImpl(bookRepository);

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void save() {
    Book book = new Book();
    book.setName("best");
    doReturn(book).when(bookRepository).save(nullable(Book.class));
    ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
    service.save(book);
    verify(bookRepository).save(captor.capture());
    Book value = captor.getValue();
    assertEquals("best", value.getName());
  }
}
