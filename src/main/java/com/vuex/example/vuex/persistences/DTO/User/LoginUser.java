package com.vuex.example.vuex.persistences.DTO.User;

import jakarta.validation.constraints.NotBlank;

public record LoginUser(
    @NotBlank
    String email,
    @NotBlank
    String password
) 
{
    
}
