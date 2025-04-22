package com.uttkarsh.SpringBoot.Security.services;

import com.uttkarsh.SpringBoot.Security.dto.LoginDTO;
import com.uttkarsh.SpringBoot.Security.dto.LoginResponseDTO;
import com.uttkarsh.SpringBoot.Security.entities.UserEntity;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final SessionService sessionService;

    public LoginResponseDTO logIn(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(userEntity);
        String refreshToken = jwtService.generateRefreshToken(userEntity);

        sessionService.generateSession(userEntity, refreshToken);

        return new LoginResponseDTO(userEntity.getId(), accessToken, refreshToken);
    }

    public LoginResponseDTO refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdWithJwtToken(refreshToken);
        sessionService.validateSession(refreshToken);

        UserEntity userEntity = userService.findUserById(userId);
        String accessToken = jwtService.generateAccessToken(userEntity);

        return new LoginResponseDTO(userEntity.getId(), accessToken, refreshToken);
    }
}
