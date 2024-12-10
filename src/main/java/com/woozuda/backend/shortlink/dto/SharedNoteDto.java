package com.woozuda.backend.shortlink.dto;

import com.woozuda.backend.diary.entity.Diary;
import com.woozuda.backend.note.entity.NoteContent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SharedNoteDto {
    private Long id;

    private Diary diary;

    private String title;

    private LocalDate date;

    private List<String> noteContents;


}
