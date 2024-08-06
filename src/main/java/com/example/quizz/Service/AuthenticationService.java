//package com.example.quizz.Service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Service;
//
//    @Service
//    public class AuthenticationService {
//
//        private final AuthenticationManager authenticationManager;
//
//        @Autowired
//        public AuthenticationService(AuthenticationManager authenticationManager) {
//            this.authenticationManager = authenticationManager;
//        }
//
//        public Authentication authenticate(String username, String password) {
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
//            return authenticationManager.authenticate(authenticationToken);
//        }
//    }
//
