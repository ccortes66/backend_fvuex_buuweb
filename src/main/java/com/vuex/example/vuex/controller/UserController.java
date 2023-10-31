package com.vuex.example.vuex.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vuex.example.vuex.persistences.DTO.User.ShowUser;
import com.vuex.example.vuex.service.TokenService;
import com.vuex.example.vuex.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class UserController 
{   
    private final UserService service;
    private final TokenService tokenService;

    @GetMapping("/me")
    public ResponseEntity<ShowUser> userMe(HttpServletRequest request)
    {
         String token = tokenService.getPrincipalhEmail(request.getHeader(HttpHeaders.AUTHORIZATION).substring(7));
         return ResponseEntity.ok(service.meUser(token));
    }    

}
