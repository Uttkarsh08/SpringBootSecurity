    package com.uttkarsh.SpringBoot.Security.handlers;


    import com.uttkarsh.SpringBoot.Security.entities.UserEntity;
    import com.uttkarsh.SpringBoot.Security.services.JwtService;
    import com.uttkarsh.SpringBoot.Security.services.UserService;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.Cookie;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
    import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
    import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
    import org.springframework.stereotype.Component;

    import java.io.IOException;

    @Slf4j
    @Component
    @RequiredArgsConstructor
    public class Oauth2Handler extends SimpleUrlAuthenticationSuccessHandler {

        private final UserService userService;
        private final JwtService jwtService;

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                            Authentication authentication) throws IOException, ServletException {

            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
            DefaultOAuth2User oAuth2User = (DefaultOAuth2User) token.getPrincipal();

            String email = oAuth2User.getAttribute("email");
            log.info("email: "+ email);

            UserEntity userEntity = userService.findUserByEmail(email);

            if(userEntity == null){
                UserEntity newUser = UserEntity.builder()
                        .name(oAuth2User.getAttribute("name"))
                        .email(email)
                        .build();
                userEntity = userService.saveUser(newUser);
            }

            String accessToken = jwtService.generateAccessToken(userEntity);
            String refreshToken = jwtService.generateRefreshToken(userEntity);

            Cookie cookie = new Cookie("refreshToken", refreshToken);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);

            String frontEndUrl = "http://localhost:8080/home.html?token="+accessToken;
            getRedirectStrategy().sendRedirect(request, response, frontEndUrl);

        }
    }
