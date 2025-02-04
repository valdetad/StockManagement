package com.example.StockManagement.controller;

import com.example.StockManagement.data.dtos.AuthenticationResponse;
import com.example.StockManagement.data.model.User;
import com.example.StockManagement.service.AuthenticationService;
import com.example.StockManagement.service.JwtService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationService authService;
    private final JwtService jwtService;

    public AuthenticationController(AuthenticationService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User request) {
        try {
            AuthenticationResponse response = authService.register(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This username already exists. Try something else!");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestParam String refreshToken) {
        try {
            String username = jwtService.extractUsername(refreshToken);
            if (jwtService.isRefreshTokenValid(refreshToken, username)) {
                User user = authService.getUserByUsername(username);

                String newAccessToken = jwtService.generateToken(user);
                String newRefreshToken = jwtService.generateRefreshToken(user);

                return ResponseEntity.ok(new AuthenticationResponse(newAccessToken, newRefreshToken));
            }

            throw new IllegalArgumentException("Invalid or expired refresh token");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
        }
    }
}

