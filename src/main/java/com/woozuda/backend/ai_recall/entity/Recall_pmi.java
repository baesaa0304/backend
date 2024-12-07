package com.woozuda.backend.ai_recall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "retrospective_pmi")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Recall_pmi {
    @Id
    private Long air_id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String strength_analysis;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String improvement;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String scalability;
}
