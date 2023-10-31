package com.vuex.example.vuex.persistences.DTO.Links;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public record RegisterLink(
    @NotBlank
    @Pattern(regexp = "^(http|https)://[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}(/\\S*)?$", 
             message = "URL no válido" )
    String longLink
) 
{}
