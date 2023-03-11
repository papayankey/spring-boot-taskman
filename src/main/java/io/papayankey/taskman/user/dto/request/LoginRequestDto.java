package io.papayankey.taskman.user.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginRequestDto {

    private String username;
    private String password;

}
