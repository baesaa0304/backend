package com.woozuda.backend.mypage.service;

import com.woozuda.backend.account.entity.AiType;
import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.account.repository.UserRepository;
import com.woozuda.backend.mypage.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final UserRepository userRepository;

    public EmailDto getEmail(String username){
        UserEntity userEntity = userRepository.findByUsername(username);
        return new EmailDto(userEntity.getEmail());
    }

    @Transactional
    public void makeNovel(String username){
        UserEntity userEntity = userRepository.findByUsername(username);
        userEntity.setAiType(AiType.PICTURE_NOVEL);
        userRepository.save(userEntity);
    }

    @Transactional
    public void makePoem(String username){
        UserEntity userEntity = userRepository.findByUsername(username);
        userEntity.setAiType(AiType.PICTURE_POETRY);
        userRepository.save(userEntity);
    }

    @Transactional
    public void alarmOn(String username){
        UserEntity userEntity = userRepository.findByUsername(username);
        userEntity.setAlarm(true);
        userRepository.save(userEntity);
    }

    @Transactional
    public boolean alarmOff(String username){
        UserEntity userEntity = userRepository.findByUsername(username);
        userEntity.setAlarm(false);
        userRepository.save(userEntity);
        return true;
    }
}
