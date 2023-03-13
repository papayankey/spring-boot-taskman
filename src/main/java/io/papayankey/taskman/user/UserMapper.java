package io.papayankey.taskman.user;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public abstract class UserMapper {
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(userRegisterRequest.getPassword()))")
    public abstract UserEntity toUserEntity(UserRegisterRequest userRegisterRequest);
}
