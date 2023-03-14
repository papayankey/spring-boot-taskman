package io.papayankey.taskman.user;

import io.papayankey.taskman.exception.UserExistException;
import io.papayankey.taskman.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserMapper userMapper;

    public UserAuthenticationResponse register(UserRegisterRequest userRegisterRequest) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(userRegisterRequest.getUsername());

        if (optionalUser.isPresent()) {
            throw new UserExistException(userRegisterRequest.getUsername(), userRegisterRequest.getEmail());
        }

        UserEntity userEntity = userRepository.save(userMapper.toUserEntity(userRegisterRequest));
        return UserAuthenticationResponse.builder()
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .build();
    }

    public UserAuthenticationResponse login(UserLoginRequest userLoginRequest) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(), userLoginRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            UserEntity userEntity = (UserEntity) (authentication.getPrincipal());

            return UserAuthenticationResponse.builder()
                    .username(userLoginRequest.getUsername())
                    .email(userEntity.getEmail())
                    .token(jwtUtil.createToken(userLoginRequest))
                    .build();
        } catch (AuthenticationException exception) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}

