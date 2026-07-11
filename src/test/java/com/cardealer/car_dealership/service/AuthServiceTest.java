package com.cardealer.car_dealership.service;



import com.cardealer.car_dealership.dto.LoginRequest;
import com.cardealer.car_dealership.dto.RegisterRequest;
import com.cardealer.car_dealership.entity.User;
import com.cardealer.car_dealership.Repository.UserRepository;
import com.cardealer.car_dealership.Service.AuthService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

class AuthServiceTest {

    @Test
    void shouldRegisterUserSuccessfully() {

        UserRepository repository = Mockito.mock(UserRepository.class);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        AuthService service =
                new AuthService(repository, encoder);

        RegisterRequest request = new RegisterRequest();

        request.setName("Tisha");
        request.setEmail("tisha@gmail.com");
        request.setPassword("123456");

        Mockito.when(repository.existsByEmail(request.getEmail()))
                .thenReturn(false);

        String result = service.register(request);

        assertEquals("User Registered Successfully", result);

        Mockito.verify(repository).save(Mockito.any(User.class));

    }
    
    @Test
    void shouldLoginSuccessfully() {

        UserRepository repository = Mockito.mock(UserRepository.class);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        AuthService service =
                new AuthService(repository, encoder);

        User user = new User();

        user.setEmail("abc@gmail.com");

        user.setPassword(encoder.encode("123456"));

        Mockito.when(repository.findByEmail("abc@gmail.com"))
                .thenReturn(Optional.of(user));

        LoginRequest request = new LoginRequest();

        request.setEmail("abc@gmail.com");

        request.setPassword("123456");

        String result = service.login(request);

        assertEquals("Login Successful", result);

    }
    
    @Test
    void shouldThrowExceptionWhenUserNotFound(){

        UserRepository repository =
                Mockito.mock(UserRepository.class);

        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();

        AuthService service =
                new AuthService(repository, encoder);

        LoginRequest request = new LoginRequest();

        request.setEmail("xyz@gmail.com");

        request.setPassword("123456");

        Mockito.when(repository.findByEmail(request.getEmail()))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> service.login(request));

        assertEquals("User not found",
                exception.getMessage());

    }
    
    @Test
    void shouldThrowExceptionWhenPasswordIsIncorrect() {

        UserRepository repository = Mockito.mock(UserRepository.class);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        AuthService service = new AuthService(repository, encoder);

        User user = new User();
        user.setEmail("abc@gmail.com");
        user.setPassword(encoder.encode("123456"));

        Mockito.when(repository.findByEmail("abc@gmail.com"))
                .thenReturn(Optional.of(user));

        LoginRequest request = new LoginRequest();
        request.setEmail("abc@gmail.com");
        request.setPassword("12345678");

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.login(request)
        );

        assertEquals("Invalid Password", exception.getMessage());
    }

}