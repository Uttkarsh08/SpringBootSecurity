package com.uttkarsh.SpringBoot.Security.repositories;

import com.uttkarsh.SpringBoot.Security.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
