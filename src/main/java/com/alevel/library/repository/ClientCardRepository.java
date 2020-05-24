package com.alevel.library.repository;

import com.alevel.library.model.ClientCard;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientCardRepository extends CrudRepository<ClientCard, Integer> {
    Optional<ClientCard> findByClientId(Integer clientId);

}
