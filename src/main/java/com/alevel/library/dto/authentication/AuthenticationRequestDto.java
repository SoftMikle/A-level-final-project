package com.alevel.library.dto.authentication;


import lombok.Data;

@Data
public class AuthenticationRequestDto {
    private String login;
    private String password;
}
