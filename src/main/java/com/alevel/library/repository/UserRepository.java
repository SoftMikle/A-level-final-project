package com.alevel.library.repository;

import com.alevel.library.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);

}

