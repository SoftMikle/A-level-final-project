package com.alevel.library.dto.response;

import com.alevel.library.dto.request.ClientRequestDto;
import com.alevel.library.model.Client;
import com.alevel.library.model.User;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class ClientResponseDto {

    int id;
    String firstName;
    String lastName;
    Date birthDate;

    public ClientResponseDto() {
    }

    public ClientResponseDto(int id, String firstName, String lastName, Date birthDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
    public Client toClient() {
        Client client = new Client();
        client.setId(id);
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setBirthDay(birthDate);

        return client;
    }

    public static ClientResponseDto toClientResponseDto(Client client) {
        ClientResponseDto clientResponseDto = new ClientResponseDto();
        clientResponseDto.setId(client.getId());
        clientResponseDto.setFirstName(client.getFirstName());
        clientResponseDto.setLastName(client.getLastName());
        clientResponseDto.setBirthDate(client.getBirthDay());

        return clientResponseDto;
    }
}
