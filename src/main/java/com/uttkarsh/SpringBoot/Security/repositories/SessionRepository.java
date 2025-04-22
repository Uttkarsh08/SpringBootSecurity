package com.uttkarsh.SpringBoot.Security.repositories;

import com.uttkarsh.SpringBoot.Security.entities.SessionEntity;
import com.uttkarsh.SpringBoot.Security.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    List<SessionEntity> findByUser(UserEntity userEntity);

    Optional<SessionEntity> findByRefreshToken(String refreshToken);
}
