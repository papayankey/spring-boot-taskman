package io.papayankey.taskman.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterResponse {
    private String username;
    private String email;
}
