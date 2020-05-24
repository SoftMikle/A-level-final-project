package com.alevel.library.repository;

import com.alevel.library.model.ClientCard;
import com.alevel.library.model.ClientCardItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ClientCardItemRepository extends CrudRepository<ClientCardItem, Integer> {
    List<ClientCardItem> findByClientCardId(Integer clientCardId);
}
