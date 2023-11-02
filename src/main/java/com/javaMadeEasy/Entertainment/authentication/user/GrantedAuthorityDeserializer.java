//package com.javaMadeEasy.Entertainment.authentication.user;
//
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import com.fasterxml.jackson.databind.JsonNode;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import java.io.IOException;
//
//public class GrantedAuthorityDeserializer extends JsonDeserializer<GrantedAuthority> {
//
//    @Override
//    public GrantedAuthority deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
//        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
//        String authority = node.get("authority").asText();
//
//            if (authority.equals("ADMIN")) {
//            return new SimpleGrantedAuthority("ADMIN");
//        } else if (authority.equals("USER")) {
//            return new SimpleGrantedAuthority("USER");
//        } else {
//            throw new IllegalArgumentException("Invalid authority value: " + authority);
//        }
//    }
//}
//
//
