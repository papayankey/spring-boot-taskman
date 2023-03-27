package io.papayankey.taskman.authentication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationLoginResponse {
    private String username;
    private String email;
    private String token;
}
