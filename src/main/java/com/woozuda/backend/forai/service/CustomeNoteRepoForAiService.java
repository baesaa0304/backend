package com.woozuda.backend.forai.service;

import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.account.repository.UserRepository;
import com.woozuda.backend.forai.dto.CountRecallDto;
import com.woozuda.backend.forai.repository.CustomNoteRepoForAi;
import com.woozuda.backend.forai.dto.NonRetroNoteEntryResponseDto;
import com.woozuda.backend.forai.dto.RetroNoteEntryResponseDto;
import com.woozuda.backend.note.entity.type.Framework;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomeNoteRepoForAiService {
    private final CustomNoteRepoForAi customNoteRepoForAi;
    private final UserRepository userRepository;

    // 사용자 유저 정보 가져오기
    public UserEntity getUserEntity(String username) {
        return userRepository.findByUsername(username);
    }
    /**
     * 사용자 , 주간 , 일기가져오기
     * @param username
     * @param start_date
     * @param end_date
     * @return
     */
    public List<NonRetroNoteEntryResponseDto> getNonRetroNotes(String username, LocalDate start_date, LocalDate end_date) {
        return customNoteRepoForAi.searchNonRetroNote(username, start_date, end_date);
    }
    // 일기 수
    public long getDiaryCount (String username, LocalDate start_date, LocalDate end_date) {
        return customNoteRepoForAi.aiDiaryCount(username, start_date, end_date);
    }


    /**
     * 사용자 , 주간 , 회고 가져오기
     * @param username
     * @param start_date
     * @param end_date
     * @return
     */
    public List<RetroNoteEntryResponseDto> getRetroNotes(String username, LocalDate start_date, LocalDate end_date , Framework type) {
        return customNoteRepoForAi.searchRetroNote(username, start_date, end_date , type);
    }
    // 회고 타입별 수
    public CountRecallDto getCountRecallDto(String username, LocalDate start_date, LocalDate end_date){
        return customNoteRepoForAi.countRecallDto(username,start_date,end_date);
    }

}
