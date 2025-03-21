package com.woozuda.backend.shortlink.dto.ai_creation;

import com.woozuda.backend.ai_creation.entity.CreationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SharedAiByType {

    private CreationType creationType;
    private SearchSharedAiCreationDto aiCreation;
}
