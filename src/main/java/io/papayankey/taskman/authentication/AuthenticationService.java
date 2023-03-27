package io.papayankey.taskman.authentication;

public interface AuthenticationService {
    AuthenticationRegisterResponse register(AuthenticationRegisterRequest authenticationRegisterRequest);

    AuthenticationLoginResponse login(AuthenticationLoginRequest authenticationLoginRequest);
}
