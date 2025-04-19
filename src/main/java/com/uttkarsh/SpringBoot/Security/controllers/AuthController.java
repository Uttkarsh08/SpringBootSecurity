package com.uttkarsh.SpringBoot.Security.controllers;


import com.uttkarsh.SpringBoot.Security.advice.ApiResponse;
import com.uttkarsh.SpringBoot.Security.dto.LoginDTO;
import com.uttkarsh.SpringBoot.Security.dto.SignUpDTO;
import com.uttkarsh.SpringBoot.Security.dto.UserDTO;
import com.uttkarsh.SpringBoot.Security.services.AuthService;
import com.uttkarsh.SpringBoot.Security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO){
        UserDTO userDTO = userService.signUp(signUpDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> logIn(@RequestBody LoginDTO loginDTO){
        String token = authService.logIn(loginDTO);
        return ResponseEntity.ok(new ApiResponse<>(token));
    }

}
