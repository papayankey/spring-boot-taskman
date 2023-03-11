package io.papayankey.taskman.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.papayankey.taskman.user.dto.request.LoginRequestDto;
import io.papayankey.taskman.user.dto.request.RegisterRequestDto;
import io.papayankey.taskman.user.dto.response.LoginResponseDto;
import io.papayankey.taskman.user.dto.response.RegisterResponseDto;
import io.papayankey.taskman.exception.UserExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static io.papayankey.taskman.util.Constant.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

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
            String token = createToken(loginRequestDto);

            return LoginResponseDto.builder()
                    .username(loginRequestDto.getUsername())
                    .token(token)
                    .build();
        } catch (AuthenticationException exception) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    private String createToken(LoginRequestDto loginRequestDto) {
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
        return JWT.create()
                .withSubject(loginRequestDto.getUsername())
                .withIssuedAt(JWT_ISSUED)
                .withExpiresAt(JWT_EXPIRATION)
                .sign(algorithm);
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

