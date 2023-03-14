package io.papayankey.taskman.user;

public interface UserService {
    UserRegisterResponse register(UserRegisterRequest userRegisterRequest);

    UserLoginResponse login(UserLoginRequest userLoginRequest);
}
