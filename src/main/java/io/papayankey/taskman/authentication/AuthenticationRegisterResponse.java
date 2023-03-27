package io.papayankey.taskman.authentication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRegisterResponse {
    private String username;
    private String email;
}
