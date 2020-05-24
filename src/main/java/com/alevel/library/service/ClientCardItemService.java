package com.alevel.library.service;

import com.alevel.library.model.ClientCard;
import com.alevel.library.model.ClientCardItem;

import java.util.List;

public interface ClientCardItemService {

    boolean existsById(Integer id);

    ClientCardItem save(ClientCardItem clientCardItem);

    ClientCardItem findById(Integer id);

    void delete(Integer id);

    List<ClientCardItem> findByClientCardId(Integer clientCardId);

    void updateStatus(ClientCardItem clientCardItem);
}
