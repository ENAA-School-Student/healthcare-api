package com.healthcare.medicalsystem.controller;

import com.healthcare.medicalsystem.configuration.JwtUtils;
import com.healthcare.medicalsystem.entity.User;
import com.healthcare.medicalsystem.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Le nom d'utilisateur est déjà pris");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("L'email est déjà utilisé");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User userSaved =  userRepository.save(user);
        Map<String, Object> response = new HashMap<>();
        response.put("token", jwtUtils.generateToken(userSaved.getUsername(), userSaved.getRole().name()));
        response.put("type", "Bearer");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody User user) {
        try {
             Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
             if(authentication.isAuthenticated()) {
                 Map<String, Object> response = new HashMap<>();
                 response.put("token", jwtUtils.generateToken(user.getUsername(), user.getRole().name()));
                 response.put("type", "Bearer");
                 return ResponseEntity.ok(response);
             }
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nom d'utilisateur ou mot de passe incorrect");

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nom d'utilisateur ou mot de passe incorrect");
        }
    }


}
