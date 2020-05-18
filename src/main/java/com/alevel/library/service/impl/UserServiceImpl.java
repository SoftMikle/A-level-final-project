package com.alevel.library.service.impl;

import com.alevel.library.model.Role;
import com.alevel.library.model.Status;
import com.alevel.library.model.User;
import com.alevel.library.repository.RoleRepository;
import com.alevel.library.repository.UserRepository;
import com.alevel.library.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(user);
        log.info("In register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = (List<User>) userRepository.findAll();
        log.info("In gatAll -  {} users found", result.size());
        return result;
    }

    public Page<User> findAll(Pageable pageable) {
        Page<User> result = userRepository.findAll(pageable);
        return result;
    }

    @Override
    public User findByLogin(String login) {
        User result = userRepository.findByLogin(login);
        log.info("In findByLogin - user: {} found by login: {}", result, login);
        return result;
    }

    @Override
    public User findById(Integer id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("In findById - no users found by id: {}", id);
            return null;
        }
        log.info("In findById - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
        log.info("In delete - user with id: {} successfully deleted", id);
    }
}
