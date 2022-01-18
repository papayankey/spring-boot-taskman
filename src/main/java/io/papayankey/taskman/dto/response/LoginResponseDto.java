package io.papayankey.taskman.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponseDto {

    private String username;
    private String token;

}
