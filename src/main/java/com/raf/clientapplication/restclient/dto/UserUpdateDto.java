package com.raf.clientapplication.restclient.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    private String oldUsername;
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean banned;
}
