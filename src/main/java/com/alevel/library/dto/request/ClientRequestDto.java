package com.alevel.library.dto.request;

import com.alevel.library.model.Client;
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

    Boolean isDebtor;

    public Client toClient() {
        Client client = new Client();
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setBirthDay(birthDate);
        client.setIsDebtor(isDebtor);

        return client;
    }
}
