package io.papayankey.taskman.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserAuthenticationResponse {
    private String username;
    private String email;
    private String token;
}
