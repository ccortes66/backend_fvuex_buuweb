package com.vuex.example.vuex.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import com.vuex.example.vuex.persistences.DTO.Links.RegisterLink;
import com.vuex.example.vuex.service.LinkService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class RedirectController 
{  
    private final LinkService service;
    
    /* 
        ejemplo blacj redirect
        @GetMapping("/{shortLink}")
        public RedirectView getLongLink(@PathVariable("shortLink") String shortLink)
        {    
            return new RedirectView(service.getLinkByShortLink(shortLink).longLink());
        } 
    */ 
     
        @GetMapping("/{shortLink}")
        public ResponseEntity<RegisterLink> getLongLink(@PathVariable("shortLink") String shortLink)
        {    
            return ResponseEntity.ok(service.getLinkByShortLink(shortLink));
        }

}
