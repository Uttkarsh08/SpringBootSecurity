package com.uttkarsh.SpringBoot.Security.services;


import com.uttkarsh.SpringBoot.Security.dto.PostDTO;

import java.util.List;

public interface PostService {

    List<PostDTO> getAllPosts();

    PostDTO createNewPost(PostDTO inputPost);

    PostDTO getPostById(Long postId);
}
