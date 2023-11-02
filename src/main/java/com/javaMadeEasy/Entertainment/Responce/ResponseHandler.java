package com.javaMadeEasy.Entertainment.Responce;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseHandler {

public static ResponseEntity<Object> responseBuilder(String message, int statusCode, Object responseObject) {

    Map<String, Object> responseBody = new LinkedHashMap<>();
    responseBody.put("body", responseObject);

    Map<String, Object> response = new LinkedHashMap<>();
    response.put("statusCode", statusCode);
    response.put("statusCodeValue", HttpStatus.valueOf(statusCode));
    response.put("data", responseBody);
    responseBody.put("message", message);


    return ResponseEntity.status(HttpStatus.valueOf(statusCode)).body(response);
}









}
