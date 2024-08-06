package com.example.quizz.Controller;


import com.example.quizz.Entity.User;
import com.example.quizz.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;



    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String huhu(){
        return "hoang cong thanh dep trai";
    }
    @PostMapping("/test")
    public User test(@Valid @RequestBody User user){
        String hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        User createUser = this.userService.createUser(user);
       return createUser;

    }
}
