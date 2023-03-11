package io.papayankey.taskman.user.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisterRequestDto {

   private String username;
   private String firstName;
   private String lastName;
   private String email;
   private String password;

}
