package io.papayankey.taskman.authentication;

import io.papayankey.taskman.util.CustomServerResponse;
import io.papayankey.taskman.util.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationServiceImpl authenticationServiceImpl;

    @PostMapping(path = "/register")
    public ResponseEntity<CustomServerResponse> register(@RequestBody AuthenticationRegisterRequest authenticationRegisterRequest) {
        AuthenticationRegisterResponse authenticationRegisterResponse = authenticationServiceImpl.register(authenticationRegisterRequest);
        CustomServerResponse responseData = CustomServerResponse.builder()
                .data(authenticationRegisterResponse)
                .status(HttpStatus.CREATED.value())
                .title(HttpStatus.CREATED.name())
                .detail("Registration successful")
                .build();
        return ResponseHandler.create(responseData, HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<CustomServerResponse> login(@RequestBody AuthenticationLoginRequest authenticationLoginRequest) {
        AuthenticationLoginResponse authenticationLoginResponse = authenticationServiceImpl.login(authenticationLoginRequest);
        CustomServerResponse responseData = CustomServerResponse.builder()
                .status(HttpStatus.OK.value())
                .title(HttpStatus.OK.name())
                .detail("Login successful")
                .data(authenticationLoginResponse)
                .build();
        return ResponseHandler.create(responseData, HttpStatus.OK);
    }
}
