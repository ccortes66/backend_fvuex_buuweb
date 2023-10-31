package com.vuex.example.vuex.persistences.DTO.Links;


import com.vuex.example.vuex.persistences.document.LinkDocument;


public record ShowLinks(
   String id, 
   String longLink,
   String shortLink,  
   String user
) 
{
    public ShowLinks(LinkDocument document)
    {
        this(document.get_id(),
             document.getLongLink(), 
             document.getShortLink(), 
             document.getDocument().getEmail());
    }
}
