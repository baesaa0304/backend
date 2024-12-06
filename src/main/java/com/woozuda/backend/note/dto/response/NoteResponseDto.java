package com.woozuda.backend.note.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class NoteResponseDto {

    private Long id; //노트 ID
    private String diary; //다이어리 제목
    private String title; //노트 제목
    private String date; //노트 작성 날짜
    private String weather; //날씨 (RETROSPECTIVE type일 때는 null)
    private String season; //계절 (RETROSPECTIVE type일 때는 null)
    private String feeling; //감정 (RETROSPECTIVE type일 때는 null)
    private String question; // 질문 (COMMON,RETROSPECTIVE type일 때는 null)
    private String framework; //회고 프레임워크 (COMMON, QUESTION type일 때는 null)
    private List<String> content; //노트 내용(type에 상관없이 배열로 저장. COMMON,QUESTION type: content.size()==1, RETROSPECTIVE type: content.size()>1)

    @QueryProjection
    public NoteResponseDto(Long id, String diary, String title, String date, String weather, String season, String feeling, List<String> content) {
        this.id = id;
        this.diary = diary;
        this.title = title;
        this.date = date;
        this.weather = weather;
        this.season = season;
        this.feeling = feeling;
        this.content = content;
    }

    @QueryProjection
    public NoteResponseDto(Long id, String diary, String title, String date, String weather, String season, String feeling, String question, List<String> content) {
        this.id = id;
        this.diary = diary;
        this.title = title;
        this.date = date;
        this.weather = weather;
        this.season = season;
        this.feeling = feeling;
        this.question = question;
        this.content = content;
    }

    @QueryProjection
    public NoteResponseDto(Long id, String diary, String title, String date, String framework, List<String> content) {
        this.id = id;
        this.diary = diary;
        this.title = title;
        this.date = date;
        this.framework = framework;
        this.content = content;
    }


}
