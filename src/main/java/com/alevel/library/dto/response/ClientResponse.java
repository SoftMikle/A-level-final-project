package com.alevel.library.dto.response;

import javax.validation.constraints.Size;
import java.util.Date;

public class ClientResponse {

    int id;

    @Size(min = 3, max = 50)
    String name;
    @Size(min = 3, max = 100)
    String surname;

    Date birthDate;

    public ClientResponse() {
    }

    public ClientResponse(int id, String name, String surname, Date birthDate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
