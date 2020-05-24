package com.alevel.library.service.impl;

import com.alevel.library.model.ClientCardItem;
import com.alevel.library.model.additional.enums.Status;
import com.alevel.library.repository.ClientCardItemRepository;
import com.alevel.library.service.ClientCardItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ClientCardItemServiceImpl implements ClientCardItemService {

    private final ClientCardItemRepository clientCardItemRepository;

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
        clientCardItem.setStatus(Status.ACTIVE);
        ClientCardItem registeredClientCardItem = clientCardItemRepository.save(clientCardItem);
        log.info("In create - clientCardItem with id: {} successfully registered", registeredClientCardItem.getId());
        return registeredClientCardItem;
    }

    @Override
    public void updateStatus(ClientCardItem clientCardItem) {
        if(clientCardItem != null){
            clientCardItemRepository.save(clientCardItem);
            log.info("In updateStatus - clientCardItem with id: {} successfully changed status",
                    clientCardItem.getId());
        } else {
            log.warn("In updateStatus - clientCardItem with id: {} not found", clientCardItem.getId());
        }
    }

    @Override
    public ClientCardItem findById(Integer id) {
        ClientCardItem result = clientCardItemRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("In findById - no clientCardItem found by id: {}", id);
            return null;
        }
        log.info("In findById - clientCardItem of client: {} found by id: {}", result.getClientCard().getClient().getFirstName(), id);
        return result;
    }

    @Override
    public void delete(Integer id) {
        if (clientCardItemRepository.existsById(id)) {
            clientCardItemRepository.deleteById(id);
            log.info("In delete - clientCardItem with id: {} successfully deleted", id);
        }
        log.info("In delete - clientCardItem with id: {} not found", id);
    }

    @Override
    public List<ClientCardItem> findByClientCardId(Integer clientCardId) {
        return clientCardItemRepository.findByClientCardId(clientCardId);
    }
}
