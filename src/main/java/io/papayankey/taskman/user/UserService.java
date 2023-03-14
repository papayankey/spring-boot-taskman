package io.papayankey.taskman.user;

public interface UserService {
    UserAuthenticationResponse register(UserRegisterRequest userRegisterRequest);

    UserAuthenticationResponse login(UserLoginRequest userLoginRequest);
}
