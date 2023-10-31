package com.vuex.example.vuex.persistences.document;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.vuex.example.vuex.persistences.DTO.Links.RegisterLink;
import com.vuex.example.vuex.persistences.DTO.Links.UpdateLink;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document
public class LinkDocument 
{  
   @Id  
   @Setter(AccessLevel.NONE)
   private String _id;

   private String longLink ;

   @Indexed(unique = true)
   private String shortLink;  
   
   @DocumentReference(lazy = true)
   private UserDocument document;

   public LinkDocument(RegisterLink registerLink)
   {
      this.longLink = registerLink.longLink().trim();
      this.shortLink = NanoIdGenerator.generateNanoId(10);
      
   }

   public LinkDocument updateLink(UpdateLink updateLink)
   {
      this.longLink = updateLink.longLink();
      return this;        
   }


}

class NanoIdGenerator {
    
  private static String digits = "0123456789";
  private static String asciiLowercase = "abcdefghijklmnopqrstuvwxyz";
  private static String asciiUppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  private static Map<Integer,String> data =  new HashMap<>(); 
  private static Random choice = new Random();

   static {
      
      data.put(0, asciiLowercase);
      data.put(1, asciiUppercase);
      data.put(2, digits);

   }

   public static String generateNanoId(Integer largo)
   {  
      StringBuilder builder = new StringBuilder();
      for(int i=0; i<largo; i++)
      {
          String value  = data.get(choice.nextInt(0,3));
          builder.append(value.charAt(choice.nextInt(value.length())));  
      }
      return builder.toString();
   }
   
  
}
   
   
