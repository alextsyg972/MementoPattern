package org.example.springsecuritypractice3.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private static final Logger logger = LoggerFactory.getLogger(CustomOauth2UserService.class);
    private static final String ADMIN = "";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String login = (String) attributes.get("login");

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();


        if (ADMIN.equals(login)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        logger.info("Пользователь с login {} вошел в систему", login);
        return new DefaultOAuth2User(
                authorities,
                oAuth2User.getAttributes(),
                "login"
        );
    }
}
