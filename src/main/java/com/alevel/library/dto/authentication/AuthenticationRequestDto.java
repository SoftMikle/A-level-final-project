package com.alevel.library.dto.authentication;


import lombok.Data;

@Data
public class AuthenticationRequestDto {
    private String username;
    private String password;
}
