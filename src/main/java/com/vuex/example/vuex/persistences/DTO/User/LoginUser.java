package com.vuex.example.vuex.persistences.DTO.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginUser(
    @NotBlank
    @Email
    String email,
    @NotBlank
    String password
) 
{
    
}
