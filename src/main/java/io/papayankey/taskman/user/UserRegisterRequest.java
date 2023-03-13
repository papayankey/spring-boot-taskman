package io.papayankey.taskman.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRegisterRequest {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
