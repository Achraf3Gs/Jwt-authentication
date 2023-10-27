package com.guess.secure1.demo;


import com.guess.secure1.user.Pupil;
import com.guess.secure1.user.User;
import com.guess.secure1.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({""})
public class UserController {


    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users/list")
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }
}
