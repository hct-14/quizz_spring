package com.example.quizz.Service;

import com.example.quizz.Entity.User;
import com.example.quizz.Repository.UserRepository;
//import com.example.quizz.SercurityUtil;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public User createUser(User user) {
        boolean userExists = existsByEmail(user.getEmail());
        if (!userExists) {

            return this.userRepository.save(user);
        } else {
            System.out.println("User already exists");
            return null;
        }
    }
    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByEmail(username);
    }
    public void updateUserToken(String token, String email){
        User currentUser = this.handleGetUserByUsername(email);
        if(currentUser != null){
            currentUser.setRefreshToken(token);
            this.userRepository.save(currentUser);
        }
    }
    public User getUserByRefreshTokenAndEmail(String token, String email) {
        return this.userRepository.findByRefreshTokenAndEmail(token, email);
    }


}
