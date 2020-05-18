package com.alevel.library.rest;

import com.alevel.library.dto.authentication.AuthenticationRequestDto;
import com.alevel.library.dto.request.RegistrationRequestDto;
import com.alevel.library.dto.response.AuthenticationResponseDto;
import com.alevel.library.model.User;
import com.alevel.library.security.jwt.JwtTokenProvider;
import com.alevel.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "library/auth")
public class AuthenticationControllerV1 {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthenticationControllerV1(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String login = requestDto.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, requestDto.getPassword()));
            User user = userService.findByLogin(login);
            if (user == null) {
                throw new UsernameNotFoundException("User with login: " + login + " not found");
            }
            AuthenticationResponseDto response = new AuthenticationResponseDto();

            String token = jwtTokenProvider.createToken(login, user.getRoles());
            response.setToken(token);
            response.setLogin(login);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity signUp(@RequestBody RegistrationRequestDto requestDto) {
        try {
            String login = requestDto.getLogin();
            User user = userService.findByLogin(login);
            if (user != null) {
                return new ResponseEntity("User: " + login + " already exists", HttpStatus.CONFLICT);
            }
            user = RegistrationRequestDto.toUser(requestDto);
            userService.register(user);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, requestDto.getPassword()));
            AuthenticationResponseDto response = new AuthenticationResponseDto();

            String token = jwtTokenProvider.createToken(login, user.getRoles());
            response.setToken(token);
            response.setLogin(login);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username, email or password");
        }
    }

}
