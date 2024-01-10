package com.raf.clientapplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String role;
    private String forbidden;

    @Override
    public String toString() {
        return "User [id=" + id + ", role=" + role + ", forbidden=" + forbidden + "]";
    }

}
