package com.alevel.library.repository;

import com.alevel.library.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    User findByLogin(String login);

    User findByEmail(String email);
}

