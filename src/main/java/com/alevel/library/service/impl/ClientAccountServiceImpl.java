package com.alevel.library.service.impl;

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

    private final ClientAccountRepository clientAccountRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public ClientAccountServiceImpl(ClientAccountRepository clientAccountRepository, ClientRepository clientRepository) {
        this.clientAccountRepository = clientAccountRepository;
        this.clientRepository = clientRepository;
    }

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
        ClientAccountInfo result = clientAccountRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("In findById - no clientAccountInfos found by id: {}", id);
            return null;
        }
        log.info("In findById - clientAccountInfo of client: {} found by id: {}", result.getClient().getLastName(), id);
        return result;
    }

    @Override
    public void delete(Integer id) {
        if (clientAccountRepository.existsById(id)) {
            clientAccountRepository.deleteById(id);
            log.info("In delete - clientAccountInfo with id: {} successfully deleted", id);
        }
        log.info("In delete - clientAccountInfo with id: {} not found", id);
    }

    public ClientAccountInfo findByClientId(Integer clientId) {
        if(clientRepository.existsById(clientId)){
            ClientAccountInfo result = clientAccountRepository.findByClientId(clientId);
            return result;
        }
        throw new ClientNotFoundException("Invalid id for client");
    }
}
