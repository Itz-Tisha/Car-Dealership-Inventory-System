package com.cardealer.car_dealership.Service;


import com.cardealer.car_dealership.dto.LoginRequest;
import com.cardealer.car_dealership.dto.RegisterRequest;
import com.cardealer.car_dealership.entity.User;
import com.cardealer.car_dealership.Repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AuthService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;

    public AuthService(UserRepository repository,
                       BCryptPasswordEncoder encoder){

        this.repository=repository;
        this.encoder=encoder;
    }

    public String register(RegisterRequest request){

        if(repository.existsByEmail(request.getEmail())){

            throw new RuntimeException("Email already exists");
        }

        User user=new User();

        user.setName(request.getName());

        user.setEmail(request.getEmail());

        user.setPassword(
                encoder.encode(request.getPassword()));

        user.setRole("USER");

        repository.save(user);

        return "User Registered Successfully";
    }
    
    public String login(LoginRequest request) {

        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!encoder.matches(request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid Password");
        }

        return "Login Successful";
    }

}