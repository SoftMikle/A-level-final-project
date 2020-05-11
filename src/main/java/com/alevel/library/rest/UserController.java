package com.alevel.library.rest;

import com.alevel.library.dto.response.UserEntityDto;
import com.alevel.library.model.UserEntity;
import com.alevel.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/library/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<UserEntityDto> getUserById(@PathVariable(name = "id") int id) {
        UserEntity userEntity = userService.findById(id);

        if(userEntity == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserEntityDto result = UserEntityDto.toUserEntityDto(userEntity);

        return ResponseEntity.ok(result);

    }
}
