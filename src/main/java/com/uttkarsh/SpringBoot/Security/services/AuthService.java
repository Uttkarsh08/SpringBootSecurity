package com.uttkarsh.SpringBoot.Security.services;

import com.uttkarsh.SpringBoot.Security.dto.LoginDTO;
import com.uttkarsh.SpringBoot.Security.entities.UserEntity;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String logIn(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );
        if(authentication.isAuthenticated()){
            UserEntity userEntity = (UserEntity) authentication.getPrincipal();

            String token = jwtService.generateJwtToken(userEntity);
            return token;
        }else {
            throw new BadCredentialsException("Wrong Username or Password");
        }


    }
}
