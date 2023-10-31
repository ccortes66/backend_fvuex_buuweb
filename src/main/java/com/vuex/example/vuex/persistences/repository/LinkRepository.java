package com.vuex.example.vuex.persistences.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.vuex.example.vuex.persistences.document.LinkDocument;
import com.vuex.example.vuex.persistences.document.UserDocument;

public interface LinkRepository extends CrudRepository<LinkDocument,String>
{
    Optional<Iterable<LinkDocument>> findAllByDocument(UserDocument document);
    Optional<LinkDocument> findBy_idAndDocument(String id, UserDocument document);    
    Optional<LinkDocument> findByShortLink(String shortLink);
} 
