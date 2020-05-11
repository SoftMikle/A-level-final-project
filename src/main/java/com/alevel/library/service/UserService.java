package com.alevel.library.service;

import com.alevel.library.model.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity register(UserEntity user);

    List<UserEntity> getAll();

    UserEntity findByUsername(String username);

    UserEntity findById(Integer id);

    void delete(Integer id);
}
