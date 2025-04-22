package com.uttkarsh.SpringBoot.Security.dto;

import com.uttkarsh.SpringBoot.Security.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDTO {

    private String email;
    private String password;
    private String name;
    private Set<Role> roles;
}
