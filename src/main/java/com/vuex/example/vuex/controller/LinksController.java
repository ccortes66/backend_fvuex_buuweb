package com.vuex.example.vuex.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vuex.example.vuex.persistences.DTO.Links.RegisterLink;
import com.vuex.example.vuex.persistences.DTO.Links.ShowLinks;
import com.vuex.example.vuex.persistences.DTO.Links.UpdateLink;
import com.vuex.example.vuex.service.LinkService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/links")
@RequiredArgsConstructor
public class LinksController 
{   
    private final LinkService service;
   
    @GetMapping
    public ResponseEntity<List<ShowLinks>> getAllLinks()
    {   String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(service.getAllLinks(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowLinks> getLink(@PathVariable("id") String id)
    {  
       String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
       return ResponseEntity.ok(service.getLinkById(id,user));
    }

   

    @PostMapping
    public ResponseEntity<ShowLinks> createLink(@RequestBody @Valid RegisterLink registerLink)
    {   
        String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(service.createLink(registerLink,user));
    }

    @PatchMapping
    public ResponseEntity<ShowLinks> updateLink(@RequestBody @Valid UpdateLink updateLink)
    {   
        String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(service.updateLink(updateLink,user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeLink(@PathVariable("id") String id)
    {   
        String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        service.removeLink(id, user);
        return ResponseEntity.noContent().build();
    }
}
