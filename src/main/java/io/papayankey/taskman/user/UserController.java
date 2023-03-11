package io.papayankey.taskman.user;

import io.papayankey.taskman.user.dto.request.LoginRequestDto;
import io.papayankey.taskman.user.dto.request.RegisterRequestDto;
import io.papayankey.taskman.user.dto.response.LoginResponseDto;
import io.papayankey.taskman.user.dto.response.RegisterResponseDto;
import io.papayankey.taskman.util.CustomResponse;
import io.papayankey.taskman.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/register")
    public ResponseEntity<CustomResponse> register(@RequestBody RegisterRequestDto registerRequestDto) {
        RegisterResponseDto registerResponseDto = userService.register(registerRequestDto);
        CustomResponse customResponse = CustomResponse.builder()
                .data(registerResponseDto)
                .status(HttpStatus.CREATED.value())
                .message("Registration successful")
                .build();
        return ResponseHandler.create(customResponse, HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<CustomResponse> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = userService.login(loginRequestDto);
        CustomResponse customResponse = CustomResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Login successful")
                .data(loginResponseDto)
                .build();
        return ResponseHandler.create(customResponse, HttpStatus.OK);
    }
}
