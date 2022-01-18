package io.papayankey.taskman.security;

import io.papayankey.taskman.model.UserEntity;
import io.papayankey.taskman.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) throw new UsernameNotFoundException("User not found with name: " + username);
        else {
            UserEntity entity = optionalUser.get();
            return new org.springframework.security.core.userdetails.User(entity.getEmail(), entity.getPassword(), new ArrayList<>());
        }
    }
}
