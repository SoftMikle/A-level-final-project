package com.alevel.library.service;

import com.alevel.library.model.Client;
import com.alevel.library.model.ClientAccountInfo;
import com.alevel.library.model.ClientCard;

public interface ClientAccountService {

    ClientAccountInfo findByClientId(Integer clientId);

    boolean existsById(Integer id);

    ClientAccountInfo save(ClientAccountInfo clientAccountInfo);

    ClientAccountInfo findById(Integer id);

    void delete(Integer id);

}
