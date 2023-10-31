package com.vuex.example.vuex.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.vuex.example.vuex.persistences.DTO.Links.RegisterLink;
import com.vuex.example.vuex.persistences.DTO.Links.ShowLinks;
import com.vuex.example.vuex.persistences.DTO.Links.UpdateLink;
import com.vuex.example.vuex.persistences.document.LinkDocument;
import com.vuex.example.vuex.persistences.repository.LinkRepository;
import com.vuex.example.vuex.persistences.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor 
public class LinkService 
{ 
   private final LinkRepository repository;
   private final UserRepository userRepository; 
   private final RestTemplate restTemplate;
   

   public List<ShowLinks> getAllLinks(String email)
   {  
      List<LinkDocument> data = new ArrayList<>();
      repository.findAllByDocument(userRepository.findById(email).get())
       .ifPresent((result) ->result.forEach(data::add));
       return data.stream().map(ShowLinks::new).toList();
   }   

   public ShowLinks getLinkById(String id,String email)
   {
      return new ShowLinks(repository.findBy_idAndDocument(id, userRepository.findById(email).get()).get());
   }

   public RegisterLink getLinkByShortLink(String shortLink)
   {  
      return new RegisterLink(repository.findByShortLink(shortLink).get().getLongLink());
   }
   
   @Transactional
   public ShowLinks createLink(RegisterLink registerLink,
                               String email)
   {  
      restTemplate.getForObject(registerLink.longLink(), String.class);

      LinkDocument linkDocument = new LinkDocument(registerLink);
      linkDocument.setDocument(userRepository.findById(email).get());
      repository.save(linkDocument);
      return new ShowLinks(linkDocument);
   }

   @Transactional
   public ShowLinks updateLink(UpdateLink updateLink,
                               String email)
   {  
      
      Optional<LinkDocument> document = repository.findBy_idAndDocument(updateLink.id(), 
                                                                        userRepository.findById(email).get());
      document.ifPresent(data ->{
         restTemplate.getForObject(updateLink.longLink(),String.class);
         repository.save(data.updateLink(updateLink));
      });
      return new ShowLinks(document.get());
   }

   

   @Transactional
   public void removeLink(String id,String email)
   {
      repository.delete(repository.findBy_idAndDocument(id, userRepository.findById(email).get()).get());
   }
}
