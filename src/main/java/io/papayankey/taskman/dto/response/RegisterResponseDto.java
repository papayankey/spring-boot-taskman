package io.papayankey.taskman.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisterResponseDto {

    private String username;
    private String email;

}
