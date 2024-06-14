package com.example.board.domain.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@AllArgsConstructor
// CustomOAuth2User는 OAuth2User 인터페이스를 구현하며, 사용자 정보와 추가적인 사용자 세부 정보를 포함합니다.
public class CustomOAuth2User implements OAuth2User {

    // final 을 붙여 한 번만 초기화할 거라는 의도를 명확히 전달
    // 객체가 생성된 후 상태를 변경하지 않을 것이다.
    private final OAuth2User oauth2User;
    private final String name;
    private final String profilePic;
    private final String providerId;

    // OAuth2User의 속성(attributes)을 반환합니다.
    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    // OAuth2User의 권한(authorities)을 반환합니다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }

}
