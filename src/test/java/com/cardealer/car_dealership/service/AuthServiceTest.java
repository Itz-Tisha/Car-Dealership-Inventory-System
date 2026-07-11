package com.cardealer.car_dealership.service;



import com.cardealer.car_dealership.dto.RegisterRequest;
import com.cardealer.car_dealership.entity.User;
import com.cardealer.car_dealership.Repository.UserRepository;
import com.cardealer.car_dealership.Service.AuthService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

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

}