package com.alevel.library.rest;

import com.alevel.library.dto.response.UserDto;
import com.alevel.library.model.User;
import com.alevel.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping(value = "users/{id}")
    public ResponseEntity<Integer> deleteUserByUserId(@PathVariable(name = "id") int id) {
        userService.delete(id);
        return ResponseEntity.ok(id);
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") int id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto result = UserDto.toUserDto(user);

        return ResponseEntity.ok(result);

    }

    @GetMapping(value = "users")
    public Page<UserDto> getAllUsers(
            @PageableDefault(page = 0, size = 20)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "login", direction = Sort.Direction.DESC),
                    @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            })
                    Pageable pageable) {

        Page<User> users = userService.findAll(pageable);
        Page<UserDto> result = users.map(UserDto::toUserDto);

        return result;
    }

}
