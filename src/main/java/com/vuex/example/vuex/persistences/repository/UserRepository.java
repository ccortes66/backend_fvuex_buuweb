package com.vuex.example.vuex.persistences.repository;

import org.springframework.data.repository.CrudRepository;

import com.vuex.example.vuex.persistences.document.UserDocument;

public interface UserRepository extends CrudRepository<UserDocument,String> 
{

    
}
