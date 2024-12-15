package com.woozuda.backend.ai_diary.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woozuda.backend.account.entity.QUserEntity;
import com.woozuda.backend.ai_diary.entity.AiDiary;
import com.woozuda.backend.ai_diary.entity.QAiDiary;
import jakarta.persistence.EntityManager;

import static com.woozuda.backend.ai_diary.entity.QAiDiary.aiDiary;
import static com.woozuda.backend.account.entity.QUserEntity.userEntity;

import java.time.LocalDate;
import java.util.Optional;

public class AiDiaryRepositoryImpl implements AiDiaryRepositoryCustom {

    private final JPAQueryFactory query;

    public AiDiaryRepositoryImpl(EntityManager entityManager) {
        this.query = new JPAQueryFactory(entityManager);
    }

    @Override
    public Optional<AiDiary> findByAiDiary(LocalDate start_date, LocalDate end_date, Long id, String username) {
        AiDiary result = query
                .selectFrom(aiDiary)
                .join(aiDiary.user, userEntity)
                .where(aiDiary.id.eq(id)
                        .and(aiDiary.start_date.eq(start_date))
                        .and(aiDiary.end_date.eq(end_date))
                        .and(userEntity.username.eq(username)))
                .fetchOne();

        return Optional.ofNullable(result);
    }

}
