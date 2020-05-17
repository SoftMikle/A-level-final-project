package com.alevel.library.service;

import com.alevel.library.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User register(User user);

    List<User> getAll();

    User findByUserName(String username);

    User findById(Integer id);

    void delete(Integer id);

    Page<User> findAll(Pageable pageable);
}
