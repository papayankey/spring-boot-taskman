package io.papayankey.taskman.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisterResponseDto {

    private String username;
    private String email;

}
