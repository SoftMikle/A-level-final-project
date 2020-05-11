package com.alevel.library.dto.response;

import com.alevel.library.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserEntityDto {

    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    public UserEntity toUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setUsername(username);
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setEmail(email);

        return userEntity;
    }

    public static UserEntityDto toUserEntityDto(UserEntity userEntity) {
        UserEntityDto userEntityDto = new UserEntityDto();
        userEntityDto.setId(userEntity.getId());
        userEntityDto.setUsername(userEntity.getUsername());
        userEntityDto.setFirstName(userEntity.getFirstName());
        userEntityDto.setLastName(userEntity.getLastName());
        userEntityDto.setEmail(userEntity.getEmail());

        return userEntityDto;
    }

}
