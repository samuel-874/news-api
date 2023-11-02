package com.javaMadeEasy.Entertainment.authentication.AUth;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthenticationRequest {
    private String email;
    private  String password;

    public AuthenticationRequest() {

    }

        public AuthenticationRequest(String email, String password) {
        this.email = email;
        this.password =password;
    }
}
