package com.vuex.example.vuex.persistences.DTO.User;

import java.time.Duration;

import com.auth0.jwt.algorithms.Algorithm;

public record CreateToken(
    String username, 
    Algorithm algorithm,
    Duration duration,
    String whitUser
) {
    
}
