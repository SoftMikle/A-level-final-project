package com.alevel.library.dto.response;

import com.alevel.library.model.Client;
import lombok.Data;

import java.util.Date;

@Data
public class ClientResponseDto {

    int id;
    String firstName;
    String lastName;
    Date birthDate;
    Boolean isDebtor;

    public ClientResponseDto() {
    }

    public static ClientResponseDto toDto(Client client) {
        ClientResponseDto clientResponseDto = new ClientResponseDto();
        clientResponseDto.setId(client.getId());
        clientResponseDto.setFirstName(client.getFirstName());
        clientResponseDto.setLastName(client.getLastName());
        clientResponseDto.setBirthDate(client.getBirthDay());
        clientResponseDto.setIsDebtor(client.getIsDebtor());

        return clientResponseDto;
    }
}
