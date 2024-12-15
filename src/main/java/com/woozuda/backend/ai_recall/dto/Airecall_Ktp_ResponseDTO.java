package com.woozuda.backend.ai_recall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airecall_Ktp_ResponseDTO {
    private LocalDate start_date;
    private LocalDate end_date;
    private String strength_analysis;
    private String improvement;
    private String scalability;
}
