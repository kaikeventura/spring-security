package com.kaikeventura.spring_security.controller;

import com.kaikeventura.spring_security.dto.LoginDTO;
import com.kaikeventura.spring_security.dto.TokenDTO;
import com.kaikeventura.spring_security.service.TokenService;
import com.kaikeventura.spring_security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("signup")
    public ResponseEntity<Void> signup(@RequestBody @Validated final LoginDTO loginDTO) {
        userService.signup(loginDTO);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("signin")
    public ResponseEntity<TokenDTO> signin(@RequestBody @Validated final LoginDTO loginDTO) {
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        var authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        var token = tokenService.generateToken(authentication);

        return ResponseEntity.ok(new TokenDTO(token));
    }

    @GetMapping("common-admin")
    public ResponseEntity<Void> commonAdmin() {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("common")
    public ResponseEntity<Void> common() {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("admin")
    public ResponseEntity<Void> admin() {
        return ResponseEntity.noContent().build();
    }
}
