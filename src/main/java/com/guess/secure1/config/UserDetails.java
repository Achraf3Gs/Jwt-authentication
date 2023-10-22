package com.guess.secure1.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails  {

    private Integer id;
    private String username;
    private String email;
    private String password;
    private String role;
    private String firstname;

    public UserDetails(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }








}
















