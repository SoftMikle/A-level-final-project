package com.alevel.library.repository;

import com.alevel.library.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientRepository extends PagingAndSortingRepository<Client, Integer> {

    Page<Client> findByLastName(Pageable pageable, String lastName);


}
