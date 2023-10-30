package com.vuex.example.vuex.persistences.DTO.User;

import com.vuex.example.vuex.persistences.document.UserDocument;

public record ShowUser(
    String email,
    String role
) 
{
    public ShowUser(UserDocument document)
    {
        this(document.getEmail(), 
             document.getRole());
    }
}
