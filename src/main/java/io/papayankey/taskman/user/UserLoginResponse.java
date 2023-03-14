package io.papayankey.taskman.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginResponse {
    private String username;
    private String email;
    private String token;
}
