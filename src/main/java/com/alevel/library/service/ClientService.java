package com.alevel.library.service;

import com.alevel.library.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {

    boolean existsById(Integer id);

    Client save(Client client);

    Client update(Client client);

    Page<Client> findByLastName(Pageable pageable, String lastName);

    Client findById(Integer id);

    void delete(Integer id);

    Page<Client> findAll(Pageable pageable);

    void setBook(int clientId, int bookId);
}
