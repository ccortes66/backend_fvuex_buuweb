package com.vuex.example.vuex.config;



import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig 
{  
   
   private final MiddlewareFiler filter;

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception
   {  
      security
         .httpBasic(AbstractHttpConfigurer::disable)
         .csrf(AbstractHttpConfigurer::disable)
         .sessionManagement((manager)->manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
         .cors(Customizer.withDefaults())
         .authorizeHttpRequests((request) -> 
                                           request 
                                                   .requestMatchers("/auth/**").permitAll()
                                                   .requestMatchers("/render/**").permitAll()
                                                   .requestMatchers("/user/**").permitAll()
                                                   .requestMatchers("/doc/**").permitAll()
                                                   .requestMatchers("/v3/**").permitAll()
                                                   .anyRequest()
                                                   .authenticated())

         .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);                                          
                              
         
      return security.build();
   }
   
   @Bean
   public PasswordEncoder passwordEncoder()
   {
     return new BCryptPasswordEncoder();
   }

   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception
   {
      return configuration.getAuthenticationManager();
   }

   @Bean
   public CorsConfigurationSource corsConfigurationSource()
   {
      CorsConfiguration configuration = new CorsConfiguration();
      //configuration.setAllowedOrigins(List.of("http://localhost:9000/","localhost")); 
      configuration.setAllowedOrigins(List.of("http://localhost:9000/","https://shortedlinkbuueb.onrender.com","https://654ced4edbcd355a36e61716--jolly-wisp-b0b44b.netlify.app/")); 
      configuration.setAllowedMethods(List.of("*"));
      configuration.setAllowedHeaders(List.of("*"));
      configuration.setAllowCredentials(true);
      var source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", configuration);
      return source;
   }




}
