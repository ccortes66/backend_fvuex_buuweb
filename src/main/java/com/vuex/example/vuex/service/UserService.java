package com.vuex.example.vuex.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vuex.example.vuex.persistences.DTO.User.RegisterUser;
import com.vuex.example.vuex.persistences.DTO.User.ShowUser;
import com.vuex.example.vuex.persistences.document.UserDocument;
import com.vuex.example.vuex.persistences.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService
{
    
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
    {
        return repository.findById(username).get();
    }
    
    @Transactional
    public ShowUser registerUser(RegisterUser registerUser)
    {  
        if(!registerUser.password().equals(registerUser.reepasword())) 
           { throw new IllegalArgumentException("contraeñas no coinciden"); }
        
        repository.findById(registerUser.email())
                .ifPresent((data) -> { throw new IllegalArgumentException("usuario ya existe"); });
       
        UserDocument document = new UserDocument(registerUser);
        document.setPassword(passwordEncoder.encode(registerUser.password()));
        return new ShowUser(repository.save(document));
 
    }

    public ShowUser meUser(String username)
    {
        return new ShowUser(repository.findById(username).get());
    }
    
}
