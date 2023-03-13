package io.papayankey.taskman.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserLoginRequest {
    private String username;
    private String password;
}
