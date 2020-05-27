package com.alevel.library.service.impl;

import com.alevel.library.exceptions.ClientCardNotFoundException;
import com.alevel.library.model.ClientCard;
import com.alevel.library.model.additional.enums.Status;
import com.alevel.library.repository.ClientCardRepository;
import com.alevel.library.service.ClientCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ClientCardServiceImpl implements ClientCardService {

    private final ClientCardRepository clientCardRepository;

    @Autowired
    public ClientCardServiceImpl(ClientCardRepository clientCardRepository) {
        this.clientCardRepository = clientCardRepository;
    }

    @Override
    public boolean existsById(Integer id) {
        boolean result = clientCardRepository.existsById(id);
        if (result) {
            log.info("In existsById - clientCard with id: {} exists", id);
        }
        log.info("In existsById - clientCard with id: {} not exists", id);
        return result;
    }

    @Override
    public ClientCard save(ClientCard clientCard) {
        clientCard.setStatus(Status.ACTIVE);
        ClientCard registeredClientCard = clientCardRepository.save(clientCard);
        log.info("In create - clientCard with id: {} successfully registered", registeredClientCard.getId());
        return registeredClientCard;
    }

    @Override
    public ClientCard findById(Integer id) {
        if (existsById(id)) {
        ClientCard result = clientCardRepository.findById(id).orElse(null);
            log.info("In findById - clientCard of client: {} found by id: {}", result.getClient().getFirstName(), id);
            return result;
        }
        log.warn("In findById - no clientCards found by id: {}", id);
        throw new ClientCardNotFoundException("Invalid id for clientCard");
    }

    @Override
    public void delete(Integer id) {
        if (clientCardRepository.existsById(id)) {
            clientCardRepository.deleteById(id);
            log.info("In delete - clientCard with id: {} successfully deleted", id);
        }
        log.info("In delete - clientCard with id: {} not found", id);
        throw new ClientCardNotFoundException("Invalid id for clientCard");
    }

    @Override
    public ClientCard findByClientId(Integer clientId) {
        return clientCardRepository.findByClientId(clientId).orElseThrow(
                () -> new ClientCardNotFoundException("ClientCard not found by clientId"));
    }
}
