package com.alevel.library.service.impl;

import com.alevel.library.exceptions.UserConflictException;
import com.alevel.library.exceptions.UserNotFoundException;
import com.alevel.library.model.Role;
import com.alevel.library.model.User;
import com.alevel.library.model.additional.enums.Status;
import com.alevel.library.repository.RoleRepository;
import com.alevel.library.repository.UserRepository;
import com.alevel.library.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User register(User user) {
        User existingUser = checkUnique(user);
        if (existingUser != null) {
            if (existingUser.getLogin() != null) {
                throw new UserConflictException("User with login: " + user.getLogin() + " already exists");
            }
            throw new UserConflictException("User with email: " + user.getEmail() + " already exists");
        }
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(user);
        log.info("In register - user: {} successfully registered", registeredUser.getLogin());

        return registeredUser;
    }

    private User checkUnique(User user) {
        User result = userRepository.findByLogin(user.getLogin());
        if (result != null) {
            result.setEmail(null);
            return result;
        }
        result = userRepository.findByEmail(user.getEmail());
        if (result != null) {
            result.setLogin(null);
            return result;
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> result = (List<User>) userRepository.findAll();
        log.info("In findAll -  {} users found", result.size());
        return result;
    }

    public Page<User> findAll(Pageable pageable) {
        Page<User> result = userRepository.findAll(pageable);
        return result;
    }

    @Override
    public void update(User user) {
        if (existsById(user.getId())) {
            User existingUser = findById(user.getId());
            if (user.getFirstName() != null) {
                existingUser.setFirstName(user.getFirstName());
            }
            if (user.getLastName() != null) {
                existingUser.setLastName(user.getLastName());
            }
            if (user.getPassword() != null) {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userRepository.save(existingUser);
            return;
        }
        throw new UserNotFoundException("Invalid id for client");
    }

    private boolean existsById(Integer id) {
        return userRepository.existsById(id);
    }

    @Override
    public User findByLogin(String login) {
        User result = userRepository.findByLogin(login);
        if (result == null) {
            throw new UsernameNotFoundException("User with login: " + login + " not found");
        }
        log.info("In findByLogin - user with login: {} found", login);
        return result;
    }

    @Override
    public User findById(Integer id) {
        if (existsById(id)) {
            User result = userRepository.findById(id).orElse(null);
            log.info("In findById - user: {} found by id: {}", result.getLogin(), id);
            return result;
        }
        log.warn("In findById - no users found by id: {}", id);
        throw new UserNotFoundException("User not found by id: " + id);
    }

    @Override
    public void delete(Integer id) {
        if (existsById(id)) {
            userRepository.deleteById(id);
            log.info("In delete - user with id: {} successfully deleted", id);
        }
        throw new UserNotFoundException("User not found by id: " + id);
    }
}
