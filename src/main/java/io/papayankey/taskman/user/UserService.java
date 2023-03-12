package io.papayankey.taskman.user;

import io.papayankey.taskman.exception.UserExistException;
import io.papayankey.taskman.user.dto.request.LoginRequestDto;
import io.papayankey.taskman.user.dto.request.RegisterRequestDto;
import io.papayankey.taskman.user.dto.response.LoginResponseDto;
import io.papayankey.taskman.user.dto.response.RegisterResponseDto;
import io.papayankey.taskman.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    public RegisterResponseDto register(RegisterRequestDto registerRequestDto) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(registerRequestDto.getUsername());
        if (optionalUser.isPresent()) throw new UserExistException(registerRequestDto.getUsername());

        UserEntity userEntity = userRepository.save(toUserEntity(registerRequestDto));
        return RegisterResponseDto.builder()
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .build();
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword());
            authenticationManager.authenticate(authenticationToken);
            String token = jwtUtil.createToken(loginRequestDto);

            return LoginResponseDto.builder()
                    .username(loginRequestDto.getUsername())
                    .token(token)
                    .build();
        } catch (AuthenticationException exception) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    private UserEntity toUserEntity(RegisterRequestDto registerRequestDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerRequestDto.getUsername());
        userEntity.setFirstName(registerRequestDto.getFirstName());
        userEntity.setLastName(registerRequestDto.getLastName());
        userEntity.setEmail(registerRequestDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        return userEntity;
    }

}

