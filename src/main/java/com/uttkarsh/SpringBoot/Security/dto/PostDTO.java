package com.uttkarsh.SpringBoot.Security.dto;

import com.uttkarsh.SpringBoot.Security.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Long id;
    private String title;
    private String description;

    private UserDTO author;
}
