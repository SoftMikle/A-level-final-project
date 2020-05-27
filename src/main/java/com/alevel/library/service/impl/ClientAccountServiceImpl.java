package com.alevel.library.service.impl;

import com.alevel.library.exceptions.BookStatusException;
import com.alevel.library.exceptions.ClientAccountNotFoundException;
import com.alevel.library.exceptions.ClientNotFoundException;
import com.alevel.library.model.ClientAccountInfo;
import com.alevel.library.model.additional.enums.Status;
import com.alevel.library.repository.ClientAccountRepository;
import com.alevel.library.repository.ClientRepository;
import com.alevel.library.service.ClientAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ClientAccountServiceImpl implements ClientAccountService {

    @Autowired
    private ClientAccountRepository clientAccountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public boolean existsById(Integer id) {
        boolean result = clientAccountRepository.existsById(id);
        if (result) {
            log.info("In existsById - clientAccountInfo with id: {} exists", id);
        }
        log.info("In existsById - clientAccountInfo with id: {} not exists", id);
        return result;
    }

    @Override
    public ClientAccountInfo save(ClientAccountInfo clientAccountInfo) {
        clientAccountInfo.setStatus(Status.ACTIVE);
        ClientAccountInfo registeredClientAccountInfo = clientAccountRepository.save(clientAccountInfo);
        log.info("In create - clientAccountInfo with id: {} successfully registered", registeredClientAccountInfo.getId());
        return registeredClientAccountInfo;
    }

    @Override
    public ClientAccountInfo findById(Integer id) {
        if (existsById(id)) {
            ClientAccountInfo result = clientAccountRepository.findById(id).orElse(null);
            log.info("In findById - clientAccountInfo of client: {} found by id: {}", result.getClient().getLastName(), id);
            return result;
        }
        log.warn("In findById - no clientAccountInfos found by id: {}", id);
        throw new ClientAccountNotFoundException("no clientAccountInfos found by id: " + id);
    }

    @Override
    public void delete(Integer id) {
        if (clientAccountRepository.existsById(id)) {
            clientAccountRepository.deleteById(id);
            log.info("In delete - clientAccountInfo with id: {} successfully deleted", id);
        }
        log.info("In delete - clientAccountInfo with id: {} not found", id);
        throw new ClientAccountNotFoundException("no clientAccountInfos found by id: " + id);
    }

    public ClientAccountInfo findByClientId(Integer clientId) {
        if (clientRepository.existsById(clientId)) {
            ClientAccountInfo result = clientAccountRepository.findByClientId(clientId);
            if (result == null) {
                throw new ClientAccountNotFoundException("no clientAccountInfos found");
            }
            return result;
        }
        throw new ClientNotFoundException("Invalid id for client");
    }
}
