package com.rokib.jwt.application.usecase.impl;

import com.rokib.jwt.application.repository.IUserRepository;
import com.rokib.jwt.domain.AllEnums;
import com.rokib.jwt.domain.dto.AuthenticationRequest;
import com.rokib.jwt.domain.dto.AuthenticationResponse;
import com.rokib.jwt.domain.dto.RegisterRequest;
import com.rokib.jwt.domain.entities.User;
import com.rokib.jwt.presentation.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final IUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
       com.rokib.jwt.domain.entities.User  user = com.rokib.jwt.domain.entities.User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(String.valueOf(AllEnums.Role.USER))
                .build();
        repository.save(user);
        var jwtToken = jwtUtils.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        com.rokib.jwt.domain.entities.User user = (User) repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtUtils.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
