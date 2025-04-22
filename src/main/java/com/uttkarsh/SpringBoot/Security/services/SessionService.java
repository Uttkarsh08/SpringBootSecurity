package com.uttkarsh.SpringBoot.Security.services;

import com.uttkarsh.SpringBoot.Security.entities.SessionEntity;
import com.uttkarsh.SpringBoot.Security.entities.UserEntity;
import com.uttkarsh.SpringBoot.Security.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final int SESSION_LIMIT = 2;

    public void generateSession(UserEntity userEntity, String refreshToken){
        List<SessionEntity> userSessions = sessionRepository.findByUser(userEntity);
        if(userSessions.size() == SESSION_LIMIT){
            userSessions.sort(Comparator.comparing(SessionEntity::getLastUsedAt));

            SessionEntity leastRecentlyUsedSession = userSessions.getFirst();
            sessionRepository.delete(leastRecentlyUsedSession);
        }

        SessionEntity newSession = SessionEntity.builder()
                .user(userEntity)
                .refreshToken(refreshToken)
                .build();

        sessionRepository.save(newSession);
    }

    public void validateSession(String refreshToken){
        SessionEntity userSession = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new SessionAuthenticationException("No session found"));
        userSession.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(userSession);
    }
}
