package com.vuex.example.vuex.config;

import java.net.UnknownHostException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.mongodb.MongoWriteException;

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

   @ExceptionHandler(MongoWriteException.class)
   public ResponseEntity<ErrorResponses> onDuplicateKey(MongoWriteException ex)
   {
      return ResponseEntity.badRequest().body(new ErrorResponses("400", ex.getMessage())); 
   } 

   @ExceptionHandler(UnknownHostException.class)
   public ResponseEntity<ErrorResponses> onTimeOut404(UnknownHostException ex)
   {
     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponses("404", ex.getMessage()));
   }

   @ExceptionHandler(NoSuchElementException.class)
   public ResponseEntity<ErrorResponses> onNotValue404(NoSuchElementException ex)
   {
     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponses("404", ex.getMessage()));
   }
   
   @ExceptionHandler(InternalAuthenticationServiceException.class)
   public ResponseEntity<ErrorResponses> onBadLogin403()
   {
       return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponses("401", "Email o password Erroneos"));
   } 

   @ExceptionHandler(MissingRequestCookieException.class)
   public ResponseEntity<ErrorResponses> onMissingCookie400()
   {
      return ResponseEntity.badRequest().body(new ErrorResponses("400", "missing cookie"));
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



