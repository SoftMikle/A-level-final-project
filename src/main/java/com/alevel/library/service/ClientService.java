package com.alevel.library.service;

import com.alevel.library.model.Book;
import com.alevel.library.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {

    boolean existsById(Integer id);

    Client save(Client client);

    Client update(Client client);

    Client findById(Integer id);

    void delete(Integer id);

    Page<Client> findAll(Pageable pageable);

    void setBook(int clientId, int bookId);

    Page<Client> findAllDebtors(Pageable pageable);

    Page<Client> search(Pageable pageable, Client client);

    Page<Book> findAllBooksByClientId(Integer clientId, Pageable pageable);
}
