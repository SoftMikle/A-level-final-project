package com.alevel.library.service.impl;

import com.alevel.library.exceptions.BookNotFoundException;
import com.alevel.library.exceptions.BookStatusException;
import com.alevel.library.exceptions.ClientNotFoundException;
import com.alevel.library.model.*;
import com.alevel.library.model.additional.enums.Status;
import com.alevel.library.repository.ClientRepository;
import com.alevel.library.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientCardService clientCardService;
    @Autowired
    private ClientAccountService clientAccountService;
    @Autowired
    private BookService bookService;
    @Autowired
    private ClientCardItemService clientCardItemService;

    @Override
    public boolean existsById(Integer id) {
        boolean result = clientRepository.existsById(id);
        if (result) {
            log.info("In existsById - client with id: {} exists", id);
        }
        log.info("In existsById - client with id: {} not exists", id);
        return result;
    }

    @Override
    public Client save(Client client) {
        client.setStatus(Status.ACTIVE);
        Client registeredClient = clientRepository.save(client);

        ClientCard clientCard = new ClientCard();
        clientCard.setClient(registeredClient);
        clientCard.setStatus(Status.ACTIVE);
        clientCardService.save(clientCard);

        ClientAccountInfo clientAccountInfo = new ClientAccountInfo();
        clientAccountInfo.setDiscount(.0);
        clientAccountInfo.setDonationsAndCharity("");
        clientAccountInfo.setClient(registeredClient);
        clientAccountInfo.setStatus(Status.ACTIVE);
        clientAccountService.save(clientAccountInfo);

        log.info("In create - client: {} successfully registered", registeredClient.getLastName());

        return registeredClient;
    }

    public Client update(Client client) {
        if (existsById(client.getId())) {
            Client existingClient = findById(client.getId());
            if (client.getFirstName() != null) {
                existingClient.setFirstName(client.getFirstName());
            }
            if (client.getLastName() != null) {
                existingClient.setLastName(client.getLastName());
            }
            if (client.getBirthDay() != null) {
                existingClient.setBirthDay(client.getBirthDay());
            }
            if (client.getIsDebtor() != null) {
                existingClient.setIsDebtor(client.getIsDebtor());
            }
            return clientRepository.save(existingClient);
        }
        throw new ClientNotFoundException("Invalid id for client");
    }


    @Override
    public Page<Client> findAll(Pageable pageable) {
        Page<Client> result = clientRepository.findAll(pageable);
        if (result.getContent().size() < 1) {
            throw new ClientNotFoundException("No clients found by searching request");
        }
        log.info("In findAll - {} clients found", result.getContent().size());
        return result;
    }

    @Override
    public Client findById(Integer id) {
        if (clientRepository.existsById(id)) {
            Client result = clientRepository.findById(id).orElse(null);
            log.info("In findById - client: {} found by id: {}", result.getFirstName(), id);
            return result;
        }
        log.warn("In findById - no clients found by id: {}", id);
        throw new ClientNotFoundException("Invalid id for client");
    }

    @Override
    public void delete(Integer id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            log.info("In delete - client with id: {} successfully deleted", id);
        } else {
            log.info("In delete - client with id: {} not found", id);
            throw new ClientNotFoundException("Invalid id for client");
        }
    }

    public void setBook(int clientId, int bookId) {
        Client client = clientRepository.findById(clientId).orElse(null);
        Book book = bookService.findById(bookId);
        if (book != null && client != null) {
            if (!book.getIsAvailable()) {
                throw new BookStatusException("Book is already reserved");
            }
            ClientCard clientCard = client.getClientCard();
            ClientCardItem result = new ClientCardItem();

            result.setBook(book);
            result.setClientCard(clientCard);
            result.setStatus(Status.RESERVED);
            clientCardItemService.save(result);

            book.setPopularityIndex(book.getPopularityIndex() + 1);
            book.setIsAvailable(false);
            bookService.save(book);

            Set<ClientCardItem> clientCardItems = clientCard.getClientCardItems();
            if (clientCardItems == null) {
                Set<ClientCardItem> set = new HashSet<>();
                set.add(result);
                clientCardItems = set;
                clientCard.setClientCardItems(clientCardItems);
            } else {
                clientCard.getClientCardItems().add(result);
            }
            clientCardService.save(clientCard);
        } else {
            if (client == null) {
                throw new ClientNotFoundException("Client with id: " + clientId + " not found");
            } else {
                throw new BookNotFoundException("Book with id: " + bookId + " not found");
            }
        }
    }

    @Override
    public Page<Client> findAllDebtors(Pageable pageable) {
        Page<Client> result = clientRepository.findByIsDebtor(pageable, true);
    if (result.getContent().size() < 1) {
      throw new ClientNotFoundException("No debtors found by searching request");
    }
        return result;
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
        if (result.getContent().size() < 1) {
            throw new BookNotFoundException("No books found by client: " + clientId);
        }
        return result;
    }

    @Override
    public Page<Client> search(Pageable pageable, Client client) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withMatcher("firstName", contains())
                .withMatcher("lastName", contains())
                .withMatcher("birthDay", exact());
        Example<Client> example = Example.of(client, matcher);

        Page<Client> result = clientRepository.findAll(example, pageable);
        if (result.getContent().size() < 1) {
            throw new ClientNotFoundException("No clients found by searching request");
        }
        return result;
    }
}
