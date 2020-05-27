package com.alevel.library.repository;

import com.alevel.library.model.ClientAccountInfo;
import org.springframework.data.repository.CrudRepository;

public interface ClientAccountRepository extends CrudRepository<ClientAccountInfo, Integer> {
    ClientAccountInfo findByClientId(Integer clientId);
}
