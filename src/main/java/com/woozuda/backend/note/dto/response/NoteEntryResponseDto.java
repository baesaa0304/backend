package com.woozuda.backend.note.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class NoteEntryResponseDto implements Comparable<NoteEntryResponseDto> {

    private static final Map<String, Integer> priority = Map.of("COMMON", 1, "QUESTION", 2, "RETROSPECTIVE", 3);

    private String type; //노트 종류 (COMMON, QUESTION, RETROSPECTIVE)
    private NoteResponseDto note;

    @Override
    public int compareTo(NoteEntryResponseDto o) {
        int first = o.getNote().getDate().compareTo(this.getNote().getDate());
        if (first != 0) {
            return first;
        }

        return priority.get(this.getType()) - priority.get(o.getType());
    }
}
