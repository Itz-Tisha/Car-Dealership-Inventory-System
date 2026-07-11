package com.cardealer.car_dealership.security;




import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class JwtServiceTest {


    @Test
    void shouldGenerateJwtToken() {

        JwtService jwtService = new JwtService();


        String token =
                jwtService.generateToken("abc@gmail.com");


        assertNotNull(token);

        assertFalse(token.isEmpty());

    }

}