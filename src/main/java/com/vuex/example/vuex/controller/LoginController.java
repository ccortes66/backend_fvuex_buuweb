package com.vuex.example.vuex.controller;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vuex.example.vuex.persistences.DTO.User.LoginUser;
import com.vuex.example.vuex.persistences.DTO.User.RegisterUser;
import com.vuex.example.vuex.persistences.DTO.User.ShowUser;
import com.vuex.example.vuex.persistences.DTO.User.TokenResponse;
import com.vuex.example.vuex.service.TokenService;
import com.vuex.example.vuex.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController 
{   
    private final UserService service;
    private final TokenService tokenService;
    

    @PostMapping("/register")
    public ResponseEntity<ShowUser> registerUser(@RequestBody @Valid RegisterUser user)
    {   
        return ResponseEntity.ok(service.registerUser(user));
    } 

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody @Valid LoginUser user)
    {   
        
        ResponseCookie cookie = ResponseCookie.from("refeshToken", tokenService.createRefreshtoken(user))
                                .path("/")
                                .domain("localhost")
                                .secure(false)
                                .httpOnly(true)
                                .maxAge(Duration.ofDays(30))
                                .build();
        return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, cookie.toString())
                             .body("Refresh Generate");
    }

    @GetMapping("/refesh")
    public ResponseEntity<TokenResponse> woAmI(@CookieValue(name = "refeshToken") String token)
    {
        return ResponseEntity.ok(tokenService.createPrincipaltoken(token));
        
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logoutRefreshToken()
    {   
        ResponseCookie deleteCookie = ResponseCookie.from("refeshToken", null) 
                                      .build(); 
        return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                             .build();
        
    }
}
