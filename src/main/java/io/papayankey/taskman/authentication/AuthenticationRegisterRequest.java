package io.papayankey.taskman.authentication;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthenticationRegisterRequest {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
