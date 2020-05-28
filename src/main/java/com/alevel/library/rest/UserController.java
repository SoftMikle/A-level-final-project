package com.alevel.library.rest;

import com.alevel.library.dto.authentication.RegistrationRequestDto;
import com.alevel.library.dto.response.UserDto;
import com.alevel.library.exceptions.UnauthorizedException;
import com.alevel.library.model.User;
import com.alevel.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") int id) {
        String login = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.findById(id);
        if (user.getLogin().equals(login)) {
            UserDto result = UserDto.toDto(user);
            return ResponseEntity.ok(result);
        }
        throw new UnauthorizedException("Searching account not matches with token");
    }

    @DeleteMapping(value = "/{id}")
    public HttpStatus deleteUserByUserId(@PathVariable(name = "id") int id) {
        String login = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userService.findById(id);
        if (user.getLogin().equals(login)) {
            userService.delete(id);
            return HttpStatus.OK;
        }
        throw new UnauthorizedException("Searching account not matches with token");
    }

    @PatchMapping(value = "/{id}")
    HttpStatus updateUser(@RequestBody RegistrationRequestDto userDto, @PathVariable(name = "id") int id) {
        String login = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userService.findById(id);
        if (user.getLogin().equals(login)) {
            User newUser = RegistrationRequestDto.toUser(userDto);
            newUser.setId(user.getId());
            newUser.setLogin(null);
            newUser.setEmail(null);
            userService.update(newUser);
            return HttpStatus.OK;
        }
        throw new UnauthorizedException("Searching account not matches with token");
    }
}
