package com.alevel.library.dto.request;

import com.alevel.library.dto.response.UserDto;
import com.alevel.library.model.Client;
import com.alevel.library.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientRequestDto {

    @Size(min = 3, max = 50)
    String firstName;
    @Size(min = 3, max = 100)
    String lastName;

    Date birthDate;

    public ClientRequestDto() {
    }

    public ClientRequestDto(String firstName, String lastName, Date birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public Client toClient() {
        Client client = new Client();
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setBirthDay(birthDate);

        return client;
    }

    public static ClientRequestDto toClientRequestDto(Client client) {
        ClientRequestDto clientRequestDto = new ClientRequestDto();
        clientRequestDto.setFirstName(client.getFirstName());
        clientRequestDto.setLastName(client.getLastName());
        clientRequestDto.setBirthDate(client.getBirthDay());

        return clientRequestDto;
    }
}
