package com.raf.clientapplication.restclient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClientCreateDto extends UserCreateDto{
    public ClientCreateDto() {
        super();
    }
    public ClientCreateDto(String firstName, String lastName, String username, String password, String email, LocalDate birthDate) {
        super(firstName, lastName, username, password, email,  birthDate);
    }

}
