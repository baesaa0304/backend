package com.woozuda.backend.diary.entity;

import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.global.entity.BaseTimeEntity;
import com.woozuda.backend.note.entity.Note;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "diary")
public class Diary extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private UserEntity user;

    //TODO 이미지 엔티티 생성 후 해당 칼럼 수정
    @Column(name = "image_id", nullable = false)
    private Long image;

    @Column(nullable = false)
    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(nullable = false)
    private Integer noteCount;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private final List<Note> notes = new ArrayList<>();

}
