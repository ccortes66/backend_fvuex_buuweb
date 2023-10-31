package com.vuex.example.vuex.persistences.document;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.vuex.example.vuex.persistences.DTO.User.RegisterUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class UserDocument 
{
    @Id
    private String email;
    private String password;
    private String role;
    
    public UserDocument(RegisterUser user)
    {
        this(user.email(), 
             user.password(), 
             user.role());
    }

    
    
}
