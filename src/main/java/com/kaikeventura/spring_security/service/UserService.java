package com.kaikeventura.spring_security.service;

import com.kaikeventura.spring_security.dto.LoginDTO;
import com.kaikeventura.spring_security.entity.User;
import com.kaikeventura.spring_security.repository.RoleRepository;
import com.kaikeventura.spring_security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void signup(final LoginDTO loginDTO) {
        var roles = roleRepository.findById(1);
        userRepository.save(new User(loginDTO.getEmail(), this.encodePassword(loginDTO.getPassword()), Collections.singleton(roles.get())));
    }

    private String encodePassword(final String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
