package com.woozuda.backend.note.controller;

import com.woozuda.backend.account.dto.CustomUser;
import com.woozuda.backend.diary.dto.response.NoteIdResponseDto;
import com.woozuda.backend.note.dto.request.QuestionNoteSaveRequestDto;
import com.woozuda.backend.note.service.QuestionNoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/note/question")
@RequiredArgsConstructor
public class QuestionNoteController {

    private final QuestionNoteService noteService;

    @PostMapping
    public ResponseEntity<NoteIdResponseDto> createCommonNote(
            @AuthenticationPrincipal CustomUser user,
            @RequestBody @Valid QuestionNoteSaveRequestDto requestDto
    ) {
        String username = user.getUsername();
        NoteIdResponseDto responseDto = noteService.saveQuestionNote(username, requestDto);
        return ResponseEntity.ok(responseDto);
    }

}
