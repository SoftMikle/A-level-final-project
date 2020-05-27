package com.alevel.library.rest;

import com.alevel.library.dto.response.UserResponseDto;
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

    @GetMapping(value = "/users")
    public Page<UserResponseDto> getAllUsers(
            @PageableDefault(page = 0, size = 20)
            @SortDefault.SortDefaults({@SortDefault(sort = "id", direction = Sort.Direction.ASC)})
                    Pageable pageable) {
        Page<User> users = userService.findAll(pageable);
        Page<UserResponseDto> result = users.map(UserResponseDto::toDto);

        return result;
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable(name = "id") int id) {
        User user = userService.findById(id);
        UserResponseDto result = UserResponseDto.toDto(user);

        return ResponseEntity.ok(result);

    }

    @DeleteMapping(value = "/users/{id}")
    public HttpStatus deleteUserByUserId(@PathVariable(name = "id") int id) {
        userService.delete(id);
        return HttpStatus.OK;
    }

}
