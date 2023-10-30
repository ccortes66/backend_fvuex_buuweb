package com.vuex.example.vuex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/render")
public class HelloController 
{
   @GetMapping("/home")
   public String hello(Model mode, HttpServletRequest request)
   {  
   
      return "index";
   }   
   
   @GetMapping("/me")
   public String me(Model mode, HttpServletRequest request)
   {  
      return "me";
   } 
}
