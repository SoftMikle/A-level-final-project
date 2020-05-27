package com.alevel.library.dto.response;

import com.alevel.library.model.User;
import com.alevel.library.model.additional.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponseDto {

    private int id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private Status status;

    public static UserResponseDto toDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setLogin(user.getLogin());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setStatus(user.getStatus());

        return userResponseDto;
    }

}
