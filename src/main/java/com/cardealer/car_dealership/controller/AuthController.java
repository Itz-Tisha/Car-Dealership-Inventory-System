package com.cardealer.car_dealership.controller;

import com.cardealer.car_dealership.Repository.UserRepository;
import com.cardealer.car_dealership.Service.AuthService;
import com.cardealer.car_dealership.dto.AuthResponse;
import com.cardealer.car_dealership.dto.LoginRequest;
import com.cardealer.car_dealership.dto.MessageResponse;
import com.cardealer.car_dealership.dto.RegisterRequest;
import com.cardealer.car_dealership.entity.User;
import com.cardealer.car_dealership.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService,
                          JwtService jwtService,
                          UserRepository userRepository) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(
            @RequestBody RegisterRequest request) {

        String message = authService.register(request);
        return ResponseEntity.ok(new MessageResponse(message));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {

        String message = authService.login(request);

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole()
        );

        return ResponseEntity.ok(
                new AuthResponse(token, user.getRole(), message)
        );
    }
}
