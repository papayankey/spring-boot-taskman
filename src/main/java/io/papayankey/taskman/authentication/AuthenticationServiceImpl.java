package io.papayankey.taskman.authentication;

import io.papayankey.taskman.exception.UserExistException;
import io.papayankey.taskman.security.jwt.JWTService;
import io.papayankey.taskman.user.UserEntity;
import io.papayankey.taskman.user.UserMapper;
import io.papayankey.taskman.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JWTService jwtService;
    private UserMapper userMapper;

    public AuthenticationRegisterResponse register(AuthenticationRegisterRequest authenticationRegisterRequest) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(authenticationRegisterRequest.getUsername());

        if (optionalUser.isPresent()) {
            throw new UserExistException(authenticationRegisterRequest.getUsername(), authenticationRegisterRequest.getEmail());
        }

        UserEntity userEntity = userRepository.save(userMapper.toUserEntity(authenticationRegisterRequest));
        return AuthenticationRegisterResponse.builder()
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .build();
    }

    public AuthenticationLoginResponse login(AuthenticationLoginRequest authenticationLoginRequest) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authenticationLoginRequest.getUsername(), authenticationLoginRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            UserEntity userEntity = (UserEntity) (authentication.getPrincipal());

            return AuthenticationLoginResponse.builder()
                    .username(authenticationLoginRequest.getUsername())
                    .email(userEntity.getEmail())
                    .token(jwtService.createToken(authenticationLoginRequest))
                    .build();
        } catch (AuthenticationException exception) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}

