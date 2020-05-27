package com.alevel.library.dto.authentication;

import com.alevel.library.model.User;
import com.alevel.library.utills.ValidPassword;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class RegistrationRequestDto {

    @Size(min = 3, max = 100, message = "Login should be between 3 and 100 characters long")
    private String login;

    @Size(min = 3, max = 100)
    private String firstName;

    @Size(min = 3, max = 100)
    private String lastName;

    @Size(min = 5, max = 255)
    @Email
    private String email;

    @ValidPassword
    private String password;

    public static User toUser(RegistrationRequestDto userDto) {
        User user = new User();
        user.setLogin(userDto.getLogin());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        return user;
    }
}
