package com.kaikeventura.spring_security.service;

import com.kaikeventura.spring_security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        var optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isPresent()) {
            return optionalUser.get();
        }

        throw new UsernameNotFoundException("User not found");
    }
}
