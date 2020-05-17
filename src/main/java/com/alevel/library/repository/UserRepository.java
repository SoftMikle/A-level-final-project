package com.alevel.library.repository;

import com.alevel.library.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    User findByUserName(String userName);

    Page<User> findAll(Pageable pageable);

}

