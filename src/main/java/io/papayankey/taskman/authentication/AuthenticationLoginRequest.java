package io.papayankey.taskman.authentication;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthenticationLoginRequest {
    private String username;
    private String password;
}
