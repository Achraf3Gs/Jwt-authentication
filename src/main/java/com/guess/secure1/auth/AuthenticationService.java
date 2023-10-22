package com.guess.secure1.auth;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.guess.secure1.user.User;
import com.guess.secure1.config.JwtService;
import com.guess.secure1.user.Role;

import com.guess.secure1.user.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user=User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();
        repository.save(user);
        var jwtToken= jwtService.generateToken((User) user);

        Role role= user.getRole();

        return AuthenticationResponse.builder()
                .role(role)
                .token(jwtToken)
                .build();


    }



    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken= jwtService.generateToken(user);
        String username = jwtService.extractUsername(jwtToken);
        Role role= user.getRole();
        String name= user.getFirstname();
        Integer id= user.getId();
        AuthenticationResponse response = AuthenticationResponse.builder()

                .token(jwtToken)
                .role(role)
                .id(id)
                .name(name)
                .build();
        return response;
    }

}


