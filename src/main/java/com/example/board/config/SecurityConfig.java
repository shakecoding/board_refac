package com.example.board.config;

import com.example.board.domain.dto.UsersDTO;
import com.example.board.domain.oauth.CustomOAuth2User;
import com.example.board.mapper.UsersMapper;
import com.example.board.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity // 웹 보안 활성화, Spring Security
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final UsersMapper usersMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // 전체 요청에 접근할 수 있도록 하는 코드.
//        return http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
//                .build();

        return http
                // API서버나 RESTful 서비스에서는 CSRF 보호가 필요하지 않음.
                .csrf(AbstractHttpConfigurer::disable)
                // 요청에 대한 인증 및 인가를 설정.
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )

                // 로그인을 OAuth2기반으로 구성할 것이다.
                .oauth2Login(login -> login
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        ).successHandler(authenticationSuccessHandler()).permitAll()
                )

                .logout(
                        logout -> logout
                        .logoutSuccessHandler((request, response, authentication) -> {
//                            request.getSession().invalidate();
                            String clientId = "5618085d40e3ca0f01a1e415a47564bb";
                            String logoutRedirectUri = "http://localhost:8090";
                            String logoutUri = "https://kauth.kakao.com/oauth/logout?client_id=" + clientId + "&logout_redirect_uri=" + logoutRedirectUri;
                            response.sendRedirect(logoutUri);
                        })
                )
                .build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, auth) -> {
            CustomOAuth2User customOAuth2User = (CustomOAuth2User) auth.getPrincipal();

            UsersDTO user = usersMapper.findByProviderId(customOAuth2User.getProviderId());

            if (user.getRole() == null) {
                response.sendRedirect("/board/join");
            }
            else {
                response.sendRedirect("/board/list");
            }
        };
    }



}
