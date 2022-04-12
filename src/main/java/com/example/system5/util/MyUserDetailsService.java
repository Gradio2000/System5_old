package com.example.system5.util;

import com.example.system5.model.User;
import com.example.system5.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> authUser = userRepository.findByEmail(email);
        return new AuthUser(authUser.orElseThrow(
                () -> new UsernameNotFoundException("User '" + email + "' was not found")));
    }
}