package com.raf.clientapplication.restclient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class UserDto {

	private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private String role;
    private String password;
    private boolean banned;
    private int numberOfTrainings;
    public UserDto() {
    }
    public UserDto(String email, String firstName, String lastName, String username,String password, String role, boolean banned) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.role = role;
        this.banned = banned;
    }
    @Override
    public String toString() {
        return "UserDto [email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", username="
                + username + ", role=" + role + ", banned=" + banned + "]";
    }
}
