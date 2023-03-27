package io.papayankey.taskman.user;

import io.papayankey.taskman.authentication.AuthenticationRegisterRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public abstract class UserMapper {
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(authenticationRegisterRequest.getPassword()))")
    public abstract UserEntity toUserEntity(AuthenticationRegisterRequest authenticationRegisterRequest);
}
