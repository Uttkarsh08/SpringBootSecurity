package com.uttkarsh.SpringBoot.Security.utils;

import com.uttkarsh.SpringBoot.Security.dto.PostDTO;
import com.uttkarsh.SpringBoot.Security.entities.PostEntity;
import com.uttkarsh.SpringBoot.Security.entities.UserEntity;
import com.uttkarsh.SpringBoot.Security.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final PostService postService;

    public boolean isOwnerOfPost(Long postId){
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDTO post = postService.getPostById(postId);

        return post.getAuthor().getId().equals(user.getId());

    }
}
