package com.vuex.example.vuex.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.vuex.example.vuex.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MiddlewareFiler extends OncePerRequestFilter
{
    public final TokenService service;
    public final UserDetailsService userService;
    
    @Autowired
    public MiddlewareFiler(@Lazy TokenService service,
                           @Lazy UserDetailsService userService) 
    {
        this.service = service;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain)
                                    throws ServletException, IOException 
    {
          Optional<String> headerAutorization = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION));
          headerAutorization.ifPresent((data) ->{
              
              if(isTokenBearer(data) && service.verifiPrincipalToken(data.substring(7)))
              {
                String email = service.getPrincipalhEmail(data.substring(7));
                User user = (User) userService.loadUserByUsername(email);
                var auth = new UsernamePasswordAuthenticationToken(user.getUsername(),
                                                                   user.getPassword(),
                                                                   user.getAuthorities());

              SecurityContextHolder.getContext().setAuthentication(auth);
              }
              
          });

          filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) 
    {
        return request.getRequestURI().contains("auth") ||
               request.getRequestURI().contains("home")  ;
    } 
    
    private Boolean isTokenBearer(String header)
    {
        return header.contains("Bearer");
    }


    
    
}
