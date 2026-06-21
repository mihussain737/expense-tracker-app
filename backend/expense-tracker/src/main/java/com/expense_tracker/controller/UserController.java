package com.expense_tracker.controller;

import com.expense_tracker.entity.User;
import com.expense_tracker.modal.LoginUserDto;
import com.expense_tracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class UserController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/api/auth/login")
    public ResponseEntity<Map> loginUser(
            @RequestBody LoginUserDto loginUserDto) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(),
                                loginUserDto.getPassword()));

        if(authentication.isAuthenticated()){
            return ResponseEntity.ok(Map.of("message", "Login Successful"));
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Invalid Credentials"));
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            return ResponseEntity
                    .badRequest()
                    .body("Username already exists");
        }
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            return ResponseEntity
                    .badRequest()
                    .body("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return  new ResponseEntity<>("User created Successfully", HttpStatus.CREATED);
    }
}
