package io.papayankey.taskman.user;

import io.papayankey.taskman.exception.UserExistException;
import io.papayankey.taskman.security.jwt.JWTService;
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
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JWTService jwtService;
    private UserMapper userMapper;

    public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(userRegisterRequest.getUsername());

        if (optionalUser.isPresent()) {
            throw new UserExistException(userRegisterRequest.getUsername(), userRegisterRequest.getEmail());
        }

        UserEntity userEntity = userRepository.save(userMapper.toUserEntity(userRegisterRequest));
        return UserRegisterResponse.builder()
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .build();
    }

    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(), userLoginRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            UserEntity userEntity = (UserEntity) (authentication.getPrincipal());

            return UserLoginResponse.builder()
                    .username(userLoginRequest.getUsername())
                    .email(userEntity.getEmail())
                    .token(jwtService.createToken(userLoginRequest))
                    .build();
        } catch (AuthenticationException exception) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}

