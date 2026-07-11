package com.cardealer.car_dealership.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


public class JwtService {


    private final String SECRET =
            "mysecretkeymysecretkeymysecretkey123456789";


    private SecretKey getSigningKey(){

        return Keys.hmacShaKeyFor(
                SECRET.getBytes(StandardCharsets.UTF_8)
        );
    }


    public String generateToken(String email){


        return Jwts.builder()

                .setSubject(email)

                .setIssuedAt(new Date())

                .setExpiration(
                        new Date(
                        System.currentTimeMillis()
                        + 3600000))

                .signWith(getSigningKey())

                .compact();

    }

}