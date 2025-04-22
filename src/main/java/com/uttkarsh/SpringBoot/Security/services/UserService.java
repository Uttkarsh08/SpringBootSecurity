package com.uttkarsh.SpringBoot.Security.services;

import com.uttkarsh.SpringBoot.Security.dto.SignUpDTO;
import com.uttkarsh.SpringBoot.Security.dto.UserDTO;
import com.uttkarsh.SpringBoot.Security.entities.UserEntity;
import com.uttkarsh.SpringBoot.Security.exceptions.ResourceNotFoundException;
import com.uttkarsh.SpringBoot.Security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(()-> new BadCredentialsException("UserEntity with email:"+username+"not found"));
    }

    public UserDTO signUp(SignUpDTO signUpDTO) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(signUpDTO.getEmail());
        if(userEntity.isPresent()){
            throw new BadCredentialsException("User already exist");
        }

        UserEntity toSave = mapper.map(signUpDTO, UserEntity.class);
        toSave.setPassword(passwordEncoder.encode(toSave.getPassword()));

        UserEntity savedUser = userRepository.save(toSave);
        return mapper.map(savedUser, UserDTO.class);
    }

    public UserEntity findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not Exist"));
    }

    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public UserEntity saveUser(UserEntity newUser) {
        return userRepository.save(newUser);
    }
}
