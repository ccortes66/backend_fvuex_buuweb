package com.vuex.example.vuex.persistences.DTO.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterUser(
    @NotBlank
    //email validator
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
             message = "email no válido"  )
    String email,
    @Size(min = 8, max = 20)
    String password,
    @Size(min = 8, max = 20)
    String reepasword,
    @NotBlank
    String role
){} 

