package com.woozuda.backend.account.service;

import com.woozuda.backend.account.dto.CustomOAuth2User;
import com.woozuda.backend.account.dto.GoogleResponse;
import com.woozuda.backend.account.dto.NaverResponse;
import com.woozuda.backend.account.dto.OAuth2Response;
import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response;

        if(registrationId.equals("naver")){
            System.out.println("아~~~");
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }else if(registrationId.equals("google")){
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }else{
            return null;
        }

        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        UserEntity existData = userRepository.findByUsername(username);

        if (existData == null) {

            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(username);
            //userEntity.setEmail(oAuth2Response.getEmail());
            //userEntity.setName(oAuth2Response.getName());
            userEntity.setRole("ROLE_ADMIN");

            userRepository.save(userEntity);

            return new CustomOAuth2User(userEntity);
        }else{
            return new CustomOAuth2User(existData);
        }
    }
}
