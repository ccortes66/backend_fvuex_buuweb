package com.vuex.example.vuex.config;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.JWTVerificationException;

@RestControllerAdvice
public class CustomHandlerException 
{
   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<List<ErrorResponses>> onBadRequest400(MethodArgumentNotValidException exception)
   {  
     List<ErrorResponses> error = exception.getFieldErrors().stream().map(ErrorResponses::new).toList();
     return ResponseEntity.badRequest().body(error);
   }
   
   @ExceptionHandler(IllegalArgumentException.class)
   public ResponseEntity<ErrorResponses> onSingleBadRequest400(IllegalArgumentException exception)
   { 

     return ResponseEntity.badRequest().body(new ErrorResponses("400",exception.getMessage()));
   }

   @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<ErrorResponses> onVerificationToken400(JWTVerificationException exception)
    {
      return ResponseEntity.badRequest().body(new ErrorResponses("400",exception.getMessage()));
    }

}


record ErrorResponses(String field, 
                     String error) 
{
    public ErrorResponses(FieldError error)
    {
        this(error.getField(), error.getDefaultMessage());
    }
}



