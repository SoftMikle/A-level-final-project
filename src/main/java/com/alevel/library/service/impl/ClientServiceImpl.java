package com.alevel.library.service.impl;

import com.alevel.library.exceptions.BookNotFoundException;
import com.alevel.library.exceptions.ClientNotFoundException;
import com.alevel.library.model.*;
import com.alevel.library.model.additional.enums.Status;
import com.alevel.library.repository.ClientRepository;
import com.alevel.library.service.BookService;
import com.alevel.library.service.ClientAccountService;
import com.alevel.library.service.ClientCardService;
import com.alevel.library.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientCardService clientCardService;
    private final ClientAccountService clientAccountService;
    private final BookService bookService;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository,
                             ClientCardService clientCardService,
                             ClientAccountService clientAccountService,
                             BookService bookService) {
        this.clientRepository = clientRepository;
        this.clientCardService = clientCardService;
        this.clientAccountService = clientAccountService;
        this.bookService = bookService;
    }

    @Override
    public boolean existsById(Integer id) {
        boolean result = clientRepository.existsById(id);
        if(result) {
            log.info("In existsById - client with id: {} exists", id);
        }
        log.info("In existsById - client with id: {} not exists", id);
        return result;
    }

    @Override
    public Client save(Client client) {
        client.setStatus(Status.ACTIVE);
        Client registeredClient = clientRepository.save(client);

        System.out.println("id = " + registeredClient.getId());

        ClientCard clientCard = new ClientCard();
        clientCard.setClient(registeredClient);
        clientCard.setStatus(Status.ACTIVE);
        clientCardService.save(clientCard);

        log.info("In create - client: {} successfully registered", registeredClient.getLastName());

        ClientAccountInfo clientAccountInfo = new ClientAccountInfo();
        clientAccountInfo.setDiscount(.0);
        clientAccountInfo.setDonationsAndCharity("");
        clientAccountInfo.setClient(registeredClient);
        clientAccountInfo.setStatus(Status.ACTIVE);
        clientAccountService.save(clientAccountInfo);

        return registeredClient;
    }

    public Client update(Client client) {
        if(existsById(client.getId())){
            Client existingClient = findById(client.getId());
            if(client.getFirstName() != null){
                existingClient.setFirstName(client.getFirstName());
            }
            if(client.getLastName() != null){
                existingClient.setLastName(client.getLastName());
            }
            if(client.getBirthDay() != null){
                existingClient.setBirthDay(client.getBirthDay());
            }
            return clientRepository.save(existingClient);
        }
        throw new ClientNotFoundException("Invalid id for client");
    }


    @Override
    public Page<Client> findAll(Pageable pageable) {
        Page<Client> result = clientRepository.findAll(pageable);
        log.info("In findAll - {} clients found", result.getContent().size());
        return result;
    }

    @Override
    public Page<Client> findByLastName(Pageable pageable, String lastName) {
        Page<Client> result = clientRepository.findByLastName(pageable, lastName);
        log.info("In findBySurname - {} clients found by surname: {}", result.getContent().size(), lastName);
        return result;
    }

    @Override
    public Client findById(Integer id) {
        Client result = clientRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("In findById - no clients found by id: {}", id);
            return null;
        }
        log.info("In findById - client: {} found by id: {}", result.getFirstName(), id);
        return result;
    }

    @Override
    public void delete(Integer id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            log.info("In delete - client with id: {} successfully deleted", id);
        }
        log.info("In delete - client with id: {} not found", id);
    }

    public void setBook(int clientId, int bookId) {
        Client client = clientRepository.findById(clientId).orElse(null);
        Book book = bookService.findById(bookId);
        if(book != null && client != null) {
            ClientCard clientCard = client.getClientCard();
            ClientCardItem result = new ClientCardItem();
            result.setBook(book);
            result.setClientCard(clientCard);
            result.setStatus(Status.RESERVED);

            book.setPopularityIndex(book.getPopularityIndex() + 1);
            book.setAvailable(false);
            bookService.save(book);

            Set<ClientCardItem> clientCardItems = clientCard.getClientCardItems();
            if(clientCardItems == null){
                clientCardItems = Set.of(result);
                clientCard.setClientCardItems(clientCardItems);
            } else {
                clientCard.getClientCardItems().add(result);
            }
            clientCardService.save(clientCard);
        } else {
            if(client == null) {
                throw new ClientNotFoundException("Client with id: " + clientId + " not found");
            } else {
                throw new BookNotFoundException("Book with id: " + bookId + " not found");
            }
        }
    }
}
