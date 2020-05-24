package com.alevel.library.repository;

import com.alevel.library.model.ClientCardItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientCardItemRepository extends CrudRepository<ClientCardItem, Integer> {
    List<ClientCardItem> findByClientCardId(Integer clientCardId);

    Page<ClientCardItem> findByClientCardId(Integer clientCardId, Pageable pageable);
}
