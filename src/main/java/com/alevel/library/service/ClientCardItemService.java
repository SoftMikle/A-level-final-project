package com.alevel.library.service;

import com.alevel.library.model.ClientCardItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientCardItemService {

    boolean existsById(Integer id);

    ClientCardItem save(ClientCardItem clientCardItem);

    ClientCardItem findById(Integer id);

    void delete(Integer id);

    List<ClientCardItem> findByClientCardId(Integer clientCardId);

    void updateStatus(ClientCardItem clientCardItem);

    Page<ClientCardItem> findByClientId(Integer clientId, Pageable pageable);
}
