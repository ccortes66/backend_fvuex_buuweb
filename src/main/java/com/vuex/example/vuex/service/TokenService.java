package com.vuex.example.vuex.service;

import java.time.Duration;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.vuex.example.vuex.persistences.DTO.User.CreateToken;
import com.vuex.example.vuex.persistences.DTO.User.LoginUser;
import com.vuex.example.vuex.persistences.DTO.User.TokenResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService 
{  
   private Algorithm principalAlgorithm = Algorithm.HMAC256("secret-principal");
   private Algorithm refeshAlgorithm = Algorithm.HMAC256("secret-refersh");
   private final AuthenticationManager manager;

   public String createRefreshtoken(LoginUser user)
   {  
      var login = new UsernamePasswordAuthenticationToken(user.email(),user.password()); 
      manager.authenticate(login);
      return TokenHelper.createToken(new CreateToken(user.email(), 
                                                     refeshAlgorithm, 
                                                     Duration.ofMinutes(15), 
                                                     "refersh token"));
   }

   public Boolean verifiRefresToken(String token)
   {
      return TokenHelper.verifyToken(token, refeshAlgorithm);
   }

   public String getRefeshEmail(String token)
   { 
      return TokenHelper.getUsername(token ,refeshAlgorithm);
   }

   public TokenResponse createPrincipaltoken(String outToken)
   {  
      verifiRefresToken(outToken);
      String token = TokenHelper.createToken(new CreateToken(getRefeshEmail(outToken), 
                                                             principalAlgorithm, 
                                                             Duration.ofMinutes(15), 
                                                     "principal token"));

      return new TokenResponse(token, Duration.ofMinutes(15).toMillis());
   }

   public String createPrincipaltokenFilter(String outToken)
   {  
     verifiRefresToken(outToken); 
     return TokenHelper.createToken(new CreateToken(getRefeshEmail(outToken), 
                                                    principalAlgorithm, 
                                                    Duration.ofMinutes(15), 
                                                     "principal token"));
   }

   public Boolean verifiPrincipalToken(String token)
   {
      return TokenHelper.verifyToken(token, principalAlgorithm);
   }

   public String getPrincipalhEmail(String token)
   { 
      return TokenHelper.getUsername(token ,principalAlgorithm);
   }



    
}

class TokenHelper
{
   public static String createToken(CreateToken createToken)
   {
      return JWT.create()
                       .withSubject(createToken.username())
                       .withIssuer(createToken.whitUser())
                       .withIssuedAt(new Date())
                       .withExpiresAt(new Date(System.currentTimeMillis() + createToken.duration().toMillis()))
                       .sign(createToken.algorithm());
   }

   public static Boolean verifyToken(String token, Algorithm algorithm)
   {
      try{return JWT.require(algorithm).build().verify(token) != null;}
      catch(JWTVerificationException exception){ throw new JWTVerificationException(exception.getMessage()); }
   }

   public static String getUsername(String token, Algorithm algorithm)
   {
      return JWT.require(algorithm).build().verify(token).getSubject();
   }
}
