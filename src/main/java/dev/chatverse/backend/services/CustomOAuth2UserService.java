package dev.chatverse.backend.services;

import dev.chatverse.backend.documents.User.Role;
import dev.chatverse.backend.documents.User.User;
import dev.chatverse.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauthUser = super.loadUser(userRequest);
        return processOAuth2User(oauthUser);
    }

    private OAuth2User processOAuth2User(OAuth2User oauthUser) {
        String email = oauthUser.getAttribute("email");
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            user = new User();
            assert email != null;
            user.setEmail(email);
            user.setUserName(Objects.requireNonNull(oauthUser.getAttribute("name")));
            user.setRoles(new HashSet<>(Set.of(Role.USER)));
            user.setGivenName(Objects.requireNonNull(oauthUser.getAttribute("given_name")));
            user.setFamilyName(Objects.requireNonNull(oauthUser.getAttribute("family_name")));
            user.setPicture(oauthUser.getAttribute("picture"));
            user.setLocale(oauthUser.getAttribute("locale"));
            userRepository.save(user);
        }

        System.out.println(oauthUser.getAttributes());

        Set<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
        return new DefaultOAuth2User(authorities, oauthUser.getAttributes(), "email");
    }
}
