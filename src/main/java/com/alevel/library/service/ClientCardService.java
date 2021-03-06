package com.alevel.library.service;

import com.alevel.library.model.ClientCard;

public interface ClientCardService {

    boolean existsById(Integer id);

    ClientCard save(ClientCard clientCard);

    ClientCard findById(Integer id);

    void delete(Integer id);

    ClientCard findByClientId(Integer clientId);

}
