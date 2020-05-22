package com.alevel.library.dto.authentication;


import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class AuthenticationRequestDto {

    @Size(min = 3, max = 100)
    private String login;

    @Size(min = 8, max = 100)
    @Pattern(regexp = "((?=.*[a-z])(?=.*\\\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,})")
    private String password;
}
