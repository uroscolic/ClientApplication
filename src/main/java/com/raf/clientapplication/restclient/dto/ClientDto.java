package com.raf.clientapplication.restclient.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDto {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private int numberOfTrainings;

 
}
