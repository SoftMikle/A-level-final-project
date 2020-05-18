package com.alevel.library.dto.response;


import lombok.Data;

@Data
public class AuthenticationResponseDto {
    private String login;
    private String token;
}
