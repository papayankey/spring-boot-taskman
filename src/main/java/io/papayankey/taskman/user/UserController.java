package io.papayankey.taskman.user;

import io.papayankey.taskman.util.CustomServerResponse;
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
    private UserServiceImpl userServiceImpl;

    @PostMapping(path = "/register")
    public ResponseEntity<CustomServerResponse> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        UserAuthenticationResponse userAuthenticationResponse = userServiceImpl.register(userRegisterRequest);
        CustomServerResponse responseData = CustomServerResponse.builder()
                .data(userAuthenticationResponse)
                .status(HttpStatus.CREATED.value())
                .title(HttpStatus.CREATED.name())
                .detail("Registration successful")
                .build();
        return ResponseHandler.create(responseData, HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<CustomServerResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        UserAuthenticationResponse userAuthenticationResponse = userServiceImpl.login(userLoginRequest);
        CustomServerResponse responseData = CustomServerResponse.builder()
                .status(HttpStatus.OK.value())
                .title(HttpStatus.OK.name())
                .detail("Login successful")
                .data(userAuthenticationResponse)
                .build();
        return ResponseHandler.create(responseData, HttpStatus.OK);
    }
}
