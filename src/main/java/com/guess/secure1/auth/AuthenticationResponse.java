package com.guess.secure1.auth;

import com.guess.secure1.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String message;

    private String token;

    private String name;

    private Role role;

    private Integer id;








}
