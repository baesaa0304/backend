package com.woozuda.backend.shortlink.dto.ai_creation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AiCreationIdDto {
    List<Long> id;
}
