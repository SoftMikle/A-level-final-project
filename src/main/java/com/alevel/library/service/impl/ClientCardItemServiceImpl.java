package com.alevel.library.service.impl;

import com.alevel.library.exceptions.ClientCardItemNotFoundException;
import com.alevel.library.exceptions.ClientNotFoundException;
import com.alevel.library.model.Client;
import com.alevel.library.model.ClientCard;
import com.alevel.library.model.ClientCardItem;
import com.alevel.library.model.additional.enums.Status;
import com.alevel.library.repository.ClientCardItemRepository;
import com.alevel.library.service.ClientCardItemService;
import com.alevel.library.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ClientCardItemServiceImpl implements ClientCardItemService {

    private final ClientCardItemRepository clientCardItemRepository;
    @Autowired
    private ClientService clientService;

    @Autowired
    public ClientCardItemServiceImpl(ClientCardItemRepository clientCardItemRepository) {
        this.clientCardItemRepository = clientCardItemRepository;
    }

    @Override
    public boolean existsById(Integer id) {
        boolean result = clientCardItemRepository.existsById(id);
        if (result) {
            log.info("In existsById - clientCardItem with id: {} exists", id);
        }
        log.info("In existsById - clientCardItem with id: {} not exists", id);
        return result;
    }

    @Override
    public ClientCardItem save(ClientCardItem clientCardItem) {
        clientCardItem.setStatus(Status.RESERVED);
        ClientCardItem registeredClientCardItem = clientCardItemRepository.save(clientCardItem);
        log.info("In create - clientCardItem with id: {} successfully registered", registeredClientCardItem.getId());
        return registeredClientCardItem;
    }

    @Override
    public void updateStatus(ClientCardItem clientCardItem) {
        if (clientCardItem != null && clientCardItem.getId() != null) {
            clientCardItemRepository.save(clientCardItem);
            log.info("In updateStatus - clientCardItem with id: {} successfully changed status",
                    clientCardItem.getId());
        } else {
            log.warn("In updateStatus - clientCardItem with id: {} not found", clientCardItem.getId());
            throw new ClientCardItemNotFoundException("Invalid id for ClientCardItem");
        }
    }

    @Override
    public ClientCardItem findById(Integer id) {
        if (existsById(id)) {
            ClientCardItem result = clientCardItemRepository.findById(id).orElse(null);
            log.info("In findById - clientCardItem of client: {} found by id: {}", result.getClientCard().getClient().getFirstName(), id);
            return result;
        }
        log.warn("In findById - no clientCardItem found by id: {}", id);
        throw new ClientCardItemNotFoundException("Invalid id for ClientCardItem");
    }

    @Override
    public void delete(Integer id) {
        if (clientCardItemRepository.existsById(id)) {
            clientCardItemRepository.deleteById(id);
            log.info("In delete - clientCardItem with id: {} successfully deleted", id);
        }
        log.info("In delete - clientCardItem with id: {} not found", id);
        throw new ClientCardItemNotFoundException("Invalid id for ClientCardItem");
    }

    @Override
    public List<ClientCardItem> findByClientCardId(Integer clientCardId) {
        return clientCardItemRepository.findByClientCardId(clientCardId);
    }

    @Override
    public Page<ClientCardItem> findByClientId(Integer clientId, Pageable pageable) {
        if (clientService.existsById(clientId)) {
            Client client = clientService.findById(clientId);
            ClientCard clientCard = client.getClientCard();
            int clientCardId = clientCard.getId();
            Page<ClientCardItem> result = clientCardItemRepository.findByClientCardId(clientCardId, pageable);
            return result;
        }
        throw new ClientNotFoundException("Client with id: " + clientId + " not found");
    }
}
